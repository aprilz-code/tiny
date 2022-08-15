package com.aprilz.tiny.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @description: 购物车商品货品勾选状态
 * @author: Aprilz
 * @since: 2022/7/26
 **/
@Data
public class CheckedParam {

    @NotEmpty
    private List<Integer> productIds;

    private Integer isChecked;
}
