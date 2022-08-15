package com.aprilz.tiny.mbg.entity;

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
import java.util.Date;

/**
 * <p>
 * 优惠券用户使用表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-14
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_coupon_user")
@ApiModel(value = "ApCouponUser对象", description = "优惠券用户使用表")
public class ApCouponUser extends BaseEntity<ApCouponUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("优惠券状态， 1可用  2 已用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("优惠券ID")
    @TableField("coupon_id")
    private Long couponId;

    @ApiModelProperty("使用时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("used_time")
    private Date usedTime;

    @ApiModelProperty("有效期开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("start_time")
    private Date startTime;

    @ApiModelProperty("有效期截至时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("end_time")
    private Date endTime;

    @ApiModelProperty("订单ID")
    @TableField("order_id")
    private Long orderId;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
