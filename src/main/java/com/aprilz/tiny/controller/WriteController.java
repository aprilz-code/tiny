package com.aprilz.tiny.controller;

import com.aprilz.tiny.common.api.CommonResult;
import com.aprilz.tiny.param.ApUseInfoParam;
import com.aprilz.tiny.service.IApCodeService;
import com.aprilz.tiny.service.IApUseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @description: 二维码
 * @author: liushaohui
 * @since: 2022/5/25
 **/
@Controller
public class WriteController {

    @Autowired
    private IApCodeService apCodeService;

    @Autowired
    private IApUseInfoService apUseInfoService;

    @GetMapping({"/to", "/"})
    public ModelAndView index(@RequestParam("token") String token) {
        ModelAndView model = new ModelAndView();
        boolean bool = apCodeService.verification(token);
        if (bool) {
            model.setViewName("writePage");
        } else {
            model.addObject("errMsg", "无权访问");
            model.setViewName("error");
        }
        return model;
    }


    @GetMapping("/newCode")
    @ResponseBody
    public String newCode() {
        apCodeService.newCode();
        return "success";
    }


    /**
     * @param param
     * @return com.aprilz.tiny.common.api.CommonResult
     * @author liushaohui
     * @description 保存信息
     * @since 2022/5/25
     **/
    @PostMapping("/doIt")
    @ResponseBody
    public CommonResult doIt(@Valid ApUseInfoParam param, @RequestParam("front") MultipartFile front, @RequestParam("behind") MultipartFile behind) {
        Assert.notNull(front, "身份证正面不能为空");
        Assert.notNull(behind, "身份证背面不能为空");
        boolean bool = apCodeService.verification(param.getToken());
        if (!bool) {
            return CommonResult.forbidden(null);
        }
        apUseInfoService.doIt(param, front, behind);

        return CommonResult.success(null);
    }

}
