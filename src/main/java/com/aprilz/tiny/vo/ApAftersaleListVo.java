package com.aprilz.tiny.vo;

import com.aprilz.tiny.mbg.entity.ApAftersale;
import com.aprilz.tiny.mbg.entity.ApOrderGoods;
import lombok.Data;

import java.util.List;

/**
 * @description: 售后列表vo
 * @author: Aprilz
 * @since: 2022/7/25
 **/
@Data
public class ApAftersaleListVo extends ApAftersale {


    private List<ApOrderGoods> goodsList;


}
