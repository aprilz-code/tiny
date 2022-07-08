package com.aprilz.tiny.controller.member;

import com.aprilz.tiny.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: aprilz
 * @since: 2022/7/8
 **/
@RestController
@Api(tags = "会员登录注册管理")
@RequestMapping("/member")
public class MemberController {

    @ApiOperation(value = "登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query")
    })
    @PostMapping("/userLogin")
    public CommonResult<Object> userLogin(@NotNull(message = "用户名不能为空") @RequestParam String username,
                                          @NotNull(message = "密码不能为空") @RequestParam String password,
                                          @RequestHeader String uuid) {
        //   verificationService.check(uuid, VerificationEnums.LOGIN);
        //   return ResultUtil.data(this.memberService.usernameLogin(username, password));
        return null;
    }

}
