package com.aprilz.tiny.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 订单提交
 * @author: Aprilz
 * @since: 2022/8/2
 **/
@Data
public class OrderSubmitParam {
    @NotNull
    private Long cartId;
    @NotNull
    private Long addressId;
    @NotNull
    private Long couponId;
    @NotNull
    private Long userCouponId;
    @NotBlank
    private String message;
    //团购规则ID
    @NotNull
    private Long grouponRulesId;
    //参与的团购
    @NotNull
    private Long grouponLinkId;

}
