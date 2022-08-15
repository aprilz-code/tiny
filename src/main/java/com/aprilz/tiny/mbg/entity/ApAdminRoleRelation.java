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

/**
 * <p>
 * 后台用户和角色关系表
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_admin_role_relation")
@ApiModel(value = "ApAdminRoleRelation对象", description = "后台用户和角色关系表")
public class ApAdminRoleRelation extends BaseEntity<ApAdminRoleRelation> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("状态：0->无效；1->有效")
    @TableField("status")
    @TableLogic
    private Boolean status;

    @TableField("admin_id")
    private Long adminId;

    @TableField("role_id")
    private Long roleId;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
