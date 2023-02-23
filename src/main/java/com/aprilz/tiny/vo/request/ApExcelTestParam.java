package com.aprilz.tiny.vo.request;


import cn.aprilz.excel.core.annotations.DictFormat;
import cn.aprilz.excel.core.convert.DictConvert;
import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Aprilz
 * @date 2023/2/23-14:49
 * @description 导入
 */
@Data
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class ApExcelTestParam {

    @ExcelProperty("年龄")
    @NotNull(message = "年龄不能为空")
    private Integer age;


    @ExcelProperty(value = "性别", converter = DictConvert.class)
    @DictFormat("dic_sex")
    private Integer sex;

    @ExcelProperty("标题")
    @NotBlank(message = "标题不能为空")
    private String name;

    @ExcelProperty("链接")
    private String url;

    @ExcelProperty(value = "测试时间")
    @DateTimeFormat(DatePattern.NORM_DATE_PATTERN)
    private Date testTime;


}
