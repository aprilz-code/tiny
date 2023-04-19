package com.aprilz.tiny.common.validator.annotations;

/**
 * @author Aprilz
 * @date 2023/3/10-16:07
 * @description 身份证号格式校验
 */

import com.aprilz.tiny.common.validator.constraint.IdCardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdCardValidator.class)
public @interface IdCardValid {

    boolean required() default true;

    String message() default "身份证格式错误";

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
