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
 * 行政区域表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Data
@TableName("ap_region")
@ApiModel(value = "ApRegion对象", description = "行政区域表")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ApRegion extends BaseEntity {

    @TableId(value = "id")
    @ApiModelProperty(value = "唯一标识")
    private Long id;

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


}
