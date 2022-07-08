package com.aprilz.tiny.controller;


import com.aprilz.tiny.common.api.CommonResult;
import com.aprilz.tiny.dto.ApAdminLoginParam;
import com.aprilz.tiny.mbg.entity.ApAdminEntity;
import com.aprilz.tiny.mbg.entity.ApPermissionEntity;
import com.aprilz.tiny.service.IApAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员登录注册管理Controller
 * Created by aprilz on 2018/8/3.
 */
@Controller
@Api(tags = "ApMemberController", description = "会员登录注册管理")
@RequestMapping("/sso")
public class ApMemberController {
    @Autowired
    private IApAdminService adminService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<ApAdminEntity> register(@RequestBody ApAdminEntity ApAdminParam, BindingResult result) {
        ApAdminEntity ApAdmin = adminService.register(ApAdminParam);
        if (ApAdmin == null) {
            CommonResult.error();
        }
        return CommonResult.success(ApAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody ApAdminLoginParam ApAdminLoginParam, BindingResult result) {
        String token = adminService.login(ApAdminLoginParam.getUsername(), ApAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取用户所有权限（包括+-权限）")
    @RequestMapping(value = "/permission/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('sso:permission:read')")
    public CommonResult<List<ApPermissionEntity>> getPermissionList(@PathVariable Long adminId) {
        List<ApPermissionEntity> permissionList = adminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }

}
