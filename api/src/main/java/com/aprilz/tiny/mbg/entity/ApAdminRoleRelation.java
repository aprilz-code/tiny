package com.aprilz.tiny.mbg.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.aprilz.tiny.mbg.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 后台用户和角色关系表
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
@Data
@TableName("ap_admin_role_relation")
@ApiModel(value = "ApAdminRoleRelation对象", description = "后台用户和角色关系表")
public class ApAdminRoleRelation extends BaseDO {

    @TableId(value = "id")
    @ApiModelProperty(value = "唯一标识")
    @ExcelIgnore
    private Long id;

    @ApiModelProperty("状态：0->无效；1->有效")
    @TableField("status")
    private Boolean status;

    @TableField("admin_id")
    private Long adminId;

    @TableField("role_id")
    private Long roleId;


}
