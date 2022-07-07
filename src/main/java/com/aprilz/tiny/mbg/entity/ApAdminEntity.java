package com.aprilz.tiny.mbg.entity;

import com.aprilz.tiny.mbg.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-07
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_admin")
@ApiModel(value = "ApAdminEntity对象", description = "后台用户表")
public class ApAdminEntity extends BaseEntity<ApAdminEntity> {

    private static final long serialVersionUID = 1L;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private Date updateTime;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @ApiModelProperty("头像")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty("备注信息")
    @TableField("note")
    private String note;

    @ApiModelProperty("最后登录时间")
    @TableField("login_time")
    private Date loginTime;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
