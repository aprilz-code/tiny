package com.aprilz.excel.core.convert;

import cn.hutool.core.convert.Convert;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.aprilz.excel.core.annotations.DictFormat;
import com.aprilz.excel.core.util.DictUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Excel 数据字典转换器
 */
@Slf4j
public class DictConvert implements Converter<Object> {

    @Override
    public Class<?> supportJavaTypeKey() {
        throw new UnsupportedOperationException("暂不支持，也不需要");
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        throw new UnsupportedOperationException("暂不支持，也不需要");
    }

    @Override
    public Object convertToJavaData(ReadCellData readCellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        // 使用字典解析
        String type = getType(contentProperty);
        String value = readCellData.getStringValue();
        String key = DictUtil.getDictDataByValue(type, value);
        if (key == null) {
            log.error("[convertToJavaData][type({}) 解析不掉 label({})]", type, value);
            return null;
        }
        // 将 String 的 value 转换成对应的属性
        Class<?> fieldClazz = contentProperty.getField().getType();
        return Convert.convert(fieldClazz, key);
    }

    @Override
    public WriteCellData<String> convertToExcelData(Object object, ExcelContentProperty contentProperty,
                                                    GlobalConfiguration globalConfiguration) {
        // 空时，返回空
        if (object == null) {
            return new WriteCellData<>("");
        }

        // 使用字典格式化
        String type = getType(contentProperty);
        String key = String.valueOf(object);
        String value = DictUtil.getDictDataByKey(type, key);
        if (value == null) {
            log.error("[convertToExcelData][type({}) 转换不了 key({})]", type, key);
            //返回key本身
            return new WriteCellData<>(key);
        }
        // 生成 Excel 小表格
        return new WriteCellData<>(value);
    }

    private static String getType(ExcelContentProperty contentProperty) {
        return contentProperty.getField().getAnnotation(DictFormat.class).value();
    }

}
