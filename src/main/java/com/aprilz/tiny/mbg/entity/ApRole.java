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
 * 后台用户角色表
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
@Data
@TableName("ap_role")
@ApiModel(value = "ApRole对象", description = "后台用户角色表")
public class ApRole extends BaseEntity {

    @TableId(value = "id")
    @ApiModelProperty(value = "唯一标识")
    @ExcelIgnore
    private Long id;

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




}
