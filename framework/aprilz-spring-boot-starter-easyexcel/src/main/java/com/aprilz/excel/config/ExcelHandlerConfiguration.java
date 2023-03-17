package com.aprilz.excel.config;

import com.aprilz.excel.core.aop.ResponseExcelReturnValueHandler;
import com.aprilz.excel.core.handler.ManySheetWriteHandler;
import com.aprilz.excel.core.handler.SheetWriteHandler;
import com.aprilz.excel.core.handler.SingleSheetWriteHandler;
import com.aprilz.excel.core.properties.ExcelConfigProperties;
import com.aprilz.excel.enhance.DefaultWriterBuilderEnhancer;
import com.aprilz.excel.enhance.WriterBuilderEnhancer;
import com.alibaba.excel.converters.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author Hccake 2020/10/28
 * @version 1.0
 */
@RequiredArgsConstructor
public class ExcelHandlerConfiguration {

    private final ExcelConfigProperties configProperties;

    private final ObjectProvider<List<Converter<?>>> converterProvider;

    /**
     * ExcelBuild增强
     *
     * @return DefaultWriterBuilderEnhancer 默认什么也不做的增强器
     */
    @Bean
    @ConditionalOnMissingBean
    public WriterBuilderEnhancer writerBuilderEnhancer() {
        return new DefaultWriterBuilderEnhancer();
    }

    /**
     * 单sheet 写入处理器
     */
    @Bean
    @ConditionalOnMissingBean
    public SingleSheetWriteHandler singleSheetWriteHandler() {
        return new SingleSheetWriteHandler(configProperties, converterProvider, writerBuilderEnhancer());
    }

    /**
     * 多sheet 写入处理器
     */
    @Bean
    @ConditionalOnMissingBean
    public ManySheetWriteHandler manySheetWriteHandler() {
        return new ManySheetWriteHandler(configProperties, converterProvider, writerBuilderEnhancer());
    }

    /**
     * 返回Excel文件的 response 处理器
     *
     * @param sheetWriteHandlerList 页签写入处理器集合
     * @return ResponseExcelReturnValueHandler
     */
    @Bean
    @ConditionalOnMissingBean
    public ResponseExcelReturnValueHandler responseExcelReturnValueHandler(
            List<SheetWriteHandler> sheetWriteHandlerList) {
        return new ResponseExcelReturnValueHandler(sheetWriteHandlerList);
    }


}
