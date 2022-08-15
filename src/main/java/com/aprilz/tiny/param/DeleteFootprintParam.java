package com.aprilz.tiny.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 删除浏览记录param
 * @author: Aprilz
 * @since: 2022/7/28
 **/
@Data
public class DeleteFootprintParam {


    @NotNull
    private Long id;
}
