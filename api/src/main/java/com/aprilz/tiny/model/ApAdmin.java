package com.aprilz.tiny.model;


import com.aprilz.desensitize.core.annotations.Desensitize;
import com.aprilz.desensitize.core.enums.DesensitizeRuleEnums;
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
 * 后台用户表
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
@TableName("ap_admin")
@ApiModel(value = "ApAdmin对象", description = "后台用户表")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ApAdmin extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    @ApiModelProperty(value = "唯一标识")
    private Long id;

    @ApiModelProperty("帐号启用状态：0->禁用；1->启用")
    @TableField("status")
    private Boolean status;

    @TableField("username")
    private String username;

    @TableField("password")
    @Desensitize(rule = DesensitizeRuleEnums.PASSWORD)
    private String password;

    @ApiModelProperty("手机")
    @TableField("mobile")
    @Desensitize(rule = DesensitizeRuleEnums.MOBILE_PHONE)
    private String mobile;

    @ApiModelProperty("性别：0->女；1->男 2-未知")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("邮箱")
    @TableField("email")
    @Desensitize(rule = DesensitizeRuleEnums.EMAIL)
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


}
