package com.aprilz.tiny.mbg.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.aprilz.tiny.mbg.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限)
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
@Data
@TableName("ap_admin_permission_relation")
@ApiModel(value = "ApAdminPermissionRelation对象", description = "后台用户和权限关系表(除角色中定义的权限以外的加减权限)")
public class ApAdminPermissionRelation extends BaseEntity {


    @TableId(value = "id")
    @ApiModelProperty(value = "唯一标识")
    @ExcelIgnore
    private Long id;

    private static final long serialVersionUID = 1L;

    @TableField("admin_id")
    private Long adminId;

    @TableField("permission_id")
    private Long permissionId;

    @TableField("type")
    private Integer type;




}
