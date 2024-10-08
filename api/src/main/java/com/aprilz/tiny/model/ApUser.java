package com.aprilz.tiny.model;


import com.aprilz.tiny.component.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-13
 */
@Data
@TableName("ap_user")
@ApiModel(value = "ApUser对象", description = "用户表")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ApUser extends BaseEntity {

    @TableId(value = "id")
    @ApiModelProperty(value = "唯一标识")
    private Long id;

    @ApiModelProperty("用户名称")
    @TableField("username")
    private String username;

    @ApiModelProperty("用户密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("性别：0 未知， 1男， 1 女")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty("生日")
    @TableField("birthday")
    private Date birthday;

    @ApiModelProperty("最近一次登录时间")
    @TableField("last_login_time")
    private Date lastLoginTime;

    @ApiModelProperty("最近一次登录IP地址")
    @TableField("last_login_ip")
    private String lastLoginIp;

    @ApiModelProperty("0 普通用户，1 VIP用户，2 高级VIP用户")
    @TableField("user_level")
    private Integer userLevel;

    @ApiModelProperty("用户昵称或网络名称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("用户手机号码")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty("用户头像图片")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("微信登录openid")
    @TableField("wx_openid")
    private String wxOpenid;

    @ApiModelProperty("微信登录会话KEY")
    @TableField("session_key")
    private String sessionKey;

    @ApiModelProperty("帐号启用状态：0->禁用；1->启用")
    @TableField("status")
    private Integer status;


}
