package cn.aprilz.dtp.config;

import cn.aprilz.dtp.core.DtpExecutor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 注册器 Spring注册bean时
 */
public class DtpImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        //注册Bean
        //读取用户配置 DtpProperties
        DtpProperties dtpProperties = new DtpProperties();

        Binder binder = Binder.get(environment);
        ResolvableType type = ResolvableType.forClass(DtpProperties.class);
        Bindable<?> target = Bindable.of(type).withExistingValue(dtpProperties);
        binder.bind("dtp",target);
        //动态线程池配置
        for (DtpProperties.DtpExecutorProperties executorProperties : dtpProperties.getExecutors()) {
            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getRawBeanDefinition();
            beanDefinition.setBeanClass(DtpExecutor.class);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(executorProperties.getCorePoolSize());
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(executorProperties.getMaximumPoolSize());
            registry.registerBeanDefinition(executorProperties.getName(),beanDefinition);
        }
    }


}
