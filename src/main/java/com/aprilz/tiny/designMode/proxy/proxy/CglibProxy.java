package com.aprilz.tiny.designMode.proxy.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Classname CglibProxy
 * @Description cglib代理star
 * @Date 2022/2/26 11:47
 * @Created by aprilz
 */
public class CglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 这里增强
        System.out.println("收钱");
        Object invoke = methodProxy.invokeSuper(o, objects);
        System.out.println("散场");
        return invoke;
    }

    // 根据一个类型产生代理类，此方法不要求一定放在MethodInterceptor中
    public Object CreatProxyedObj(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(clazz);

        enhancer.setCallback(this);

        return enhancer.create();
    }
}
