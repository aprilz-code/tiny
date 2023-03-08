package cn.aprilz.dtp.config;

import cn.aprilz.dtp.core.DtpExecutor;
import cn.aprilz.dtp.util.DtpUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Bean后置处理器
 */
public class DtpBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DtpExecutor) {
            DtpUtil.set(beanName, (DtpExecutor) bean);
        }
        return bean;
    }
}
