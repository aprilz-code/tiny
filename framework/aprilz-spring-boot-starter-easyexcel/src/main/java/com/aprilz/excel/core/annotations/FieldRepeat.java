package com.aprilz.excel.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Aprilz
 * @date 2023/3/10-9:33
 * @description 导入数据字段重复性校验使用
 * 使用示例 @FieldRepeat(fields={"name","typeCode"},message="字典名称重复，请重新输入！")
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldRepeat {
    /**
     * 需要校验的字段
     *
     * @return
     */
    String[] fields() default {};

    String message() default "存在重复数据";

}
