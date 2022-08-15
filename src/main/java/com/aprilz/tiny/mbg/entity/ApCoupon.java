package com.aprilz.tiny.mbg.entity;

import com.aprilz.tiny.component.JsonLongArrayTypeHandler;
import com.aprilz.tiny.mbg.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 优惠券信息及规则表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-14
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "ap_coupon", autoResultMap = true)
@ApiModel(value = "ApCoupon对象", description = "优惠券信息及规则表")
public class ApCoupon extends BaseEntity<ApCoupon> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("优惠券名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("优惠券介绍，通常是显示优惠券使用限制文字")
    @TableField("`desc`")
    private String desc;

    @ApiModelProperty("优惠券标签，例如新人专用")
    @TableField("tag")
    private String tag;

    @ApiModelProperty("优惠券数量，如果是0，则是无限量")
    @TableField("total")
    private Integer total;

    @ApiModelProperty("优惠金额，")
    @TableField("discount")
    private BigDecimal discount;

    @ApiModelProperty("最少消费金额才能使用优惠券。")
    @TableField("`min`")
    private BigDecimal min;

    @ApiModelProperty("用户领券限制数量，如果是0，则是不限制；默认是1，限领一张.")
    @TableField("`limit`")
    private Integer limit;

    @ApiModelProperty("优惠券赠送类型，如果是0则通用券，用户领取；如果是1，则是注册赠券；如果是2，则是优惠券码兑换；")
    @TableField("`type`")
    private Integer type;

    @ApiModelProperty("商品限制类型，如果0则全商品，如果是1则是类目限制，如果是2则是商品限制。")
    @TableField("goods_type")
    private Integer goodsType;

    @ApiModelProperty("商品限制值，goods_type如果是0则空集合，如果是1则是类目集合，如果是2则是商品集合。")
    @TableField(value = "goods_value", typeHandler = JsonLongArrayTypeHandler.class)
    private Long[] goodsValue;

    @ApiModelProperty("优惠券兑换码")
    @TableField("`code`")
    private String code;

    @ApiModelProperty("有效时间限制，如果是0，则基于领取时间的有效天数days；如果是1，则start_time和end_time是优惠券有效期；")
    @TableField("time_type")
    private Integer timeType;

    @ApiModelProperty("基于领取时间的有效天数days。")
    @TableField("days")
    private Integer days;

    @ApiModelProperty("使用券开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("start_time")
    private Date startTime;

    @ApiModelProperty("使用券截至时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("end_time")
    private Date endTime;

    @ApiModelProperty("帐号启用状态：0->禁用；1->启用")
    @TableField("status")
    private Integer status;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
