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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ApAdminRoleRelation extends BaseEntity {

    @TableId(value = "id")
    @ApiModelProperty(value = "唯一标识")
    private Long id;

    @ApiModelProperty("状态：0->无效；1->有效")
    @TableField("status")
    private Boolean status;

    @TableField("admin_id")
    private Long adminId;

    @TableField("role_id")
    private Long roleId;


}
