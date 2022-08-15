package com.aprilz.tiny.common.plugin.delay.interfaces.execute;

import com.aprilz.tiny.common.plugin.delay.interfaces.TimeTriggerExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * test执行器
 *
 * @author Chopper
 * @version v1.0
 * 2021-06-09 10:49
 */
@Component
@Slf4j
public class TestTimeTriggerExecutor implements TimeTriggerExecutor {

    @Override
    public void execute(Object object) {
        log.info("执行器具执行任务{}", object);
    }
}
