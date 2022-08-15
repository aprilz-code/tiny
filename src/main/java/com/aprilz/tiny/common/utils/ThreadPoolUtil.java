package com.aprilz.tiny.common.utils;

import io.micrometer.core.instrument.util.NamedThreadFactory;

import java.util.concurrent.*;

/**
 * 线程池
 *
 * @author Chopper
 */
public class ThreadPoolUtil {

    /**
     * 核心线程数，会一直存活，即使没有任务，线程池也会维护线程的最少数量
     */
    private static final int SIZE_CORE_POOL = 5;
    /**
     * 线程池维护线程的最大数量
     */
    private static final int SIZE_MAX_POOL = 10;
    /**
     * 线程池维护线程所允许的空闲时间
     */
    private static final long ALIVE_TIME = 2000;
    /**
     * 线程缓冲队列
     */
    private static final BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(100);
    // 线程池对拒绝任务(无线程可用)的处理策略
//    ThreadPoolExecutor.AbortPolicy()：默认值；抛出RejectedExecutionException异常
//    ThreadPoolExecutor.CallerRunsPolicy()：由向线程池提交任务的线程来执行该任务
//    ThreadPoolExecutor.DiscardOldestPolicy()：抛弃最旧的任务（最先提交而没有得到执行的任务）
//    ThreadPoolExecutor.DiscardPolicy()：抛弃当前的任务
    //   private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL, ALIVE_TIME, TimeUnit.MILLISECONDS, bqueue, new ThreadPoolExecutor.CallerRunsPolicy());
    public static ThreadPoolExecutor threadPool;

//    static {
//    //prestartAllCoreThreads设置项，可以在线程池创建，但还没有接收到任何任务的情况下，先行创建符合corePoolSize参数值的线程数：
//        pool.prestartAllCoreThreads();
//    }

    /**
     * 无返回值直接执行, 管他娘的
     *
     * @param runnable
     */
    public static void execute(Runnable runnable) {
        getThreadPool().execute(runnable);
    }

    /**
     * 返回值直接执行, 管他娘的
     *
     * @param callable
     */
    public static <T> Future<T> submit(Callable<T> callable) {
        return getThreadPool().submit(callable);
    }

    /**
     * DCL获取线程池
     *
     * @return 线程池对象
     */
    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool == null) {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL, ALIVE_TIME, TimeUnit.MILLISECONDS, bqueue, new NamedThreadFactory("delay"), new ThreadPoolExecutor.CallerRunsPolicy());
                    //  threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
                    return threadPool;
                }
                return threadPool;
            }
        }
        return threadPool;
    }

//    public static ThreadPoolExecutor getPool() {
//        return pool;
//    }
//
//    public static void main(String[] args) {
//        System.out.println(pool.getPoolSize());
//    }
}
