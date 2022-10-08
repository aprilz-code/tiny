package com.aprilz.tiny.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: Context
 * @author: Aprilz
 * @since: 2022/9/30
 **/
@Component
public class Context {

    @Autowired
    Map<String, Strategy> serviceMap = new HashMap<>();
    // 默认装配至serviceMap中

//    @PostConstruct
//    public void init(){
//        serviceMap.get("strategyA").show();
//    }


}
