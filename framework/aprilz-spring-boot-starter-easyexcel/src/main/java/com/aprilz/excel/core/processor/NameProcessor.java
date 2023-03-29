package com.aprilz.excel.core.processor;

import java.lang.reflect.Method;

/**
 * @author Aprilz
 * @date 2020/3/29
 */
public interface NameProcessor {

    /**
     * 解析名称
     *
     * @param args   拦截器对象
     * @param method
     * @param key    表达式
     * @return
     */
    String doDetermineName(Object[] args, Method method, String key);

}
