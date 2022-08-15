package com.aprilz.tiny.mbg.entity;

import com.aprilz.tiny.mbg.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 后台用户角色和权限关系表
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_role_permission_relation")
@ApiModel(value = "ApRolePermissionRelation对象", description = "后台用户角色和权限关系表")
public class ApRolePermissionRelation extends BaseEntity<ApRolePermissionRelation> {

    private static final long serialVersionUID = 1L;

    @TableField("role_id")
    private Long roleId;

    @TableField("permission_id")
    private Long permissionId;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
