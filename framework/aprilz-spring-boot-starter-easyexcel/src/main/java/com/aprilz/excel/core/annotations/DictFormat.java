package com.aprilz.excel.core.annotations;

import java.lang.annotation.*;

/**
 * 字典格式化
 * <p>
 * 实现将字典数据的值，格式化成字典数据的标签
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DictFormat {

    /**
     * 例如说，DictTypeConstants
     *
     * @return 字典类型
     */
    String value();

}
