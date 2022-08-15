package com.aprilz.tiny.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 订单取消
 * @author: Aprilz
 * @since: 2022/8/2
 **/
@Data
public class OrderCancelParam {
    @NotNull
    private Long orderId;


}
