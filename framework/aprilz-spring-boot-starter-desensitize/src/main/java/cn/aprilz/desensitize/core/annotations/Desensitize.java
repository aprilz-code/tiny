package cn.aprilz.desensitize.core.annotations;

import cn.aprilz.desensitize.core.enums.DesensitizeRuleEnums;
import cn.aprilz.desensitize.core.handle.SensitiveJsonSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Aprilz
 * @date 2023/4/17-14:22
 * @description 数据脱敏注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveJsonSerializer.class)
public @interface Desensitize {

    /**
     * 脱敏规则
     */
    DesensitizeRuleEnums rule();
}
