package com.aprilz.tiny.controller;//package com.aprilz.tiny.controller;

import com.aprilz.tiny.common.api.CommonResult;
import com.aprilz.tiny.mbg.entity.ApExcelTest;
import com.aprilz.tiny.service.IApTestService;
import com.aprilz.tiny.service.impl.ApExcelTest2ServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author Aprilz
 * @since 2022-08-11
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private IApTestService testService;


    @GetMapping("/testTrans")
    public CommonResult<Boolean> testTrans() {
        return CommonResult.success(testService.testTrans());
    }

    @GetMapping("/testTrans2")
    public CommonResult<Boolean> testTrans2() {
        return CommonResult.success(testService.testTrans2());
    }

}
