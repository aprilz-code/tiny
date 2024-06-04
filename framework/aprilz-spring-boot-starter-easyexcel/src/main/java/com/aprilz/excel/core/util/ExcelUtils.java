package com.aprilz.excel.core.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.google.common.collect.Lists;
import com.aprilz.excel.core.custom.AutoHeadColumnWidthStyleStrategy;
import com.aprilz.excel.core.custom.CustomerColorCellWriteHandler;
import com.aprilz.excel.core.drop.annotations.ChainDropDownFields;
import com.aprilz.excel.core.drop.annotations.DropDownFields;
import com.aprilz.excel.core.drop.entity.ChainDropDown;
import com.aprilz.excel.core.drop.handle.ChainDropDownWriteHandler;
import com.aprilz.excel.core.drop.handle.DropDownWriteHandler;
import com.aprilz.excel.core.handler.DefaultAnalysisEventListener;
import com.aprilz.excel.core.properties.ExcelConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author Aprilz
 * @date 2023/2/22-16:12
 * @description Excel工具类
 */
@Slf4j
public class ExcelUtils {

    private static ExcelConfigProperties configProperties;

    public ExcelUtils(ExcelConfigProperties configProperties) {
        ExcelUtils.configProperties = configProperties;
    }


    /**
     * 读取excel 并解析
     *
     * @param file  文件
     * @param clazz 解析成哪个dto
     * @param <T>   t
     * @return list
     * @throws IOException error
     */
    public static <T> List<Object> read(MultipartFile file, Class<T> clazz) throws IOException {
        DefaultAnalysisEventListener defaultAnalysisEventListener = new DefaultAnalysisEventListener();
        EasyExcel.read(file.getInputStream(), clazz, defaultAnalysisEventListener).sheet().doRead();
        return defaultAnalysisEventListener.getList();
    }

    /**
     * 将列表以 Excel 响应给前端
     *
     * @param response  响应
     * @param filename  文件名
     * @param sheetName Excel sheet 名
     * @param head      Excel head 头
     * @param data      数据列表哦
     * @param <T>       泛型，保证 head 和 data 类型的一致性
     * @throws IOException 写入失败的情况
     */
    public static <T> void writez(HttpServletResponse response, String filename, String sheetName,
                                 Class<T> head, List<T> data) throws IOException {
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename + System.currentTimeMillis() + ".xlsx", "UTF-8"));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        // 输出 Excel
        EasyExcel.write(response.getOutputStream(), head)
                //    .autoCloseStream(false) // 不要自动关闭，交给 Servlet 自己处理
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 基于 column 长度，自动适配。最大 255 宽度
                .sheet(sheetName).doWrite(data);

    }


    public static <T> List<T> readz(MultipartFile file, Class<T> head) throws IOException {
        return EasyExcel.read(file.getInputStream(), head, null)
                //        .autoCloseStream(false)  // 不要自动关闭，交给 Servlet 自己处理
                .doReadAllSync();
    }

    /**
     * 处理有下拉框注解的属性
     *
     * @param fields 字段属性集合
     * @return map
     */
    public static Map<Integer, String[]> processDropDown(Field[] fields) {
        // 响应字段对应的下拉集合
        Map<Integer, String[]> map = new HashMap<>();
        Field field = null;
        // 循环判断哪些字段有下拉数据集，并获取
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            // 解析注解信息
            DropDownFields dropDownField = field.getAnnotation(DropDownFields.class);
            if (null != dropDownField) {
                String[] sources = ResolveAnnotation.resolve(dropDownField, null);
                if (null != sources && sources.length > 0) {
                    map.put(i, sources);
                }
            }
        }
        return map;
    }


    /**
     * 需要特殊扩展参数的
     *
     * @param fields
     * @param params
     * @return
     */
    public static Map<Integer, String[]> processDropDown(Field[] fields, String params) {
        // 响应字段对应的下拉集合
        Map<Integer, String[]> map = new HashMap<>();
        Field field = null;
        // 循环判断哪些字段有下拉数据集，并获取
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            // 解析注解信息
            DropDownFields dropDownField = field.getAnnotation(DropDownFields.class);
            if (null != dropDownField) {
                String[] sources = ResolveAnnotation.resolve(dropDownField, params);
                if (null != sources && sources.length > 0) {
                    map.put(i, sources);
                }
            }
        }
        return map;
    }

    public static Map<Integer, ChainDropDown> processChainDropDown(Field[] fields) {
        // 响应字段对应的下拉集合
        Map<Integer, ChainDropDown> map = new HashMap<>();
        Field field = null;
        int rowIndex = 0;
        // 循环判断哪些字段有下拉数据集，并获取
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            // 解析注解信息
            ChainDropDownFields chainDropDownFields = field.getAnnotation(ChainDropDownFields.class);
            if (null != chainDropDownFields) {
//                List<ChainDropDown> sources = ResolveAnnotation.resolve(chainDropDownFields);
                ChainDropDown resolve = ResolveAnnotation.resolve(chainDropDownFields);
                long collect = resolve.getDataMap().keySet().size();
                System.out.println("collect=" + collect);
                if (resolve.isRootFlag()) {
                    resolve.setRowIndex(rowIndex);
                    rowIndex += 1;
                } else {
                    resolve.setRowIndex(rowIndex);
                    rowIndex += collect;
                }
                if (!ObjectUtils.isEmpty(resolve)) {
                    map.put(i, resolve);
                }
            }
        }
        return map;
    }

    /**
     * 获取Excel列的号码A-Z - AA-ZZ - AAA-ZZZ 。。。。
     *
     * @param num
     * @return
     */
    public static String getColNum(int num) {
        int MAX_NUM = 26;
        char initChar = 'A';
        if (num == 0) {
            return initChar + "";
        } else if (num > 0 && num < MAX_NUM) {
            int result = num % MAX_NUM;
            return (char) (initChar + result) + "";
        } else if (num >= MAX_NUM) {
            int result = num / MAX_NUM;
            int mod = num % MAX_NUM;
            String starNum = getColNum(result - 1);
            String endNum = getColNum(mod);
            return starNum + endNum;
        }
        return "";
    }


    /**
     * 下载错误模板专用
     *
     * @param data
     * @param templateClass
     * @param errorLineNums
     * @param pageSize
     * @param fileName
     * @throws Exception
     */
    public static String errorExcel(List data, Set<Long> errorLineNums, String template, Class templateClass, Integer pageSize, String fileName) throws Exception {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        pageSize = Optional.ofNullable(pageSize).orElse(50000);
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = fileName + System.currentTimeMillis();
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 获取改类声明的所有字段
        Field[] fields = templateClass.getDeclaredFields();
        // 响应字段对应的下拉集合
        Map<Integer, String[]> map = processDropDown(fields);
        Map<Integer, ChainDropDown> integerChainDropDownMap = processChainDropDown(fields);
        ExcelWriter excelWriter = null;
        OutputStream out = null;
        // 文件写入目录
        String tmpPath = System.getProperty("java.io.tmpdir") + fileName + ".xlsx";
        File tempFile = new File(tmpPath);
        tempFile.createNewFile();
        try {
            //out = response.getOutputStream();
            out = new FileOutputStream(tempFile);
            ExcelWriterBuilder writerBuilder = EasyExcel.write(out, templateClass)
                    .needHead(Boolean.FALSE).needHead(false)
                    .registerWriteHandler(BeanUtils.instantiateClass(AutoHeadColumnWidthStyleStrategy.class))
                    .registerWriteHandler(new DropDownWriteHandler(map, 1))
                    .registerWriteHandler(new ChainDropDownWriteHandler(integerChainDropDownMap, 1))
                    .registerWriteHandler(new CustomerColorCellWriteHandler(errorLineNums));
            String templatePath = configProperties.getTemplatePath();
            if (StringUtils.hasText(template)) {
                ClassPathResource classPathResource = new ClassPathResource(
                        templatePath + File.separator + template);
                InputStream inputStream = classPathResource.getInputStream();
                writerBuilder.withTemplate(inputStream);
            }

            excelWriter = writerBuilder.build();
            // 分页写入
            pageWrite(excelWriter, data, pageSize);
        } catch (Throwable e) {
            response.setHeader("Content-Disposition", "attachment;filename=下载失败");
            e.printStackTrace();
            log.error("文档下载失败:" + e.getMessage());
        } finally {
            data.clear();
            if (excelWriter != null) {
                excelWriter.finish();
            }
            assert out != null;
            out.flush();
            out.close();
        }

        MultipartFile multipartFile = FileUtils.getMultipartFile(tempFile, tempFile.getName());
        //String fileUrl = FileUploadUtil.fileUpload(multipartFile, BusinessType.EXCEL.name(), Date.from(LocalDateTime.now().plusMinutes(30L).atZone(ZoneId.systemDefault()).toInstant()));
        String fileUrl = null;
        //上传完毕，清除文件
        //tempFile.delete();
        // return getUrl(fileUrl);
        return tmpPath;
    }

    /**
     * 下载错误模板专用
     *
     * @param data
     * @param templateClass
     * @param errorLineNums
     * @param pageSize
     * @param fileName
     * @param params        扩展参数
     * @throws Exception
     */
    public static String errorExcel(List data, Set<Long> errorLineNums, String template, Class templateClass, Integer pageSize, String fileName, String params) throws Exception {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        pageSize = Optional.ofNullable(pageSize).orElse(50000);
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = fileName + System.currentTimeMillis();
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 获取改类声明的所有字段
        Field[] fields = templateClass.getDeclaredFields();
        // 响应字段对应的下拉集合
        Map<Integer, String[]> map = processDropDown(fields, params);
        Map<Integer, ChainDropDown> integerChainDropDownMap = processChainDropDown(fields);
        ExcelWriter excelWriter = null;
        OutputStream out = null;
        // 文件写入目录
        String tmpPath = System.getProperty("java.io.tmpdir") + fileName + ".xlsx";
        File tempFile = new File(tmpPath);
        tempFile.createNewFile();
        try {
            //out = response.getOutputStream();
            out = new FileOutputStream(tempFile);
            ExcelWriterBuilder writerBuilder = EasyExcel.write(out, templateClass)
                    .needHead(Boolean.FALSE).needHead(false)
                    .registerWriteHandler(BeanUtils.instantiateClass(AutoHeadColumnWidthStyleStrategy.class))
                    .registerWriteHandler(new DropDownWriteHandler(map))
                    .registerWriteHandler(new ChainDropDownWriteHandler(integerChainDropDownMap, 2))
                    .registerWriteHandler(new CustomerColorCellWriteHandler(errorLineNums));
            String templatePath = configProperties.getTemplatePath();
            if (StringUtils.hasText(template)) {
                ClassPathResource classPathResource = new ClassPathResource(
                        templatePath + File.separator + template);
                InputStream inputStream = classPathResource.getInputStream();
                writerBuilder.withTemplate(inputStream);
            }

            excelWriter = writerBuilder.build();
            // 分页写入
            pageWrite(excelWriter, data, pageSize);
        } catch (Throwable e) {
            response.setHeader("Content-Disposition", "attachment;filename=下载失败");
            e.printStackTrace();
            log.error("文档下载失败:" + e.getMessage());
        } finally {
            data.clear();
            if (excelWriter != null) {
                excelWriter.finish();
            }
            assert out != null;
            out.flush();
            out.close();
        }

        MultipartFile multipartFile = FileUtils.getMultipartFile(tempFile, tempFile.getName());
        //String fileUrl = FileUploadUtil.fileUpload(multipartFile, BusinessType.EXCEL.name(), Date.from(LocalDateTime.now().plusMinutes(30L).atZone(ZoneId.systemDefault()).toInstant()));
        String fileUrl = null;
        //上传完毕，清除文件
        //tempFile.delete();
        // return getUrl(fileUrl);
        return tmpPath;
    }


    /**
     * 分页写入
     *
     * @param writer   ExcelWriter
     * @param data     数据
     * @param pageSize 分页大小
     */
    public static void pageWrite(ExcelWriter writer, List<Object> data, Integer pageSize) {
        List<List<Object>> lt = Lists.partition(data, pageSize);
        for (int i = 0; i < lt.size(); i++) {
            int j = i + 1;
            WriteSheet writeSheet = EasyExcel.writerSheet(i, "第" + j + "页").build();
            writer.write(lt.get(i), writeSheet);
        }
    }


    private static String getUrl(String url) throws UnsupportedEncodingException {
        if (StrUtil.isBlank(url)) {
            return null;
        }
        String encode = URLEncoder.encode(url.substring(url.lastIndexOf("/") + 1), "UTF-8");
        return url.substring(0, url.lastIndexOf("/") + 1) + encode;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = new String("http://trainschool.oss-cn-shenzhen.aliyuncs.com/train-test/EXCEL/2023/0330/1680162383990000/%E5%9F%B9%E8%AE%AD%E5%B7%A5%E4%BA%BA%E6%A8%A1%E6%9D%BF1680162370298.xlsx");
        System.out.println(url.substring(0, url.lastIndexOf("/") + 1));
        System.out.println(url.substring(url.lastIndexOf("/") + 1));
        String encode = URLEncoder.encode(url.substring(url.lastIndexOf("/") + 1), "UTF-8");
        System.out.println(url.substring(0, url.lastIndexOf("/") + 1) + encode);

    }


}
