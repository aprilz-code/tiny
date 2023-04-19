package com.aprilz.tiny.designMode.strategy.impl;

import com.aprilz.tiny.designMode.strategy.Strategy;
import org.springframework.stereotype.Service;

/**
 * @description: 为中秋准备的促销活动B
 * @author: Aprilz
 * @since: 2022/9/30
 **/
@Service
public class StrategyB implements Strategy {

    @Override
    public void show() {
        System.out.println("满200元减50元");
    }
}
