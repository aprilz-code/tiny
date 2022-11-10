package com.aprilz.tiny.designMode.observer.impl;

import com.aprilz.tiny.designMode.observer.AbstractSubject;

/**
 * @description: 目标类
 * @author: Aprilz
 * @since: 2022/11/10
 **/
public class MySubject extends AbstractSubject {

    @Override
    public void operation() {
        System.out.println("具体目标状态改变");
        System.out.println("正在通知观察者。。。");
        this.notifyObservers();
        System.out.println("通知完毕！");
    }
}
