package com.aprilz.tiny.designMode.observer.impl;

import com.aprilz.tiny.designMode.observer.Observer;

/**
 * @description: 观察者A
 * @author: Aprilz
 * @since: 2022/11/10
 **/
public class ObserverA implements Observer {
    @Override
    public void execute() {
        System.out.println("观察者A do somethings");
    }
}
