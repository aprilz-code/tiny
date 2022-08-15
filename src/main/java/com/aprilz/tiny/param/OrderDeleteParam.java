package com.aprilz.tiny.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 订单删除
 * @author: Aprilz
 * @since: 2022/8/2
 **/
@Data
public class OrderDeleteParam {
    @NotNull
    private Long orderId;


}
