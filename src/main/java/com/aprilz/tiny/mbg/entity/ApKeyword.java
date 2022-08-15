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
 * 关键字表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_keyword")
@ApiModel(value = "ApKeyword对象", description = "关键字表")
public class ApKeyword extends BaseEntity<ApKeyword> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("关键字")
    @TableField("keyword")
    private String keyword;

    @ApiModelProperty("关键字的跳转链接")
    @TableField("url")
    private String url;

    @ApiModelProperty("是否是热门关键字")
    @TableField("is_hot")
    private Boolean isHot;

    @ApiModelProperty("是否是默认关键字")
    @TableField("is_default")
    private Boolean isDefault;

    @ApiModelProperty("排序")
    @TableField("sort_order")
    private Integer sortOrder;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
