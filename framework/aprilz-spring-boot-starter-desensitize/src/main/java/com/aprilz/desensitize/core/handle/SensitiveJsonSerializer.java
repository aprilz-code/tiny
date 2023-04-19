package com.aprilz.desensitize.core.handle;

import com.aprilz.desensitize.core.annotations.Desensitize;
import com.aprilz.desensitize.core.enums.DesensitizeRuleEnums;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Aprilz
 * @date 2023/4/17-14:28
 * @description 数据脱敏JSON序列化工具
 */
public class SensitiveJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {


    private DesensitizeRuleEnums rule;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(rule.desensitize().apply(value));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Desensitize annotation = property.getAnnotation(Desensitize.class);
        if (Objects.nonNull(annotation) && Objects.equals(String.class, property.getType().getRawClass())) {
            this.rule = annotation.rule();
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}
