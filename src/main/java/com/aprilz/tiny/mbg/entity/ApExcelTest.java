package com.aprilz.tiny.mbg.entity;

//import com.aprilz.excel.core.annotations.DictFormat;
//import com.aprilz.excel.core.convert.DateConverter;
//import com.aprilz.excel.core.convert.DictConvert;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.aprilz.excel.core.annotations.DictFormat;
import com.aprilz.excel.core.convert.DictConvert;
import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.aprilz.excel.core.drop.annotations.DropDownFields;
import com.aprilz.excel.core.drop.enums.DropDownType;
import com.aprilz.tiny.common.excel.DictDataSearch;
import com.aprilz.tiny.mbg.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@Data
@TableName("ap_excel_test")
@ApiModel(value = "ApExcelTest对象", description = "excel-test表")
public class ApExcelTest extends BaseEntity {

    @TableId(value = "id")
    @ApiModelProperty(value = "唯一标识")
    @ExcelIgnore
    private Long id;

    @TableField("age")
    @ExcelProperty("年龄")
    private Integer age;


    @TableField("sex")
    @ExcelProperty(value = "性别", converter = DictConvert.class)
    @DictFormat("dic_sex")
    @DropDownFields(source = {"男", "女"})
    //@DropDownFields(sourceClass = DictDataSearch.class, type = DropDownType.DIC_SEX)
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
    @ExcelProperty(value = "测试时间")
    @DateTimeFormat(DatePattern.NORM_DATE_PATTERN)
    private Date testTime;




}
