package com.aprilz.tiny.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 优惠券领取
 * @author: Aprilz
 * @since: 2022/7/27
 **/
@Data
public class ReceiveParam {

    @NotNull
    private Long couponId;
}
