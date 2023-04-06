package com.aprilz.es.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 宋平州
 */
public class DtpExecutor extends ThreadPoolExecutor {

    /**
     * @param corePoolSize  核心线程数
     * @param maximumPoolSize 最大线程数
     */
    public DtpExecutor(int corePoolSize, int maximumPoolSize) {
        super(corePoolSize, maximumPoolSize, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
    }
}
