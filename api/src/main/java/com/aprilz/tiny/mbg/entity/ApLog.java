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
 * 操作日志表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Data
@TableName("ap_log")
@ApiModel(value = "ApLog对象", description = "操作日志表")
public class ApLog extends BaseDO {

    @TableId(value = "id")
    @ApiModelProperty(value = "唯一标识")
    private Long id;

    @ApiModelProperty("管理员")
    @TableField("admin")
    private String admin;

    @ApiModelProperty("管理员地址")
    @TableField("ip")
    private String ip;

    @ApiModelProperty("操作分类")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("操作动作")
    @TableField("action")
    private String action;

    @ApiModelProperty("操作状态")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty("操作结果，或者成功消息，或者失败消息")
    @TableField("result")
    private String result;

    @ApiModelProperty("补充信息")
    @TableField("comment")
    private String comment;


}
