package com.aprilz.excel.core.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.aprilz.excel.core.drop.annotations.ChainDropDownFields;
import com.aprilz.excel.core.drop.annotations.DropDownFields;
import com.aprilz.excel.core.drop.entity.ChainDropDown;
import com.aprilz.excel.core.drop.handle.ChainDropDownWriteHandler;
import com.aprilz.excel.core.drop.handle.DropDownWriteHandler;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Aprilz
 * @date 2023/2/22-16:12
 * @description Excel工具类
 */
@Slf4j
public class ExcelUtils {

    /**
     * 浏览器导出excel文件
     * 官网文档地址：https://www.yuque.com/easyexcel/doc/easyexcel
     *
     * @param data          数据
     * @param templateClass 模板对象class
     * @param pageSize      每页多少条
     * @param fileName      文件名称
     * @param response      输出流
     * @throws Exception err
     */
    public static void exportBrowser(List data, Class templateClass, Integer pageSize, String fileName, HttpServletResponse response) throws Exception {
        pageSize = Optional.ofNullable(pageSize).orElse(50000);
        fileName = fileName + System.currentTimeMillis();
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 获取改类声明的所有字段
        Field[] fields = templateClass.getDeclaredFields();
        //移除serialVersionUID
        if (fields.length > 0) {
            fields = Arrays.stream(fields).filter(field -> !field.getName().equals("serialVersionUID")).collect(Collectors.toList()).toArray(new Field[0]);
        }
        // 响应字段对应的下拉集合
        Map<Integer, String[]> map = processDropDown(fields);
        Map<Integer, ChainDropDown> integerChainDropDownMap = processChainDropDown(fields);
        ExcelWriter excelWriter = null;
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            excelWriter = EasyExcel.write(out, templateClass).registerWriteHandler(new DropDownWriteHandler(map))
                    .registerWriteHandler(new ChainDropDownWriteHandler(integerChainDropDownMap)).build();
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
                String[] sources = ResolveAnnotation.resolve(dropDownField);
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
    public static <T> void write(HttpServletResponse response, String filename, String sheetName,
                                 Class<T> head, List<T> data) throws IOException {
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename + System.currentTimeMillis() + ".xlsx", "UTF-8"));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        // 输出 Excel
        EasyExcel.write(response.getOutputStream(), head)
                //    .autoCloseStream(false) // 不要自动关闭，交给 Servlet 自己处理
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 基于 column 长度，自动适配。最大 255 宽度
                .sheet(sheetName).doWrite(data);

    }

    public static <T> List<T> read(MultipartFile file, Class<T> head) throws IOException {
        return EasyExcel.read(file.getInputStream(), head, null)
                //        .autoCloseStream(false)  // 不要自动关闭，交给 Servlet 自己处理
                .doReadAllSync();
    }
}
