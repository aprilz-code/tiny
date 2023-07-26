package com.aprilz.excel.core.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.aprilz.excel.core.exception.ErrorMessage;

import java.util.List;
import java.util.Map;

/**
 * list analysis EventListener
 */
public abstract class ListAnalysisEventListener<T> extends AnalysisEventListener<T> {

    public Long headRowNumber;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headRowNumber = context.readRowHolder().getRowIndex().longValue();
    }


    /**
     * 获取 excel 解析的对象列表
     *
     * @return 集合
     */
    public abstract List<T> getList();

    /**
     * 获取异常校验结果
     *
     * @return 集合
     */
    public abstract List<ErrorMessage> getErrors();

}
