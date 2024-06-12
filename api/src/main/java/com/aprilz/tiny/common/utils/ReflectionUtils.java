package com.aprilz.tiny.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aprilz
 * @date 2023/8/22-17:38
 * @description 反射Util
 */
public class ReflectionUtils {

    /**
     * 获取类以及父类所有的属性
     *
     * @param clazz
     * @return
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            fields.add(field);
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            List<Field> superFields = getAllFields(superClass);
            fields.addAll(superFields);
        }

        return fields;
    }


    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            // 在当前类中查找属性
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);  // 设置为可访问
            return field;
        } catch (NoSuchFieldException e) {
            // 如果在当前类找不到属性，则查找父类
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getField(superClass, fieldName);
            } else {
                e.printStackTrace();
                return null;
            }
        }
    }
}
