package com.aprilz.tiny.vo;

import com.aprilz.tiny.mbg.entity.ApFootprint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 用户浏览足迹vo
 * @author: Aprilz
 * @since: 2022/7/28
 **/
@Data
public class ApFootprintVo extends ApFootprint {


    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品简介")
    private String brief;


    @ApiModelProperty("商品页面商品图片")
    private String picUrl;

    @ApiModelProperty("零售价格")
    private BigDecimal retailPrice;


}
