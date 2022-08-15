package com.aprilz.tiny.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 优惠券兑换
 * @author: Aprilz
 * @since: 2022/7/27
 **/
@Data
public class ExchangeParam {

    @NotBlank
    private String code;
}
