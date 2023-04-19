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

    @ApiModelProperty(value = "id", example = "32741")
    private Long id;

    @ApiModelProperty(value = "父id，顶级父id为0")
    private Long parentId;

    private LocalDateTime updateTime;

    private List<? extends BaseTree> childList;

}
