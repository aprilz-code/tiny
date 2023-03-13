package com.aprilz.tiny.common.validator.constraint;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.aprilz.tiny.common.utils.Regionutil;
import com.aprilz.tiny.common.validator.annotations.RegionExcelValid;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aprilz
 * @date 2023/3/10-16:14
 * @description excel地区导入格式校验
 */
@Component
public class RegionExcelValidator implements ConstraintValidator<RegionExcelValid, String> {

    @Resource
    private Regionutil regionutil;

    // 是否强制校验
    private boolean required;

    @Override
    public void initialize(RegionExcelValid constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        if (!required && StrUtil.isBlank(value)) {
//            return true;
//        }
//
//        if (StrUtil.isBlank(value)) {
//            return false;
//        }
//        List<String> split = StrUtil.split(value, "-");
//        if (CollUtil.isEmpty(split) || split.size() != 3) {
//            return false;
//        }
//
//        List<HjRegionTree> tree = RegionTreeUtil.getTree();
//        //List<HjRegionTree> collect = tree.stream().filter(t -> split.get(0).equals(t.getLabel())).collect(Collectors.toList());
//        List<HjRegionTree> collect = valid(tree, split.get(0));
//        if (CollUtil.isEmpty(collect)) {
//            this.setErrorMessage(context, "省级不存在");
//            return false;
//        }
//        collect = valid(collect.get(0).getChildren(), split.get(1));
//        if (CollUtil.isEmpty(collect)) {
//            this.setErrorMessage(context, "市级不存在");
//            return false;
//        }
//        collect = valid(collect.get(0).getChildren(), split.get(2));
//        if (CollUtil.isEmpty(collect)) {
//            this.setErrorMessage(context, "区级不存在");
//            return false;
//        }
//        return true;
//    }
//
//    private List<HjRegionTree> valid(List<HjRegionTree> tree, String label) {
//        return tree.stream().filter(t -> label.equals(t.getLabel())).collect(Collectors.toList());
//    }

    // 设置校验失败的提示消息
    private void setErrorMessage(ConstraintValidatorContext constraintValidatorContext, String errorMessage) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(errorMessage)
                .addConstraintViolation();
    }
}
