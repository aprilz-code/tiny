package com.aprilz.tiny.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description: 测试控制器
 * @author: liushaohui
 * @since: 2022/5/25
 **/
@Controller
public class TestController {

    @GetMapping("/test")
    public String getInsertUser(){
        return "test";
    }

}
