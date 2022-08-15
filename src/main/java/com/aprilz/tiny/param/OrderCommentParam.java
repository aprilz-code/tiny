package com.aprilz.tiny.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 订单评价
 * @author: Aprilz
 * @since: 2022/8/2
 **/
@Data
public class OrderCommentParam {
    @NotNull
    private Long orderGoodsId;

    private String content;

    private Integer star;

    private Boolean hasPicture;

    private List<String> picUrls;
}
