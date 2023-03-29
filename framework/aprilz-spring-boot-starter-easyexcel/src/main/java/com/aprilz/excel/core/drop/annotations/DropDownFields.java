package com.aprilz.excel.core.drop.annotations;

import com.aprilz.excel.core.drop.enums.DropDownType;
import com.aprilz.excel.core.drop.services.IDropDownService;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DropDownFields {
    /**
     * 固定下拉
     *
     * @return
     */
    String[] source() default {};

    /**
     * 动态下拉内容，可查数据库返回等，其他操作
     *
     * @return
     */
    Class<? extends IDropDownService> sourceClass() default IDropDownService.class;

    /**
     * 下拉类型枚举，可能动态查询的时候需要用到
     *
     * @return
     */
    DropDownType type() default DropDownType.NONE;

}
