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
 * 系统配置表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_system")
@ApiModel(value = "ApSystem对象", description = "系统配置表")
public class ApSystem extends BaseEntity<ApSystem> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("系统配置名")
    @TableField("key_name")
    private String keyName;

    @ApiModelProperty("系统配置值")
    @TableField("key_value")
    private String keyValue;

    @TableField("description")
    private String description;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
