package com.aprilz.tiny.designMode.proxy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Classname StarProxy
 * @Description jdk动态代理增强类
 * @Date 2022/2/26 10:57
 * @Created by aprilz
 */
public class StarProxy  implements InvocationHandler {

    //目标类,也就是被代理对象
    private Object target;

    public void setTarget(Object target){
        this.target = target;
    }

   /**
   * @Author: aprilz
   * @Description: 给Star增强
   * @Date: 2022/2/26  11:22
   * @Param proxy:
    * @Param method:
    * @Param args:
   * @return: java.lang.Object
   **/
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("收钱");
        Object invoke = method.invoke(target, args);
        System.out.println("散场");
        return invoke;
    }

    //生成代理类
    public Object CreatProxyObj() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }
}
