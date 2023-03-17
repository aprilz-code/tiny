package com.aprilz.dtp.listen;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.aprilz.dtp.config.DtpProperties;
import com.aprilz.dtp.core.DtpExecutor;
import com.aprilz.dtp.util.DtpUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author 宋平州
 * nacos监听器
 * InitializingBean spring 的初始化Bean
 */
public class NacosListener implements Listener , InitializingBean {

    // nacos-clinet1.*版本@NacosInjected注入可以触发nacosConfigPublishedEvent回调，也就是配置注册到nacos的时候产生的回调，其余方式不会触发 **
//    @NacosInjected
//    private ConfigService configService;
    
    //nacos-clinet2.*版本用法 https://github.com/nacos-group/nacos-examples/issues/28
    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public Executor getExecutor() {
        return Executors.newFixedThreadPool(1);
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
        bean.setResources(new ByteArrayResource(configInfo.getBytes()));
        Properties properties = bean.getObject();

        DtpProperties dtpProperties = new DtpProperties();
        ConfigurationPropertySource sources = new MapConfigurationPropertySource(properties);
        Binder binder =  new Binder(sources);
        ResolvableType type = ResolvableType.forClass(DtpProperties.class);
        Bindable<?> target = Bindable.of(type).withExistingValue(dtpProperties);
        binder.bind("dtp",target);

        //配置的线程数组属性
        List<DtpProperties.DtpExecutorProperties> executors = dtpProperties.getExecutors();
        if(CollectionUtils.isEmpty(executors)){
            return;
        }
        for (DtpProperties.DtpExecutorProperties executorProperties : executors) {//Bean对象
            DtpExecutor dtpExecutor = DtpUtil.get(executorProperties.getName());
            dtpExecutor.setCorePoolSize(executorProperties.getCorePoolSize());
            dtpExecutor.setMaximumPoolSize(executorProperties.getMaximumPoolSize());
        }
    }

    //创建NacosListener Bean时会调用此方法
    @Override
    public void afterPropertiesSet() throws Exception {
        //configService.addListener("dtp.yml","train-school-dev",this);
        nacosConfigManager.getConfigService().addListener("dtp.yml","DEFAULT_GROUP",this);
    }
}
