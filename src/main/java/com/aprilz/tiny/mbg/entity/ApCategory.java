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
 * 类目表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_category")
@ApiModel(value = "ApCategory对象", description = "类目表")
public class ApCategory extends BaseEntity<ApCategory> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("类目名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("类目关键字，以JSON数组格式")
    @TableField("keywords")
    private String keywords;

    @ApiModelProperty("类目广告语介绍")
    @TableField("`desc`")
    private String desc;

    @ApiModelProperty("父类目ID")
    @TableField("pid")
    private Long pid;

    @ApiModelProperty("类目图标")
    @TableField("icon_url")
    private String iconUrl;

    @ApiModelProperty("类目图片")
    @TableField("pic_url")
    private String picUrl;

    @TableField("level")
    private String level;

    @ApiModelProperty("排序")
    @TableField("sort_order")
    private Integer sortOrder;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
