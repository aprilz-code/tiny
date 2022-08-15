package com.aprilz.tiny.mbg.entity;

import com.aprilz.tiny.mbg.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_admin")
@ApiModel(value = "ApAdmin对象", description = "后台用户表")
public class ApAdmin extends BaseEntity<ApAdmin> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("帐号启用状态：0->禁用；1->启用")
    @TableField("status")
    @TableLogic
    private Boolean status;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @ApiModelProperty("手机")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty("性别：0->女；1->男 2-未知")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty("最后登录时间")
    @TableField("login_time")
    private Date loginTime;

    @ApiModelProperty("备注")
    @TableField("description")
    private String description;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
