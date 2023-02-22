package com.aprilz.tiny.controller;

import cn.aprilz.excel.core.util.ExcelUtil;
import cn.hutool.core.date.StopWatch;
import com.aprilz.tiny.common.api.CommonResult;
import com.aprilz.tiny.mbg.entity.ApExcelTest;
import com.aprilz.tiny.service.IApExcelTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * excel-test表 前端控制器
 * </p>
 *
 * @author aprilz
 * @since 2023-02-22
 */
@RestController
@RequestMapping("/excel")
public class ApExcelTestController {

    @Resource
    private IApExcelTestService iApExcelTestService;

    @GetMapping("/list")
    public CommonResult<List<ApExcelTest>> getAll(){
        return CommonResult.success(iApExcelTestService.list());
    }

    @GetMapping("/test")
    public void getAll( HttpServletResponse response) throws IOException {
        List<ApExcelTest> datas = iApExcelTestService.lambdaQuery().last("limit 2000").list();
        // 输出
        ExcelUtil.write(response, "test", "数据", ApExcelTest.class, datas);

    }

}
