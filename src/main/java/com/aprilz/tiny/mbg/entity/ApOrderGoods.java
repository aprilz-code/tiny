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
 * 订单商品表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "ap_order_goods", autoResultMap = true)
@ApiModel(value = "ApOrderGoods对象", description = "订单商品表")
public class ApOrderGoods extends BaseEntity<ApOrderGoods> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单表的订单ID")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty("商品表的商品ID")
    @TableField("goods_id")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    @TableField("goods_name")
    private String goodsName;

    @ApiModelProperty("商品编号")
    @TableField("goods_sn")
    private String goodsSn;

    @ApiModelProperty("商品货品表的货品ID")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty("商品货品的购买数量")
    @TableField("number")
    private Integer number;

    @ApiModelProperty("商品货品的售价")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("商品货品的规格列表")
    @TableField(value = "specifications", typeHandler = JsonStringArrayTypeHandler.class)
    private String[] specifications;

    @ApiModelProperty("商品货品图片或者商品图片")
    @TableField("pic_url")
    private String picUrl;

    @ApiModelProperty("订单商品评论，如果是-1，则超期不能评价；如果是0，则可以评价；如果其他值，则是comment表里面的评论ID。")
    @TableField("comment")
    private Long comment;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
