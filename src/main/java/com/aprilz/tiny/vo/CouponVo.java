package com.aprilz.tiny.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 优惠券
 * @author: aprilz
 * @since: 2022/7/15
 **/
@Data
public class CouponVo implements Serializable {

    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("coupon的ID")
    private Long cid;
    @ApiModelProperty("优惠券名称")
    private String name;
    @ApiModelProperty("优惠券介绍，通常是显示优惠券使用限制文字")
    private String desc;
    @ApiModelProperty("优惠券标签，例如新人专用")
    private String tag;
    @ApiModelProperty("最少消费金额才能使用优惠券。")
    private BigDecimal min;
    @ApiModelProperty("优惠金额，")
    private BigDecimal discount;
    @ApiModelProperty("使用券开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @ApiModelProperty("使用券截至时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @ApiModelProperty("是否可用")
    private boolean available;
}
