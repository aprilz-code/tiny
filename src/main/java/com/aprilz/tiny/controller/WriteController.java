package com.aprilz.tiny.controller;

import com.aprilz.tiny.common.api.CommonResult;
import com.aprilz.tiny.param.ApUseInfoParam;
import com.aprilz.tiny.service.IApCodeService;
import com.aprilz.tiny.service.IApUseInfoService;
import com.aprilz.tiny.utils.CheckUtils;
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
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Objects;

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

    @GetMapping({"/","/form"})
    public ModelAndView index(@RequestParam("token") String token,@RequestParam(value = "relationId",required = false) Long relationId) {
        ModelAndView model = new ModelAndView();
        boolean bool = apCodeService.verification(token);
        if (bool) {
            model.addObject("token", token);
            model.addObject("relationId", relationId);
            model.setViewName("form");
        } else {
            model.addObject("errMsg", "无权访问");
            model.setViewName("error");
        }
        return model;
    }

    @GetMapping("/home")
    public ModelAndView home(@RequestParam("username") String username,@RequestParam("totalAmount") BigDecimal totalAmount,@RequestParam("token") String token,@RequestParam("relationId") Long relationId) throws UnsupportedEncodingException {
        ModelAndView model = new ModelAndView();
        model.addObject("username", URLDecoder.decode(username, "UTF-8"));
        model.addObject("totalAmount", totalAmount);
        model.addObject("token", token);
        model.addObject("relationId", relationId);
        model.setViewName("home");
        return model;
    }

    @GetMapping("/result")
    public ModelAndView result() {
        ModelAndView model = new ModelAndView();

        model.setViewName("result");
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
        Boolean isMobile = CheckUtils.checkMobileNumber(param.getPhone());
         Assert.isTrue(isMobile,"请输入合法的手机号");
         if(Objects.isNull(param.getRelationId())){
             Assert.notNull(param.getTotalAmount(), "额度不能为空");
         }

        boolean bool = apCodeService.verification(param.getToken());
        Assert.isTrue(bool,"无权访问");
        Long relationId = apUseInfoService.doIt(param, front, behind);
        ApUseInfoParam data = new ApUseInfoParam();
        data.setUsername(param.getUsername());
        data.setTotalAmount(param.getTotalAmount());
        //代表第一次进来
        if(Objects.isNull(param.getRelationId())){
            data.setRelationId(relationId);
        }
        data.setToken(param.getToken());
        return CommonResult.success(data);
    }

}
