package com.aprilz.excel.core.drop.services;

/**
 * 动态下拉数据集接口类
 * 每个下拉列表自己实现这个接口即可
 */
public interface IDropDownService {

    default public String[] getSource(String value, String params) {
        return new String[]{};
    }

}
