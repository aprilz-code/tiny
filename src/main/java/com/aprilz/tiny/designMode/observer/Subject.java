package com.aprilz.tiny.designMode.observer;

/**
 * @description: 目标类抽象接口
 * @author: Aprilz
 * @since: 2022/11/10
 **/
public interface Subject {
    //添加观察者
    void add(Observer observer);

    //删除观察者
    void remove(Observer observer);

    //通知所有观察者
    void notifyObservers();

    //自身操作
    void operation();

}
