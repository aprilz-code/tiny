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
 * 常见问题表
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_issue")
@ApiModel(value = "ApIssue对象", description = "常见问题表")
public class ApIssue extends BaseEntity<ApIssue> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("问题标题")
    @TableField("question")
    private String question;

    @ApiModelProperty("问题答案")
    @TableField("answer")
    private String answer;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
