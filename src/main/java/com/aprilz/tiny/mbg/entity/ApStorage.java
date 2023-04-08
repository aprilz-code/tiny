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
 * 文件存储表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Data
@TableName("ap_storage")
@ApiModel(value = "ApStorage对象", description = "文件存储表")
public class ApStorage extends BaseEntity {

    @TableId(value = "id")
    @ApiModelProperty(value = "唯一标识")
    @ExcelIgnore
    private Long id;

    @ApiModelProperty("文件的唯一索引")
    @TableField("key")
    private String key;

    @ApiModelProperty("文件名")
    @TableField("name")
    private String name;

    @ApiModelProperty("文件类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("文件大小")
    @TableField("size")
    private Integer size;

    @ApiModelProperty("文件访问链接")
    @TableField("url")
    private String url;




}
