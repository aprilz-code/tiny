package com.aprilz.tiny.controller;

import cn.aprilz.excel.core.annotations.ResponseExcel;
import cn.aprilz.excel.core.annotations.Sheet;
import cn.aprilz.excel.core.util.ExcelUtil;
import com.aprilz.tiny.common.api.CommonResult;
import com.aprilz.tiny.mbg.entity.ApExcelTest;
import com.aprilz.tiny.service.IApExcelTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
    public CommonResult<List<ApExcelTest>> getAll() {
        return CommonResult.success(iApExcelTestService.list());
    }

    @GetMapping("/test")
    public void test(HttpServletResponse response) throws IOException {
        List<ApExcelTest> datas = iApExcelTestService.lambdaQuery().last("limit 2000").list();
        // 输出
        ExcelUtil.write(response, "test", "数据", ApExcelTest.class, datas);

    }


    @GetMapping("/test2")
    @ResponseExcel(name = "数据", sheets = {@Sheet(sheetName = "testSheet1"), @Sheet(sheetName = "testSheet2")})
    public List<ApExcelTest> test2(HttpServletResponse response) throws IOException {
        List<ApExcelTest> datas = iApExcelTestService.lambdaQuery().last("limit 2000").list();
        return datas;
    }

    @GetMapping("/test3")
    @ResponseExcel(name = "数据", sheets = {@Sheet(sheetName = "testSheet1"), @Sheet(sheetName = "testSheet2")})
    public List<List<ApExcelTest>> test3(HttpServletResponse response) throws IOException {
        List<List<ApExcelTest>> lists = new ArrayList<>();
        List<ApExcelTest> datas1 = iApExcelTestService.lambdaQuery().last("limit 2000").list();
        lists.add(datas1);
        List<ApExcelTest> datas2 = iApExcelTestService.lambdaQuery().last("limit 2001,2000").list();
        lists.add(datas2);
        return lists;
    }

}
