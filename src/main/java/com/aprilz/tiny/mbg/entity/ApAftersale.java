package com.aprilz.tiny.mbg.entity;

import com.aprilz.tiny.component.JsonStringArrayTypeHandler;
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
 * 售后表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "ap_aftersale", autoResultMap = true)
@ApiModel(value = "ApAftersale对象", description = "售后表")
public class ApAftersale extends BaseEntity<ApAftersale> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("售后编号")
    @TableField("aftersale_sn")
    private String aftersaleSn;

    @ApiModelProperty("订单ID")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("售后类型，0是未收货退款，1是已收货（无需退货）退款，2用户退货退款")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("退款原因")
    @TableField("reason")
    private String reason;

    @ApiModelProperty("退款金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty("退款凭证图片链接数组")
    @TableField(value = "pictures", typeHandler = JsonStringArrayTypeHandler.class)
    private String[] pictures;

    @ApiModelProperty("退款说明")
    @TableField("comment")
    private String comment;

    @ApiModelProperty("售后状态，0是可申请，1是用户已申请，2是管理员审核通过，3是管理员退款成功，4是管理员审核拒绝，5是用户已取消")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("管理员操作时间")
    @TableField("handle_time")
    private Date handleTime;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
