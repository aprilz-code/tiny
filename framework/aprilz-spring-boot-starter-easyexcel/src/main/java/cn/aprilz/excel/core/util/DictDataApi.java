package cn.aprilz.excel.core.util;

import cn.aprilz.excel.core.dto.DictDataDTO;

/**
 * @author Aprilz
 * @date 2023/2/22-16:47
 * @description 待实现
 */
public interface DictDataApi {

    /**
     * 获得指定的字典数据，从缓存中
     * key -> value
     *
     * @param dictType 字典类型
     * @param key      字典key
     * @return 字典数据
     */
    default DictDataDTO getDictDataByKey(String dictType, String key) {
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
    default DictDataDTO getDictDataByValue(String dictType, String value) {
        return null;
    }
}
