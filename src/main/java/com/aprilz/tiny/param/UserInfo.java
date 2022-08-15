package com.aprilz.tiny.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("微信用户信息")
public class UserInfo {
    @ApiModelProperty(value = "微信用户昵称")
    private String nickName;
    @ApiModelProperty(value = "微信用户头像")
    private String avatarUrl;
    @ApiModelProperty(value = "微信用户国家")
    private String country;
    @ApiModelProperty(value = "微信用户省份")
    private String province;
    @ApiModelProperty(value = "微信用户城市")
    private String city;
    @ApiModelProperty(value = "微信用户语言")
    private String language;
    @ApiModelProperty(value = "微信用户性别")
    private Byte gender;


}
