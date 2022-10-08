package com.aprilz.tiny.service.strategy.impl;

import com.aprilz.tiny.service.strategy.Strategy;
import org.springframework.stereotype.Service;

/**
 * @description: 为春节准备的促销活动A
 * @author: Aprilz
 * @since: 2022/9/30
 **/
@Service
public class StrategyA implements Strategy {

    @Override
    public void show() {
        System.out.println("买一送一");
    }
}