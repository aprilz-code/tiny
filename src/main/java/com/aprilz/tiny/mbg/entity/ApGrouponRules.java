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
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 团购规则表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_groupon_rules")
@ApiModel(value = "ApGrouponRules对象", description = "团购规则表")
public class ApGrouponRules extends BaseEntity<ApGrouponRules> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品表的商品ID")
    @TableField("goods_id")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    @TableField("goods_name")
    private String goodsName;

    @ApiModelProperty("商品图片或者商品货品图片")
    @TableField("pic_url")
    private String picUrl;

    @ApiModelProperty("优惠金额")
    @TableField("discount")
    private BigDecimal discount;

    @ApiModelProperty("达到优惠条件的人数")
    @TableField("discount_member")
    private Integer discountMember;

    @ApiModelProperty("团购过期时间")
    @TableField("expire_time")
    private Date expireTime;

    @ApiModelProperty("团购规则状态，正常上线则0，到期自动下线则1，管理手动下线则2")
    @TableField("status")
    private Integer status;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
