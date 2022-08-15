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
 * 商品基本信息表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "ap_goods", autoResultMap = true)
@ApiModel(value = "ApGoods对象", description = "商品基本信息表")
public class ApGoods extends BaseEntity<ApGoods> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品编号")
    @TableField("goods_sn")
    private String goodsSn;

    @ApiModelProperty("商品名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("商品所属类目ID")
    @TableField("category_id")
    private Long categoryId;

    @TableField("brand_id")
    private Long brandId;

    @ApiModelProperty("商品宣传图片列表，采用JSON数组格式")
    @TableField(value = "gallery", typeHandler = JsonStringArrayTypeHandler.class)
    private String[] gallery;

    @ApiModelProperty("商品关键字，采用逗号间隔")
    @TableField("keywords")
    private String keywords;

    @ApiModelProperty("商品简介")
    @TableField("brief")
    private String brief;

    @ApiModelProperty("是否上架")
    @TableField("is_on_sale")
    private Boolean isOnSale;

    @TableField("sort_order")
    private Integer sortOrder;

    @ApiModelProperty("商品页面商品图片")
    @TableField("pic_url")
    private String picUrl;

    @ApiModelProperty("商品分享海报")
    @TableField("share_url")
    private String shareUrl;

    @ApiModelProperty("是否新品首发，如果设置则可以在新品首发页面展示")
    @TableField("is_new")
    private Boolean isNew;

    @ApiModelProperty("是否人气推荐，如果设置则可以在人气推荐页面展示")
    @TableField("is_hot")
    private Boolean isHot;

    @ApiModelProperty("商品单位，例如件、盒")
    @TableField("unit")
    private String unit;

    @ApiModelProperty("专柜价格")
    @TableField("counter_price")
    private BigDecimal counterPrice;

    @ApiModelProperty("零售价格")
    @TableField("retail_price")
    private BigDecimal retailPrice;

    @ApiModelProperty("商品详细介绍，是富文本格式")
    @TableField("detail")
    private String detail;

    @ApiModelProperty("默认单规格")
    @TableField("multiple_spec")
    private Boolean multipleSpec;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
