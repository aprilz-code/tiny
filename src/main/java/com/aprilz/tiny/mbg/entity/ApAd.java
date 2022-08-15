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
 * 广告表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_ad")
@ApiModel(value = "ApAd对象", description = "广告表")
public class ApAd extends BaseEntity<ApAd> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("广告标题")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("所广告的商品页面或者活动页面链接地址")
    @TableField("link")
    private String link;

    @ApiModelProperty("广告宣传图片")
    @TableField("url")
    private String url;

    @ApiModelProperty("广告位置：1则是首页")
    @TableField("position")
    private Integer position;

    @ApiModelProperty("活动内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("广告开始时间")
    @TableField("start_time")
    private Date startTime;

    @ApiModelProperty("广告结束时间")
    @TableField("end_time")
    private Date endTime;

    @ApiModelProperty("是否启动")
    @TableField("enabled")
    private Boolean enabled;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
