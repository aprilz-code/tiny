package com.aprilz.tiny.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author Aprilz
 * @date 2023/3/13-14:37
 * @description 自定义校验config
 */
@Configuration
public class ValidatorConfig {

    /**
     * 快速返回校验器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = Validator.class)
    public Validator validator(AutowireCapableBeanFactory beanFactory) {
        //hibernate-validator 6.x没问题，7.x有问题
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(beanFactory))// 使用spring代理，
                //   .failFast(true) //不需要快速失败,需要则打开
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

}
