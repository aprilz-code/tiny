package com.aprilz.tiny.component.mybatis;

import cn.hutool.core.util.ReflectUtil;
import com.aprilz.tiny.common.utils.SecurityUtils;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.google.common.collect.Maps;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.joor.Reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author Aprilz
 * @date 2023/7/27-14:57
 * @description 修复mybatisPlus  lambdaUpdate不触发更新时间
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class UpdateByFillPlugin implements Interceptor {

    private static final String UPDATE_TIME_T = "updateTime";

    private static final String UPDATE_TIME = "update_time";


    private static final String UPDATE_BY_T = "updateBy";

    private static final String UPDATE_BY = "update_by";

    public static final Map<String, Supplier<Object>> SUPPORT_TIME_TYPE = Maps.newHashMap();

    static {
        SUPPORT_TIME_TYPE.put(LocalDateTime.class.getName(), LocalDateTime::now);
        SUPPORT_TIME_TYPE.put(LocalDate.class.getName(), LocalDate::now);
        SUPPORT_TIME_TYPE.put(LocalTime.class.getName(), LocalTime::now);
        SUPPORT_TIME_TYPE.put(Date.class.getName(), Date::new);
    }


    @Override
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {

        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        if (SqlCommandType.UPDATE != ms.getSqlCommandType()) {
            return invocation.proceed();
        }

        Object arg = args[1];
        if (arg == null) {
            return invocation.proceed();
        }

        // arg为空时,无参Mapper执行
        // arg不为空时
        //  如果是Map,则是有参数Mapper
        //  如果是有TableName注解,则是lambdaUpdate
        Class<?> entityClazz = arg.getClass();
        if (arg instanceof Map) {
            Map paramMap = (Map) arg;

            Object et = paramMap.getOrDefault(Constants.ENTITY, null);
            //针对于updateById这种情况处理
            if (Objects.nonNull(et) && ReflectUtil.hasField(et.getClass(), UPDATE_TIME_T) && ReflectUtil.hasField(et.getClass(), UPDATE_BY_T)) {
                this.setUpdate(et);
                return invocation.proceed();
            }

            //当没有定义实体类的时候 强行塞值～
            if (paramMap.containsKey("param2") && paramMap.get("param2") instanceof LambdaUpdateWrapper) {
                LambdaUpdateWrapper lambdaUpdateWrapper = (LambdaUpdateWrapper) paramMap.get("param2");

                Field columnMap = ReflectUtil.getField(lambdaUpdateWrapper.getClass(), "columnMap");
                columnMap.setAccessible(true);
                Map<String, ColumnCache> map = (Map<String, ColumnCache>) columnMap.get(lambdaUpdateWrapper);

                if (map.keySet().stream().anyMatch(k -> k.equalsIgnoreCase(UPDATE_TIME_T))) {
                    lambdaUpdateWrapper.setSql(true, UPDATE_TIME + "='" + LocalDateTime.now() + "'");
                } else if (map.values().stream().anyMatch(k -> k.getColumn().equalsIgnoreCase(UPDATE_TIME))) {
                    lambdaUpdateWrapper.setSql(true, UPDATE_TIME + "='" + LocalDateTime.now() + "'");
                }

                if (map.keySet().stream().anyMatch(k -> k.equalsIgnoreCase(UPDATE_BY_T))) {
                    lambdaUpdateWrapper.setSql(true, UPDATE_BY + "='" + getUserName() + "'");
                    return invocation.proceed();
                } else if (map.values().stream().anyMatch(k -> k.getColumn().equalsIgnoreCase(UPDATE_BY))) {
                    lambdaUpdateWrapper.setSql(true, UPDATE_BY + "='" + getUserName() + "'");
                    return invocation.proceed();
                }
            }
        }

        if (entityClazz.isAnnotationPresent(TableName.class)) {
            // 当使用lambdaUpdate时,mybatisPlus会将其解析为select标签,不会走到MetaObjectHandlerConfig自动填充更新时间

            Reflect entityReflect = Reflect.on(arg);
            Arrays.stream(entityClazz.getDeclaredFields()).filter(e -> {
                TableField tableField = e.getAnnotation(TableField.class);
                return tableField != null && tableField.fill() == FieldFill.INSERT_UPDATE;
            }).forEach(field -> {
                Supplier<Object> objectSupplier = SUPPORT_TIME_TYPE.get(field.getType().getName());
                field.setAccessible(true);
                entityReflect.set(field.getName(), objectSupplier.get());

                //处理updateBy
                if (field.getType().getName() == String.class.getName()) {
                    field.setAccessible(true);
                    entityReflect.set(field.getName(), getUserName());
                }

            });
        }
        return invocation.proceed();
    }

    private void setUpdate(Object arg) throws IllegalAccessException, NoSuchFieldException {
        Field declaredField = getField(arg.getClass(), UPDATE_TIME_T);
        if (Objects.isNull(declaredField.get(arg))) {
            Supplier<Object> objectSupplier = SUPPORT_TIME_TYPE.get(declaredField.getType().getName());
            declaredField.set(arg, objectSupplier.get());
        }

        Field updateByField = getField(arg.getClass(), UPDATE_BY_T);
        if (Objects.isNull(updateByField.get(arg))) {
            updateByField.set(arg, getUserName());
        }
    }


    private Field getField(Class<?> clazz, String fieldName) {
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

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private String getUserName() {
        try {
            String userName = SecurityUtils.getUserName();
            return userName;
        } catch (Exception e) {
            return "ADMIN";
        }
    }


}
