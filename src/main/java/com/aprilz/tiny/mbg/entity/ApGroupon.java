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
import java.util.Date;

/**
 * <p>
 * 团购活动表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_groupon")
@ApiModel(value = "ApGroupon对象", description = "团购活动表")
public class ApGroupon extends BaseEntity<ApGroupon> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("关联的订单ID")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty("如果是开团用户，则groupon_id是0；如果是参团用户，则groupon_id是团购活动ID")
    @TableField("groupon_id")
    private Long grouponId;

    @ApiModelProperty("团购规则ID，关联ap_groupon_rules表ID字段")
    @TableField("rules_id")
    private Long rulesId;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("团购分享图片地址")
    @TableField("share_url")
    private String shareUrl;

    @ApiModelProperty("开团用户ID")
    @TableField("creator_user_id")
    private Long creatorUserId;

    @ApiModelProperty("开团时间")
    @TableField("creator_user_time")
    private Date creatorUserTime;

    @ApiModelProperty("团购活动状态，开团未支付则0，开团中则1，开团失败则2")
    @TableField("status")
    private Integer status;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
