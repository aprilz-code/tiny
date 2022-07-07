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
 * 后台用户角色和权限关系表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-07
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_role_permission_relation")
@ApiModel(value = "ApRolePermissionRelationEntity对象", description = "后台用户角色和权限关系表")
public class ApRolePermissionRelationEntity extends BaseEntity<ApRolePermissionRelationEntity> {

    private static final long serialVersionUID = 1L;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private Date updateTime;

    @TableField("role_id")
    private Long roleId;

    @TableField("permission_id")
    private Long permissionId;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
