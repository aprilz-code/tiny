package com.aprilz.tiny.controller;

import com.aprilz.tiny.service.IApCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description: 二维码
 * @author: liushaohui
 * @since: 2022/5/25
 **/
@Controller
public class WriteController {

    @Autowired
    private IApCodeService apCodeService;

    @GetMapping({"/to","/"})
    public ModelAndView index(@RequestParam("token") String token) {
        ModelAndView model = new ModelAndView();
        boolean bool =apCodeService.verification(token);
        if (bool){
            model.setViewName("writePage");
        }else{
            model.addObject("errMsg", "无权访问");
            model.setViewName("error");
        }
        return model;
    }

}
