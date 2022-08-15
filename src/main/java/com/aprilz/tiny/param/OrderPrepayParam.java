package com.aprilz.tiny.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 订单预支付
 * @author: Aprilz
 * @since: 2022/8/2
 **/
@Data
public class OrderPrepayParam {
    @NotNull
    private Long orderId;


}
