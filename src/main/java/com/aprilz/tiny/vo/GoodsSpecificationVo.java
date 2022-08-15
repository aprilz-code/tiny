package com.aprilz.tiny.vo;

import com.aprilz.tiny.mbg.entity.ApGoodsSpecification;
import lombok.Data;

import java.util.List;

/**
 * @description: 商品规格vo
 * @author: Aprilz
 * @since: 2022/7/20
 **/
@Data
public class GoodsSpecificationVo {

    private String name;

    private List<ApGoodsSpecification> valueList;

}
