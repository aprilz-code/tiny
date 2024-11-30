package com.aprilz.tiny.controller;

import com.aprilz.tiny.common.api.CommonResult;
import com.aprilz.tiny.common.storage.StorageService;
import com.aprilz.tiny.common.utils.CharUtil;
import com.aprilz.tiny.dto.ApAdminLoginParam;
import com.aprilz.tiny.model.ApStorage;
import com.aprilz.tiny.service.IApStorageService;
import com.aprilz.tiny.service.IApUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 文件存储表 前端控制器
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@RestController
@RequestMapping("/test")
public class ApTestController {

    @Resource
    private IApUserService apUserService;
    @ApiOperation(value = "testTransactionalEventListener")
    @RequestMapping(value = "/testTransactionalEventListener", method = RequestMethod.GET)
    public CommonResult testTransactionalEventListener() {
         apUserService.testTransactionalEventListener();
        return CommonResult.success("s");
    }

}
