package cn.aprilz.excel.core.handler;

import cn.aprilz.excel.core.exception.ErrorMessage;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.List;

/**
 * list analysis EventListener
 *
 * @author L.cm
 */
public abstract class ListAnalysisEventListener<T> extends AnalysisEventListener<T> {

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
