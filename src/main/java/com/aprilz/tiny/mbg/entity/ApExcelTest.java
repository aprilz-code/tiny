package com.aprilz.tiny.mbg.entity;

import cn.aprilz.excel.core.annotations.DictFormat;
import cn.aprilz.excel.core.convert.DateConverter;
import cn.aprilz.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
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
 * excel-test表
 * </p>
 *
 * @author aprilz
 * @since 2023-02-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("ap_excel_test")
@ApiModel(value = "ApExcelTest对象", description = "excel-test表")
public class ApExcelTest extends BaseEntity<ApExcelTest> {

    private static final long serialVersionUID = 1L;

    @TableField("age")
    @ExcelProperty("年龄")
    private Integer age;


    @TableField("sex")
    @ExcelProperty(value = "性别", converter = DictConvert.class)
    @DictFormat("dic_sex")
    private Integer sex;

    @ApiModelProperty("标题")
    @ExcelProperty("标题")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("链接")
    @ExcelProperty("链接")
    @TableField("url")
    private String url;

    @TableField("test_time")
    @ExcelProperty(value = "test_time", converter = DateConverter.class)
    private Date testTime;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
