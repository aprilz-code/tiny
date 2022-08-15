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
import java.math.BigDecimal;

/**
 * <p>
 * 品牌商表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_brand")
@ApiModel(value = "ApBrand对象", description = "品牌商表")
public class ApBrand extends BaseEntity<ApBrand> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("品牌商名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("品牌商简介")
    @TableField("`desc`")
    private String desc;

    @ApiModelProperty("品牌商页的品牌商图片")
    @TableField("pic_url")
    private String picUrl;

    @TableField("sort_order")
    private Integer sortOrder;

    @ApiModelProperty("品牌商的商品低价，仅用于页面展示")
    @TableField("floor_price")
    private BigDecimal floorPrice;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
