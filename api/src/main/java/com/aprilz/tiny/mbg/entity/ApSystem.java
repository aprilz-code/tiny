package com.aprilz.tiny.mbg.entity;

import com.aprilz.tiny.mbg.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 系统配置表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-19
 */
@Data
@TableName("ap_system")
@ApiModel(value = "ApSystem对象", description = "系统配置表")
public class ApSystem extends BaseDO {

    @TableId(value = "id")
    @ApiModelProperty(value = "唯一标识")
    private Long id;

    @ApiModelProperty("系统配置名")
    @TableField("key_name")
    private String keyName;

    @ApiModelProperty("系统配置值")
    @TableField("key_value")
    private String keyValue;

    @TableField("description")
    private String description;


}
