package com.aprilz.tiny.component.juc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @Title: MultiThreadingTransactionManager
 * @Description: 多线程事务管理器
 * @author: hulei
 * @date: 2023/8/7 11:36
 * @Version: 1.0
 */
@Slf4j
public class MultiThreadingTransactionManager {

    /**
     * 事务管理器
     */
    private final PlatformTransactionManager transactionManager;

    /**
     * 超时时间
     */
    private final long timeout;

    /**
     * 时间单位
     */
    private final TimeUnit unit;

    /**
     * 一阶段门闩，(第一阶段的准备阶段)，当所有子线程准备完成时（除“提交/回滚”操作以外的工作都完成），countDownLatch的值为0
     */
    private CountDownLatch oneStageLatch = null;

    /**
     * 二阶段门闩，(第二阶段的执行执行)，主线程将不再等待子线程执行，直接判定总的任务执行失败，执行第二阶段让等待确认的线程进行回滚
     */
    private final CountDownLatch twoStageLatch = new CountDownLatch(1);

    /**
     * 是否提交事务，默认是true（当任一线程发生异常时，isSubmit会被设置为false，即回滚事务）
     */
    private final AtomicBoolean isSubmit = new AtomicBoolean(true);

    /**
     * 构造方法
     *
     * @param transactionManager 事务管理器
     * @param timeout            超时时间
     * @param unit               时间单位
     */
    public MultiThreadingTransactionManager(PlatformTransactionManager transactionManager, long timeout, TimeUnit unit) {
        this.transactionManager = transactionManager;
        this.timeout = timeout;
        this.unit = unit;
    }

    /**
     * 线程池方式执行任务，可保证线程间的事务一致性
     *
     * @param runnableList 任务列表
     * @param executor     线程池
     */
    public void execute(List<Runnable> runnableList, ExecutorService executor) {
        // 排除null值
        runnableList.removeAll(Collections.singleton(null));
        // 属性初始化
        innit(runnableList.size());
        // 遍历任务列表并放入线程池
        for (Runnable runnable : runnableList) {
            // 创建线程
            Thread thread = new Thread(() -> {
                // 如果别的线程执行失败，则该任务就不需要再执行了
                if (!isSubmit.get()) {
                    log.info("当前子线程执行中止,因为线程事务中有子线程执行失败");
                    oneStageLatch.countDown();
                    return;
                }
                // 开启事务
                TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
                try {
                    // 执行业务逻辑
                    runnable.run();
                } catch (Exception e) {
                    // 执行体发生异常，设置回滚
                    isSubmit.set(false);
                    log.error("线程{}:业务发生异常,执行体:{}", Thread.currentThread().getName(), runnable);
                }
                // 计数器减一
                oneStageLatch.countDown();
                try {
                    //等待所有线程任务完成，监控是否有异常，有则统一回滚
                    twoStageLatch.await();
                    // 根据isSubmit值判断事务是否提交，可能是子线程出现异常，也有可能是子线程执行超时
                    if (isSubmit.get()) {
                        // 提交
                        transactionManager.commit(transactionStatus);
                        log.info("线程{}:事务提交成功,执行体:{}", Thread.currentThread().getName(), runnable);
                    } else {
                        // 回滚
                        transactionManager.rollback(transactionStatus);
                        log.info("线程{}:事务回滚成功,执行体:{}", Thread.currentThread().getName(), runnable);
                    }
                } catch (InterruptedException e) {
                    log.error("子线程抛出异常:{}",e.getMessage());
                }
            });
            executor.execute(thread);
        }

        //主线程担任协调者，当第一阶段所有参与者准备完成，oneStageLatch的计数为0
        //主线程发起第二阶段，执行阶段（提交或回滚）
        try {
            // 主线程等待所有线程执行完成，超时时间设置为五秒,超出等待时间则返回false,计数为0返回true
            boolean timeOutFlag = oneStageLatch.await(timeout, unit);
            long count = oneStageLatch.getCount();
            // 主线程等待超时，子线程可能发生长时间阻塞，死锁
            if (count > 0 || !timeOutFlag) {
                // 设置为回滚
                isSubmit.set(false);
                log.info("主线线程等待超时,任务即将全部回滚");
                throw new RuntimeException("主线线程等待超时,任务即将全部回滚");
            }
            twoStageLatch.countDown();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        // 返回结果,是否执行成功,事务提交即为执行成功,事务回滚即为执行失败
        boolean flag = isSubmit.get();
        if(!flag){
            log.info("有线程发生异常，事务全部回滚");
            throw new RuntimeException("有线程发生异常,数据全部回滚");
        }else{
            log.info("主线程和子线程执行无异常，事务全部提交");
        }
        executor.shutdown();
    }

    /**
     * 初始化属性
     *
     * @param size 任务数量
     */
    private void innit(int size) {
        oneStageLatch = new CountDownLatch(size);
    }
}