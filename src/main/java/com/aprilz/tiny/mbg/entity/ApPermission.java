package com.aprilz.tiny.mbg.entity;

import com.aprilz.tiny.mbg.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 后台用户权限表
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_permission")
@ApiModel(value = "ApPermission对象", description = "后台用户权限表")
public class ApPermission extends BaseEntity<ApPermission> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("父级权限id")
    @TableField("pid")
    private Long pid;

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("权限值")
    @TableField("value")
    private String value;

    @ApiModelProperty("图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("前端资源路径")
    @TableField("uri")
    private String uri;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
