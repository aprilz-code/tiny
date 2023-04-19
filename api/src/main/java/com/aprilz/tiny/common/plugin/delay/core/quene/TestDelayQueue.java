package com.aprilz.tiny.common.plugin.delay.core.quene;

import cn.hutool.json.JSONUtil;
import com.aprilz.tiny.common.plugin.delay.core.AbstractDelayQueueMachineFactory;
import com.aprilz.tiny.common.plugin.delay.interfaces.TimeTriggerExecutor;
import com.aprilz.tiny.common.plugin.delay.model.TimeTriggerMsg;
import com.aprilz.tiny.common.utils.SpringContextUtil;
import org.springframework.stereotype.Component;

/**
 * 测试延时队列
 *
 * @author paulG
 * @version v4.1
 * @date 2020/11/17 7:19 下午
 * @description
 * @since 1
 */
@Component
public class TestDelayQueue extends AbstractDelayQueueMachineFactory {


    @Override
    public void invoke(String jobId) {
        TimeTriggerMsg timeTriggerMsg = JSONUtil.toBean(jobId, TimeTriggerMsg.class);

        TimeTriggerExecutor executor = (TimeTriggerExecutor) SpringContextUtil.getBean(timeTriggerMsg.getTriggerExecutor());
        executor.execute(timeTriggerMsg.getParam());

    }

    @Override
    public String setDelayQueueName() {
        return "test_delay";
    }
}
