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
 * 收藏表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_collect")
@ApiModel(value = "ApCollect对象", description = "收藏表")
public class ApCollect extends BaseEntity<ApCollect> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户表的用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("如果type=0，则是商品ID；如果type=1，则是专题ID")
    @TableField("value_id")
    private Integer valueId;

    @ApiModelProperty("收藏类型，如果type=0，则是商品ID；如果type=1，则是专题ID")
    @TableField("type")
    private Integer type;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
