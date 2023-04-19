package com.aprilz.tiny.designMode.strategy;

import com.aprilz.tiny.common.utils.SpringContextUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: Context
 * @author: Aprilz
 * @since: 2022/9/30
 **/
@Component
public class Context {

    private static Map<String, Strategy> serviceMap = new HashMap<>();
    //不用static 默认装配至serviceMap中

    public static Strategy getStrategy(String serviceName) {
        return serviceMap.get(serviceName);
    }

    @PostConstruct
    public void init() {
        serviceMap = SpringContextUtil.getBeansOfType(Strategy.class);
    }


}
