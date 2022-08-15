package com.aprilz.tiny.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 订单退款
 * @author: Aprilz
 * @since: 2022/8/2
 **/
@Data
public class OrderRefundParam {
    @NotNull
    private Long orderId;


}
