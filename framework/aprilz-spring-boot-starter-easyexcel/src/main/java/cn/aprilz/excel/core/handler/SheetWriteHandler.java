package cn.aprilz.excel.core.handler;

import cn.aprilz.excel.core.annotations.ResponseExcel;

import javax.servlet.http.HttpServletResponse;

/**
 * @date 2020/3/29
 * <p>
 * sheet 写出处理器
 */
public interface SheetWriteHandler {

    /**
     * 是否支持
     *
     * @param obj
     * @return
     */
    boolean support(Object obj);

    /**
     * 校验
     *
     * @param responseExcel 注解
     */
    void check(ResponseExcel responseExcel);

    /**
     * 返回的对象
     *
     * @param o             obj
     * @param response      输出对象
     * @param responseExcel 注解
     */
    void export(Object o, HttpServletResponse response, ResponseExcel responseExcel);

    /**
     * 写成对象
     *
     * @param o             obj
     * @param response      输出对象
     * @param responseExcel 注解
     */
    void write(Object o, HttpServletResponse response, ResponseExcel responseExcel);

}
