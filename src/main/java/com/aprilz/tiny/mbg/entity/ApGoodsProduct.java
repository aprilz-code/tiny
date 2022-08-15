package com.aprilz.tiny.mbg.entity;

import com.aprilz.tiny.component.JsonStringArrayTypeHandler;
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
 * 商品货品表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "ap_goods_product", autoResultMap = true)
@ApiModel(value = "ApGoodsProduct对象", description = "商品货品表")
public class ApGoodsProduct extends BaseEntity<ApGoodsProduct> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品表的商品ID")
    @TableField("goods_id")
    private Long goodsId;

    @ApiModelProperty("商品规格值列表，采用JSON数组格式")
    @TableField(value = "specifications", typeHandler = JsonStringArrayTypeHandler.class)
    private String[] specifications;

    @ApiModelProperty("商品货品价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("商品货品数量")
    @TableField("number")
    private Integer number;

    @ApiModelProperty("商品货品图片")
    @TableField("url")
    private String url;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
