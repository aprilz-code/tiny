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
 * 商品参数表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_goods_attribute")
@ApiModel(value = "ApGoodsAttribute对象", description = "商品参数表")
public class ApGoodsAttribute extends BaseEntity<ApGoodsAttribute> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品表的商品ID")
    @TableField("goods_id")
    private Long goodsId;

    @ApiModelProperty("商品参数名称")
    @TableField("attribute")
    private String attribute;

    @ApiModelProperty("商品参数值")
    @TableField("value")
    private String value;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
