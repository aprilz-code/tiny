package com.aprilz.tiny.common.plugin.delay.interfaces;


import com.aprilz.tiny.common.plugin.delay.model.TimeTriggerMsg;

/**
 * 延时执行接口
 *
 * @author Chopper
 */
public interface TimeTrigger {


    /**
     * 添加延时任务
     *
     * @param timeTriggerMsg 延时任务信息
     */
    void add(TimeTriggerMsg timeTriggerMsg);

}
