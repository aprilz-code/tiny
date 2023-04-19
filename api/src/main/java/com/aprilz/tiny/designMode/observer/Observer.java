package com.aprilz.tiny.designMode.observer;

/**
 * @description: 观察者抽象接口
 * @author: Aprilz
 * @since: 2022/11/10
 **/
public interface Observer {
    //收到通知，改变自身状态
    void execute();
}
