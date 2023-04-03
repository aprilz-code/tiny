package com.aprilz.excel.core.handler;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.aprilz.excel.core.annotations.ResponseExcel;
import com.aprilz.excel.core.aop.DynamicNameAspect;
import com.aprilz.excel.core.convert.LocalDateStringConverter;
import com.aprilz.excel.core.convert.LocalDateTimeStringConverter;
import com.aprilz.excel.core.custom.AutoHeadColumnWidthStyleStrategy;
import com.aprilz.excel.core.drop.handle.ChainDropDownWriteHandler;
import com.aprilz.excel.core.drop.handle.DropDownWriteHandler;
import com.aprilz.excel.core.exception.ExcelException;
import com.aprilz.excel.core.head.HeadGenerator;
import com.aprilz.excel.core.head.HeadMeta;
import com.aprilz.excel.core.properties.ExcelConfigProperties;
import com.aprilz.excel.core.properties.SheetBuildProperties;
import com.aprilz.excel.enhance.WriterBuilderEnhancer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Aprilz
 * @date 2020/3/31
 */
@RequiredArgsConstructor
public abstract class AbstractSheetWrite implements SheetWrite, ApplicationContextAware {

    private final ExcelConfigProperties configProperties;

    private final ObjectProvider<List<Converter<?>>> converterProvider;

    private final WriterBuilderEnhancer excelWriterBuilderEnhance;

    private ApplicationContext applicationContext;


    @Override
    public void check(ResponseExcel responseExcel) {
        if (responseExcel.fill() && !StringUtils.hasText(responseExcel.template())) {
            throw new ExcelException("@ResponseExcel fill 必须配合 template 使用");
        }
    }

    @Override
    @SneakyThrows(UnsupportedEncodingException.class)
    public void export(Object o, MethodParameter parameter, HttpServletResponse response, ResponseExcel responseExcel) {
        check(responseExcel);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String name = (String) Objects.requireNonNull(requestAttributes).getAttribute(DynamicNameAspect.EXCEL_NAME_KEY,
                RequestAttributes.SCOPE_REQUEST);
        if (name == null) {
            name = UUID.randomUUID().toString();
        }
        String fileName = String.format("%s%s", URLEncoder.encode(name, "UTF-8"), responseExcel.suffix().getValue());
        // 根据实际的文件类型找到对应的 contentType
        String contentType = MediaTypeFactory.getMediaType(fileName).map(MediaType::toString)
                .orElse("application/vnd.ms-excel");
        response.setContentType(contentType);
        response.setCharacterEncoding("utf-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + fileName);
        write(o, parameter, response, responseExcel);
    }

    /**
     * 通用的获取ExcelWriter方法
     *
     * @param response                  HttpServletResponse
     * @param responseExcel             ResponseExcel注解
     * @param dropDownWriteHandler      固定值下拉处理器
     * @param chainDropDownWriteHandler 自定义动态处理器
     * @return ExcelWriter
     */
    @SneakyThrows(IOException.class)
    public ExcelWriter getExcelWriter(HttpServletResponse response, ResponseExcel responseExcel, DropDownWriteHandler dropDownWriteHandler, ChainDropDownWriteHandler chainDropDownWriteHandler) {
        ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream())
                .registerWriteHandler(BeanUtils.instantiateClass(AutoHeadColumnWidthStyleStrategy.class))
                .registerConverter(LocalDateStringConverter.INSTANCE)
                .registerConverter(LocalDateTimeStringConverter.INSTANCE).autoCloseStream(true)
                .excelType(responseExcel.suffix()).inMemory(responseExcel.inMemory());

        if (Objects.nonNull(dropDownWriteHandler)) {
            writerBuilder.registerWriteHandler(dropDownWriteHandler);
        }

        if (Objects.nonNull(chainDropDownWriteHandler)) {
            writerBuilder.registerWriteHandler(chainDropDownWriteHandler);
        }

        if (StringUtils.hasText(responseExcel.password())) {
            writerBuilder.password(responseExcel.password());
        }

        if (responseExcel.include().length != 0) {
            writerBuilder.includeColumnFieldNames(Arrays.asList(responseExcel.include()));
        }

        if (responseExcel.exclude().length != 0) {
            writerBuilder.excludeColumnFieldNames(Arrays.asList(responseExcel.exclude()));
        }

        if (responseExcel.writeHandler().length != 0) {
            for (Class<? extends WriteHandler> clazz : responseExcel.writeHandler()) {
                writerBuilder.registerWriteHandler(BeanUtils.instantiateClass(clazz));
            }
        }

        // 自定义注入的转换器
        registerCustomConverter(writerBuilder);

        if (responseExcel.converter().length != 0) {
            for (Class<? extends Converter> clazz : responseExcel.converter()) {
                writerBuilder.registerConverter(BeanUtils.instantiateClass(clazz));
            }
        }

        String templatePath = configProperties.getTemplatePath();
        if (StringUtils.hasText(responseExcel.template())) {
            ClassPathResource classPathResource = new ClassPathResource(
                    templatePath + File.separator + responseExcel.template());
            InputStream inputStream = classPathResource.getInputStream();
            writerBuilder.withTemplate(inputStream);
        }

        writerBuilder = excelWriterBuilderEnhance.enhanceExcel(writerBuilder, response, responseExcel, templatePath);

        return writerBuilder.build();
    }

    /**
     * 自定义注入转换器 如果有需要，子类自己重写
     *
     * @param builder ExcelWriterBuilder
     */
    public void registerCustomConverter(ExcelWriterBuilder builder) {
        converterProvider.ifAvailable(converters -> converters.forEach(builder::registerConverter));
    }

    /**
     * 构建一个 空的 WriteSheet 对象
     *
     * @param sheetBuildProperties sheet build 属性
     * @param template             模板信息
     * @return WriteSheet
     */
    public WriteSheet emptySheet(SheetBuildProperties sheetBuildProperties, String template) {
        // Sheet 编号和名称
        Integer sheetNo = sheetBuildProperties.getSheetNo() >= 0 ? sheetBuildProperties.getSheetNo() : null;
        String sheetName = sheetBuildProperties.getSheetName();

        // 是否模板写入
        ExcelWriterSheetBuilder writerSheetBuilder = StringUtils.hasText(template) ? EasyExcel.writerSheet(sheetNo)
                : EasyExcel.writerSheet(sheetNo, sheetName);

        return writerSheetBuilder.build();
    }

    /**
     * 获取 WriteSheet 对象
     *
     * @param sheetBuildProperties  sheet annotation info
     * @param dataClass             数据类型
     * @param template              模板
     * @param bookHeadEnhancerClass 自定义头处理器
     * @return WriteSheet
     */
    public WriteSheet emptySheet(SheetBuildProperties sheetBuildProperties, Class<?> dataClass, String template,
                                 Class<? extends HeadGenerator> bookHeadEnhancerClass) {

        // Sheet 编号和名称
        Integer sheetNo = sheetBuildProperties.getSheetNo() >= 0 ? sheetBuildProperties.getSheetNo() : null;
        String sheetName = sheetBuildProperties.getSheetName();

        // 是否模板写入
        ExcelWriterSheetBuilder writerSheetBuilder = StringUtils.hasText(template) ? EasyExcel.writerSheet(sheetNo)
                : EasyExcel.writerSheet(sheetNo, sheetName);

        // 头信息增强 1. 优先使用 sheet 指定的头信息增强 2. 其次使用 @ResponseExcel 中定义的全局头信息增强
        Class<? extends HeadGenerator> headGenerateClass = null;
        if (isNotInterface(sheetBuildProperties.getHeadGenerateClass())) {
            headGenerateClass = sheetBuildProperties.getHeadGenerateClass();
        } else if (isNotInterface(bookHeadEnhancerClass)) {
            headGenerateClass = bookHeadEnhancerClass;
        }
        // 定义头信息增强则使用其生成头信息，否则使用 dataClass 来自动获取
        if (headGenerateClass != null) {
            fillCustomHeadInfo(dataClass, bookHeadEnhancerClass, writerSheetBuilder);
        } else if (dataClass != null) {
            writerSheetBuilder.head(dataClass);
            if (sheetBuildProperties.getExcludes().length > 0) {
                writerSheetBuilder.excludeColumnFiledNames(Arrays.asList(sheetBuildProperties.getExcludes()));
            }
            if (sheetBuildProperties.getIncludes().length > 0) {
                writerSheetBuilder.includeColumnFiledNames(Arrays.asList(sheetBuildProperties.getIncludes()));
            }
        }

        // sheetBuilder 增强
        writerSheetBuilder = excelWriterBuilderEnhance.enhanceSheet(writerSheetBuilder, sheetNo, sheetName, dataClass,
                template, headGenerateClass);

        return writerSheetBuilder.build();
    }

    private void fillCustomHeadInfo(Class<?> dataClass, Class<? extends HeadGenerator> headEnhancerClass,
                                    ExcelWriterSheetBuilder writerSheetBuilder) {
        HeadGenerator headGenerator = this.applicationContext.getBean(headEnhancerClass);
        Assert.notNull(headGenerator, "The header generated bean does not exist.");
        HeadMeta head = headGenerator.head(dataClass);
        writerSheetBuilder.head(head.getHead());
        writerSheetBuilder.excludeColumnFieldNames(head.getIgnoreHeadFields());
    }

    /**
     * 是否为Null Head Generator
     *
     * @param headGeneratorClass 头生成器类型
     * @return true 已指定 false 未指定(默认值)
     */
    private boolean isNotInterface(Class<? extends HeadGenerator> headGeneratorClass) {
        return !Modifier.isInterface(headGeneratorClass.getModifiers());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
