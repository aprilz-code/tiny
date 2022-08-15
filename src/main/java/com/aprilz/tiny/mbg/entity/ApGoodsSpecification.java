package com.aprilz.tiny.mbg.entity;

import com.aprilz.tiny.mbg.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品规格表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_goods_specification")
@ApiModel(value = "ApGoodsSpecification对象", description = "商品规格表")
public class ApGoodsSpecification extends BaseEntity<ApGoodsSpecification> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品表的商品ID")
    @TableField("goods_id")
    private Long goodsId;

    @ApiModelProperty("商品规格名称")
    @TableField("specification")
    private String specification;

    @ApiModelProperty("商品规格值")
    @TableField("value")
    private String value;

    @ApiModelProperty("商品规格图片")
    @TableField("pic_url")
    private String picUrl;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
