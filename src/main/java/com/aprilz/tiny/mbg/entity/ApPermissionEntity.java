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
 * 后台用户权限表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-07
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_permission")
@ApiModel(value = "ApPermissionEntity对象", description = "后台用户权限表")
public class ApPermissionEntity extends BaseEntity<ApPermissionEntity> {

    private static final long serialVersionUID = 1L;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private Date updateTime;

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
