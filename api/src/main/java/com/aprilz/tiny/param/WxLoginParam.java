package com.aprilz.tiny.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @description: wx登录param
 * @author: aprilz
 * @since: 2022/7/14
 **/
@Data
@ApiModel("微信登录Param")
public class WxLoginParam {
    /*https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html */
    @ApiModelProperty(value = "登录时获取的 code，可通过wx.login获取", required = true)
    @NotEmpty(message = "code不能为空")
    private String code;
    @ApiModelProperty(value = "微信用户信息")
    private UserInfo userInfo;
}
