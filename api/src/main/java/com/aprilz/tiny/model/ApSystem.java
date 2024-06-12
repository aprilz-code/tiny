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
 * 系统配置表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-19
 */
@Data
@TableName("ap_system")
@ApiModel(value = "ApSystem对象", description = "系统配置表")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ApSystem extends BaseEntity {

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
