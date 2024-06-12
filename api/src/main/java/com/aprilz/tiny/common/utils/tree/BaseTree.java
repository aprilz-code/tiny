package com.aprilz.tiny.common.utils.tree;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Aprilz
 * @date 2023/3/6-8:56
 * @description Tree基类
 */
@Data
public class BaseTree {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", required = true, example = "32741")
    private Long id;

    @ApiModelProperty(value = "父id，顶级父id默认为0", required = true)
    private Long parentId;

    @ApiModelProperty(value = "排序,从小到大")
    private Integer sort;

    @ApiModelProperty(value = "父ids，顶级父ids默认为0,如果有多层结构则表现为0::1::4", required = true)
    private String parentIds;

    @ApiModelProperty(value = "排序,创建时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "子集")
    private List<? extends BaseTree> childList;


    @ApiModelProperty(value = "是否计算本身")
    private boolean addSelf = false;

}
