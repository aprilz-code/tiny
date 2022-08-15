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
 * 收货地址表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_address")
@ApiModel(value = "ApAddress对象", description = "收货地址表")
public class ApAddress extends BaseEntity<ApAddress> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("收货人名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("用户表的用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("行政区域表的省ID")
    @TableField("province")
    private String province;

    @ApiModelProperty("行政区域表的市ID")
    @TableField("city")
    private String city;

    @ApiModelProperty("行政区域表的区县ID")
    @TableField("county")
    private String county;

    @ApiModelProperty("详细收货地址")
    @TableField("address_detail")
    private String addressDetail;

    @ApiModelProperty("地区编码")
    @TableField("area_code")
    private String areaCode;

    @ApiModelProperty("邮政编码")
    @TableField("postal_code")
    private String postalCode;

    @ApiModelProperty("手机号码")
    @TableField("tel")
    private String tel;

    @ApiModelProperty("是否默认地址")
    @TableField("is_default")
    private Boolean isDefault;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
