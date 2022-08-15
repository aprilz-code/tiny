package com.aprilz.tiny.common.api;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 查询参数
 *
 * @author aprilz
 */
@Data
@Accessors(chain = true)
public class PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页号")
    private Integer pageNumber = 1;

    @ApiModelProperty(value = "页面大小")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "排序字段")
    private String sort;

    @ApiModelProperty(value = "排序方式 asc/desc")
    private String order;

    @ApiModelProperty(value = "需要驼峰转换蛇形", notes = "一般不做处理，如果数据库中就是蛇形，则这块需要处理。")
    private Boolean notConvert;

    public String getSort() {
        if (CharSequenceUtil.isNotEmpty(sort)) {
            if (notConvert == null || Boolean.FALSE.equals(notConvert)) {
                return StrUtil.toUnderlineCase(sort);
            } else {
                return sort;
            }
        }
        return sort;
    }

}
