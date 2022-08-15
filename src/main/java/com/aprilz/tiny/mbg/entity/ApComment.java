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

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "ap_comment", autoResultMap = true)
@ApiModel(value = "ApComment对象", description = "评论表")
public class ApComment extends BaseEntity<ApComment> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("如果type=0，则是商品评论；如果是type=1，则是专题评论。")
    @TableField("value_id")
    private Long valueId;

    @ApiModelProperty("评论类型，如果type=0，则是商品评论；如果是type=1，则是专题评论；")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("评论内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("管理员回复内容")
    @TableField("admin_content")
    private String adminContent;

    @ApiModelProperty("用户表的用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("是否含有图片")
    @TableField("has_picture")
    private Boolean hasPicture;

    @ApiModelProperty("图片地址列表，采用JSON数组格式")
    @TableField(value = "pic_urls", typeHandler = JsonStringArrayTypeHandler.class)
    private String[] picUrls;

    @ApiModelProperty("评分， 1-5")
    @TableField("star")
    private Integer star;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
