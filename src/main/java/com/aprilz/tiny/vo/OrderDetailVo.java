package com.aprilz.tiny.vo;

import com.aprilz.tiny.mall.utils.OrderHandleOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 订单详情vo
 * @author: Aprilz
 * @since: 2022/8/2
 **/
@Data
public class OrderDetailVo implements Serializable {
    private Long id;

    @ApiModelProperty("订单编号")
    private String orderSn;
    @ApiModelProperty("用户订单留言")
    private String message;
    private Date createTime;

    @ApiModelProperty("收货人名称")
    private String consignee;

    @ApiModelProperty("收货人手机号")
    private String mobile;

    @ApiModelProperty("收货具体地址")
    private String address;

    @ApiModelProperty("商品总费用")
    private BigDecimal goodsPrice;

    @ApiModelProperty("优惠券减免")
    private BigDecimal couponPrice;


    @ApiModelProperty("配送费用")
    private BigDecimal freightPrice;

    @ApiModelProperty("实付费用， = order_price - integral_price")
    private BigDecimal actualPrice;


    private String orderStatusText;

    //根据状态处理---订单可操作的行为
    private OrderHandleOption handleOption;

    @ApiModelProperty("售后状态，0是可申请，1是用户已申请，2是管理员审核通过，3是管理员退款成功，4是管理员审核拒绝，5是用户已取消")
    private Integer aftersaleStatus;

    @ApiModelProperty("发货快递公司 shipChannel")
    private String expCode;

    @ApiModelProperty("发货快递公司 ")
    private String expName;

    @ApiModelProperty("发货快递公司  shipSn")
    private String expNo;


}
