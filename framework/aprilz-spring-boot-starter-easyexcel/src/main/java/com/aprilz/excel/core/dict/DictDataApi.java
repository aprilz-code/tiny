package com.aprilz.excel.core.dict;

import com.aprilz.excel.core.drop.services.IDropDownService;
import com.aprilz.excel.core.dto.DictDataDTO;


/**
 * @author Aprilz
 * @date 2023/2/23-11:35
 * @description 默认DictSearch
 */
public class DictDataApi implements IDropDownService {


    /**
     * 获得指定的字典数据，从缓存中
     * key -> value
     *
     * @param dictType 字典类型
     * @param key      字典key
     * @return 字典数据
     */
    public DictDataDTO getDictDataByKey(String dictType, String key) {
        return null;
    }

    /**
     * 解析获得指定的字典数据，从缓存中
     * value -> key
     *
     * @param dictType 字典类型
     * @param value    字典数据标签
     * @return 字典数据
     */
    public DictDataDTO getDictDataByValue(String dictType, String value) {
        return null;
    }


}
