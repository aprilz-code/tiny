package com.aprilz.tiny.common.plugin.delay.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.aprilz.tiny.common.cache.Cache;
import com.aprilz.tiny.common.utils.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列工厂
 *
 * @author paulG
 * @since 2020/11/7
 **/
@Slf4j
public abstract class AbstractDelayQueueMachineFactory {

    @Autowired
    private Cache cache;
    /**
     * 是否销毁标记 volatile 保证可见性
     **/
    private volatile boolean destroyFlag = false;

    /**
     * 插入任务id
     *
     * @param jobId 任务id(队列内唯一) jobId 对应 TimeTriggerMsg 的jsonStr
     * @param time  延时时间(单位 :秒)
     * @return 是否插入成功
     */
    public boolean addJob(String jobId, Integer time) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, time);
        long delaySeconds = instance.getTimeInMillis() / 1000;
        boolean result = cache.zAdd(setDelayQueueName(), jobId, delaySeconds);
        log.info("增加延时任务, 缓存key {}, 等待时间 {}", setDelayQueueName(), time);
        return result;

    }

    @PreDestroy
    public void destroy() throws Exception {
        System.out.println("应用程序已关闭");
        this.destroyFlag = true;
    }

    /**
     * 延时队列机器开始运作
     */
    private synchronized void startDelayQueueMachine() {
        log.info("延时队列机器{}开始运作", setDelayQueueName());

        // 监听redis队列
        while (!Thread.interrupted() && !destroyFlag) {
            try {
                // 获取当前时间的时间戳
                long now = System.currentTimeMillis() / 1000;
                // 获取当前时间前的任务列表
                Set<DefaultTypedTuple> tuples = cache.zRangeByScore(setDelayQueueName(), 0, now);

                // 如果任务不为空
                if (CollUtil.isNotEmpty(tuples)) {
                    log.info("延时任务开始执行任务:{}", JSONUtil.toJsonStr(tuples));

                    for (DefaultTypedTuple tuple : tuples) {
                        String jobId = (String) tuple.getValue();
                        // 移除缓存，如果移除成功则表示当前线程处理了延时任务，则执行延时任务
                        Long num = cache.zRemove(setDelayQueueName(), jobId);
                        // 如果移除成功, 则执行
                        if (num > 0) {
                            ThreadPoolUtil.execute(() -> invoke(jobId));
                        }
                    }
                }

            } catch (Exception e) {
                log.error("处理延时任务发生异常,异常原因为{}", e.getMessage(), e);
            } finally {
                // 间隔5秒钟搞一次
                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    /**
     * 最终执行的任务方法
     *
     * @param jobId 任务id
     */
    public abstract void invoke(String jobId);


    /**
     * 要实现延时队列的名字
     */
    public abstract String setDelayQueueName();


    //@PostConstruct
    public void init() {
        new Thread(this::startDelayQueueMachine).start();
    }

}
