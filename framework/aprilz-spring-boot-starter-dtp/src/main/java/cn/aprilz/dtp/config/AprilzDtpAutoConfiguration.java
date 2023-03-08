package cn.aprilz.dtp.config;

import cn.aprilz.dtp.DtpMonitor;
import cn.aprilz.dtp.listen.NacosListener;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Aprilz
 * @date 2023/3/7-18:29
 * @description 装配线程池
 */
@Configuration
@EnableConfigurationProperties(DtpProperties.class)
@Import({DtpImportBeanDefinitionRegistrar.class,DtpBeanPostProcessor.class})
public class AprilzDtpAutoConfiguration {

    @Bean
    public NacosListener nacosListener(){
        return new NacosListener();
    }

    @Bean
    public DtpMonitor dtpMonitor(){
        return new DtpMonitor();
    }
}
