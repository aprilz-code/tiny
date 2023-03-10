package com.aprilz.tiny.controller;

import cn.aprilz.excel.core.annotations.RequestExcel;
import cn.aprilz.excel.core.annotations.ResponseExcel;
import cn.aprilz.excel.core.annotations.Sheet;
import cn.aprilz.excel.core.exception.ErrorMessage;
import cn.aprilz.excel.core.handler.DefaultAnalysisEventListener;
import cn.aprilz.excel.core.util.ExcelUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.aprilz.tiny.common.api.CommonResult;
import com.aprilz.tiny.mbg.entity.ApExcelTest;
import com.aprilz.tiny.service.IApExcelTestService;
import com.aprilz.tiny.service.impl.ApExcelTest2ServiceImpl;
import com.aprilz.tiny.vo.request.ApExcelTestParam;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Resource
    private ApExcelTest2ServiceImpl test2Service;

    @GetMapping("/list")
    public CommonResult<List<ApExcelTest>> getAll() {
        return CommonResult.success(iApExcelTestService.list());
    }

    /**
     * 普通导出数据
     * @param response
     * @throws IOException
     */
    @GetMapping("/test")
    public void test(HttpServletResponse response) throws IOException {
        List<ApExcelTest> datas = iApExcelTestService.lambdaQuery().last("limit 2000").list();
        // 输出
        ExcelUtil.write(response, "test", "数据", ApExcelTest.class, datas);

    }


    /**
     * 注解式导出单sheet数据
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/responseExcelTest")
    @ResponseExcel(name = "数据")
   // @ResponseExcel(name = "数据", sheets = @Sheet(sheetName = "testSheet1"))
    public List<ApExcelTest> test2(HttpServletResponse response) throws IOException {
        List<ApExcelTest> datas = iApExcelTestService.lambdaQuery().last("limit 2000").list();
        return datas;
    }

    /**
     * 注解式导出多页数据
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/sheetTest")
    @ResponseExcel(name = "数据", sheets = {@Sheet(sheetName = "testSheet1"), @Sheet(sheetName = "testSheet2")})
    public List<List<ApExcelTest>> test3(HttpServletResponse response) throws IOException {
        List<List<ApExcelTest>> lists = new ArrayList<>();
        List<ApExcelTest> datas1 = iApExcelTestService.lambdaQuery().last("limit 2000").list();
        lists.add(datas1);
        List<ApExcelTest> datas2 = iApExcelTestService.lambdaQuery().last("limit 2001,2000").list();
        lists.add(datas2);
        return lists;
    }

    /**
     *  普通导入数据
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/import")
    public CommonResult<String> importExcel(@RequestParam("file") MultipartFile file
                            ) throws Exception {
//        List<ApExcelTestParam> list = EasyExcel.read(file.getInputStream(), ApExcelTestParam.class, BeanUtils.instantiateClass(DefaultAnalysisEventListener.class)).sheet()
//                .doReadSync();
        List<ApExcelTestParam> list = ExcelUtil.read(file, ApExcelTestParam.class);
        System.out.println(list);
        //入库
        return CommonResult.success();
    }

    /**
     * 注解式导入数据
     * @param dataList
     * @param bindingResult
     * @return
     */
    @PostMapping("/requestExcelImport")
    public CommonResult<String> upload(@RequestExcel List<ApExcelTestParam> dataList, BindingResult bindingResult) {
        // JSR 303 校验通用校验获取失败的数据
        List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();
        if(CollUtil.isNotEmpty(errorMessageList)){
           // System.out.println(errorMessageList.toString());
            return CommonResult.error(errorMessageList.toString());
        }
      //  System.out.println(errorMessageList);
        System.out.println(dataList);
        //入库
        return CommonResult.success();
    }


    /**
     * 注解式导入数据
     * @param dataList
     * @param bindingResult
     * @return
     */
    @PostMapping("/requestExcelImport2")
    public CommonResult<String> requestExcelImport2(@RequestExcel List<ApExcelTestParam> dataList,String excelCustom, BindingResult bindingResult) {
        // JSR 303 校验通用校验获取失败的数据
        List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();
        if(CollUtil.isNotEmpty(errorMessageList)){
            // System.out.println(errorMessageList.toString());
            return CommonResult.error(errorMessageList.toString());
        }
        //  System.out.println(errorMessageList);
        System.out.println(dataList);
        //入库
        return CommonResult.success();
    }



    /**
     * 模拟大批量数据导入

     * @return
     */
    @PostMapping("/upload3")
    public CommonResult<String> upload3(@RequestParam("file") MultipartFile file) throws IOException {
        iApExcelTestService.upload3(file);
        return CommonResult.success();
    }


    /**
     * 模拟大批量数据导入

     * @return
     */
    @PostMapping("/test3")
    public CommonResult<String> test3() throws IOException {
        test2Service.writeData(new ArrayList<ApExcelTestParam>(), 0, 2000);
        return CommonResult.success();
    }
}
