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
 * 搜索历史表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_search_history")
@ApiModel(value = "ApSearchHistory对象", description = "搜索历史表")
public class ApSearchHistory extends BaseEntity<ApSearchHistory> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户表的用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("搜索关键字")
    @TableField("keyword")
    private String keyword;

    @ApiModelProperty("搜索来源，如pc、wx、app")
    @TableField("from")
    private String from;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
