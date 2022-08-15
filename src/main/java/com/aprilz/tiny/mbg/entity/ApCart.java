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
 * 购物车商品表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "ap_cart", autoResultMap = true)
@ApiModel(value = "ApCart对象", description = "购物车商品表")
public class ApCart extends BaseEntity<ApCart> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户表的用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("商品表的商品ID")
    @TableField("goods_id")
    private Long goodsId;

    @ApiModelProperty("商品编号")
    @TableField("goods_sn")
    private String goodsSn;

    @ApiModelProperty("商品名称")
    @TableField("goods_name")
    private String goodsName;

    @ApiModelProperty("商品货品表的货品ID")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty("商品货品的价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("商品货品的数量")
    @TableField("number")
    private Integer number;

    @ApiModelProperty("商品规格值列表，采用JSON数组格式")
    @TableField(value = "specifications", typeHandler = JsonStringArrayTypeHandler.class)
    private String[] specifications;

    @ApiModelProperty("购物车中商品是否选择状态")
    @TableField("checked")
    private Boolean checked;

    @ApiModelProperty("商品图片或者商品货品图片")
    @TableField("pic_url")
    private String picUrl;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
