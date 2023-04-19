package com.aprilz.tiny.designMode.observer.impl;

import com.aprilz.tiny.designMode.observer.Observer;

/**
 * @description: 观察者B
 * @author: Aprilz
 * @since: 2022/11/10
 **/
public class ObserverB implements Observer {
    @Override
    public void execute() {
        System.out.println("观察者B do somethings");
    }
}
