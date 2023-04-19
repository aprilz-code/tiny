package com.aprilz.tiny.common.validator.constraint;

import cn.hutool.core.util.StrUtil;
import com.aprilz.tiny.common.utils.IdCardUtil;
import com.aprilz.tiny.common.validator.annotations.IdCardValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Aprilz
 * @date 2023/3/10-16:14
 * @description 身份证号校验
 */
public class IdCardValidator implements ConstraintValidator<IdCardValid, String> {

    // 是否强制校验
    private boolean required;

    @Override
    public void initialize(IdCardValid constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (!required && StrUtil.isBlank(value)) {
            return true;
        }

        if (StrUtil.isBlank(value)) {
            return false;
        }
        return IdCardUtil.isLegalPattern(value);

    }
}
