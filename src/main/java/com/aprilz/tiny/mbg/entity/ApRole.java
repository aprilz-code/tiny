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
 * 后台用户角色表
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_role")
@ApiModel(value = "ApRole对象", description = "后台用户角色表")
public class ApRole extends BaseEntity<ApRole> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("后台用户数量")
    @TableField("admin_count")
    private Integer adminCount;

    @TableField("sort")
    private Integer sort;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
