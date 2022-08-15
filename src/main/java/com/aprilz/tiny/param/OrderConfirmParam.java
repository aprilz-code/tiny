package com.aprilz.tiny.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 订单确认收货
 * @author: Aprilz
 * @since: 2022/8/2
 **/
@Data
public class OrderConfirmParam {
    @NotNull
    private Long orderId;


}
