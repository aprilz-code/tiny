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
 * 行政区域表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_region")
@ApiModel(value = "ApRegion对象", description = "行政区域表")
public class ApRegion extends BaseEntity<ApRegion> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("行政区域父ID，例如区县的pid指向市，市的pid指向省，省的pid则是0")
    @TableField("pid")
    private Long pid;

    @ApiModelProperty("行政区域名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("行政区域类型，如如1则是省， 如果是2则是市，如果是3则是区县")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("行政区域编码")
    @TableField("code")
    private Integer code;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
