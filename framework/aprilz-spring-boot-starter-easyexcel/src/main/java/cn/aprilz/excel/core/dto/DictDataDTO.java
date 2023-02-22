package cn.aprilz.excel.core.dto;

import lombok.Data;

/**
 * @author Aprilz
 * @date 2023/2/22-16:44
 * @description 字典数据DTO
 */
@Data
public class DictDataDTO {

    /**
     * 字典key
     */
    private String key;
    /**
     * 字典值
     */
    private String value;
    /**
     * 字典类型
     */
    private String dictType;

}
