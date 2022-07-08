package com.aprilz.tiny.mbg.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * 数据库基础实体类
 *
 * @author aprilz
 * @version v1.0
 * @since 2020/8/20 14:34
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity<A extends BaseEntity> extends Model  {

    private static final long serialVersionUID = 1L;


    @TableId
    @ApiModelProperty(value = "唯一标识", hidden = true)
    private Long id;


    @CreatedBy
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者", hidden = true)
    private String createBy;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    @LastModifiedBy
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新者", hidden = true)
    private String updateBy;

    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "删除标志 0->删除；1->未删除", hidden = true)
    private Integer status;


}
