package com.aprilz.tiny.service.strategy.impl;

import com.aprilz.tiny.service.strategy.Strategy;
import org.springframework.stereotype.Service;

/**
 * @description: 为圣诞准备的促销活动C
 * @author: Aprilz
 * @since: 2022/9/30
 **/
@Service
public class StrategyC implements Strategy {

    @Override
    public void show() {
        System.out.println("满1000元加一元换购任意200元以下商品");
    }
}
