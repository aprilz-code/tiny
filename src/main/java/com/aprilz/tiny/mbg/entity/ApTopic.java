package com.aprilz.tiny.mbg.entity;

import com.aprilz.tiny.component.JsonLongArrayTypeHandler;
import com.aprilz.tiny.mbg.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 专题表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "ap_topic", autoResultMap = true)
@ApiModel(value = "ApTopic对象", description = "专题表")
public class ApTopic extends BaseEntity<ApTopic> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("专题标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("专题子标题")
    @TableField("subtitle")
    private String subtitle;

    @ApiModelProperty("专题内容，富文本格式")
    @TableField("content")
    private String content;

    @ApiModelProperty("专题相关商品最低价")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("专题阅读量")
    @TableField("read_count")
    private String readCount;

    @ApiModelProperty("专题图片")
    @TableField("pic_url")
    private String picUrl;

    @ApiModelProperty("排序")
    @TableField("sort_order")
    private Integer sortOrder;

    @ApiModelProperty("专题相关商品，采用JSON数组格式")
    @TableField(value = "goods", typeHandler = JsonLongArrayTypeHandler.class)
    private Long[] goods;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
