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
import java.util.Date;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_order")
@ApiModel(value = "ApOrder对象", description = "订单表")
public class ApOrder extends BaseEntity<ApOrder> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户表的用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("订单编号")
    @TableField("order_sn")
    private String orderSn;

    @ApiModelProperty("订单状态")
    @TableField("order_status")
    private Integer orderStatus;

    @ApiModelProperty("售后状态，0是可申请，1是用户已申请，2是管理员审核通过，3是管理员退款成功，4是管理员审核拒绝，5是用户已取消")
    @TableField("aftersale_status")
    private Integer aftersaleStatus;

    @ApiModelProperty("收货人名称")
    @TableField("consignee")
    private String consignee;

    @ApiModelProperty("收货人手机号")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty("收货具体地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("用户订单留言")
    @TableField("message")
    private String message;

    @ApiModelProperty("商品总费用")
    @TableField("goods_price")
    private BigDecimal goodsPrice;

    @ApiModelProperty("配送费用")
    @TableField("freight_price")
    private BigDecimal freightPrice;

    @ApiModelProperty("优惠券减免")
    @TableField("coupon_price")
    private BigDecimal couponPrice;

    @ApiModelProperty("用户积分减免")
    @TableField("integral_price")
    private BigDecimal integralPrice;

    @ApiModelProperty("团购优惠价减免")
    @TableField("groupon_price")
    private BigDecimal grouponPrice;

    @ApiModelProperty("订单费用， = goods_price + freight_price - coupon_price")
    @TableField("order_price")
    private BigDecimal orderPrice;

    @ApiModelProperty("实付费用， = order_price - integral_price")
    @TableField("actual_price")
    private BigDecimal actualPrice;

    @ApiModelProperty("微信付款编号")
    @TableField("pay_id")
    private String payId;

    @ApiModelProperty("微信付款时间")
    @TableField("pay_time")
    private Date payTime;

    @ApiModelProperty("发货编号")
    @TableField("ship_sn")
    private String shipSn;

    @ApiModelProperty("发货快递公司")
    @TableField("ship_channel")
    private String shipChannel;

    @ApiModelProperty("发货开始时间")
    @TableField("ship_time")
    private Date shipTime;

    @ApiModelProperty("实际退款金额，（有可能退款金额小于实际支付金额）")
    @TableField("refund_amount")
    private BigDecimal refundAmount;

    @ApiModelProperty("退款方式")
    @TableField("refund_type")
    private String refundType;

    @ApiModelProperty("退款备注")
    @TableField("refund_content")
    private String refundContent;

    @ApiModelProperty("退款时间")
    @TableField("refund_time")
    private Date refundTime;

    @ApiModelProperty("用户确认收货时间")
    @TableField("confirm_time")
    private Date confirmTime;

    @ApiModelProperty("待评价订单商品数量")
    @TableField("comments")
    private Integer comments;

    @ApiModelProperty("订单关闭时间")
    @TableField("end_time")
    private Date endTime;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
