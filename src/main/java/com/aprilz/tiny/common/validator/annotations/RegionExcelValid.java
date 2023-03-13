package com.aprilz.tiny.common.validator.annotations;



import com.aprilz.tiny.common.validator.constraint.RegionExcelValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Aprilz
 * @date 2023/3/10-16:07
 * @description excel地区导入格式校验
 */

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegionExcelValidator.class)
public @interface RegionExcelValid {

    boolean required() default true;

    String message() default "地区错误";

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
