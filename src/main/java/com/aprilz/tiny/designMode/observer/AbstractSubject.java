package com.aprilz.tiny.designMode.observer;

import java.util.Vector;

/**
 * @description: 目标类抽象类
 * @author: Aprilz
 * @since: 2022/11/10
 **/
public abstract class AbstractSubject implements Subject {
    Vector<Observer> vector = new Vector<Observer>();

    @Override
    public void add(Observer observer) {
        vector.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        vector.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : vector) {
            observer.execute();
        }
    }

}
