package com.aprilz.excel.core.handler;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.aprilz.excel.core.annotations.ResponseExcel;
import com.aprilz.excel.core.annotations.Sheet;
import com.aprilz.excel.core.drop.entity.ChainDropDown;
import com.aprilz.excel.core.drop.handle.ChainDropDownWriteHandler;
import com.aprilz.excel.core.drop.handle.DropDownWriteHandler;
import com.aprilz.excel.core.exception.ExcelException;
import com.aprilz.excel.core.properties.ExcelConfigProperties;
import com.aprilz.excel.core.properties.SheetBuildProperties;
import com.aprilz.excel.core.util.ExcelUtils;
import com.aprilz.excel.enhance.WriterBuilderEnhancer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Aprilz
 * @date 2020/3/29
 * <p>
 * 处理单sheet 页面
 */
public class SingleSheetWrite extends AbstractSheetWrite {

    public SingleSheetWrite(ExcelConfigProperties configProperties,
                            ObjectProvider<List<Converter<?>>> converterProvider, WriterBuilderEnhancer excelWriterBuilderEnhance) {
        super(configProperties, converterProvider, excelWriterBuilderEnhance);
    }

    /**
     * obj 是List 且list不为空同时list中的元素不是是List 才返回true 例如 List<ApTest>
     *
     * @param obj 返回对象
     * @return boolean
     */
    @Override
    public boolean support(Object obj) {
        if (obj instanceof List) {
            List<?> objList = (List<?>) obj;
            return objList instanceof List && (objList.isEmpty() || (!objList.isEmpty() && !(objList.get(0) instanceof List)));
        } else {
            throw new ExcelException("@ResponseExcel 返回值必须为List类型");
        }
    }

    @Override
    public void write(Object obj, MethodParameter parameter, HttpServletResponse response, ResponseExcel responseExcel) {
        List<?> eleList = (List<?>) obj;
        Class<?> dataClass = null;
        // 读取到Excel具体类型
        if (eleList.isEmpty()) {
            Type genericReturnType = parameter.getGenericParameterType();
            if (genericReturnType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
                if (actualTypeArguments.length > 0 && actualTypeArguments[0] instanceof Class) {
                    dataClass = (Class<?>) actualTypeArguments[0];
                }
            }
        } else {
            dataClass = eleList.get(0).getClass();
        }

        //解析出下拉框
        Field[] fields = dataClass.getDeclaredFields();
        //移除serialVersionUID
        if (fields.length > 0) {
            fields = Arrays.stream(fields).filter(field -> !field.getName().equals("serialVersionUID")).collect(Collectors.toList()).toArray(new Field[0]);
        }
        Map<Integer, String[]> map = ExcelUtils.processDropDown(fields);
        Map<Integer, ChainDropDown> integerChainDropDownMap = ExcelUtils.processChainDropDown(fields);
        int headRowNumber = responseExcel.headRowNumber();

        DropDownWriteHandler dropDownWriteHandler = new DropDownWriteHandler(map, headRowNumber);
        ChainDropDownWriteHandler chainDropDownWriteHandler = new ChainDropDownWriteHandler(integerChainDropDownMap, headRowNumber);
        ExcelWriter excelWriter = getExcelWriter(response, responseExcel, dropDownWriteHandler, chainDropDownWriteHandler);

        // 获取 Sheet 配置
        SheetBuildProperties sheetBuildProperties;
        Sheet[] sheets = responseExcel.sheets();
        if (sheets != null && sheets.length > 0) {
            sheetBuildProperties = new SheetBuildProperties(sheets[0]);
        } else {
            sheetBuildProperties = new SheetBuildProperties(0);
        }

        // 模板信息
        String template = responseExcel.template();

        // 创建sheet
        WriteSheet sheet;


        sheet = this.emptySheet(sheetBuildProperties, dataClass, template, responseExcel.headGenerator());
        if (responseExcel.fill()) {
            // 填充 sheet
            excelWriter.fill(eleList, sheet);
        } else {
            // 写入 sheet
            excelWriter.write(eleList, sheet);
        }

        excelWriter.finish();
    }

}
