package com.aprilz.tiny.service.impl;

import com.aprilz.tiny.common.utils.ThreadPoolUtil;
import com.aprilz.tiny.component.juc.MultiThreadingTransactionManager;
import com.aprilz.tiny.mapper.ApExcelTestMapper;
import com.aprilz.tiny.mbg.entity.ApExcelTest;
import com.aprilz.tiny.mbg.entity.ApUser;
import com.aprilz.tiny.service.IApTestService;
import com.aprilz.tiny.service.IApUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * excel-test表 服务实现类
 * </p>
 *
 * @author aprilz
 * @since 2023-02-22
 */
@Service
@Slf4j
public class ApTestServiceImpl extends ServiceImpl<ApExcelTestMapper, ApExcelTest> implements IApTestService {

    @Resource
    private IApUserService apUserService;

    /**
     * DataSourceTransactionManager和PlatformTransactionManager是Spring框架中用来管理事务的两种接口，它们之间的区别如下：
     * <p>
     * DataSourceTransactionManager是PlatformTransactionManager接口的一种具体实现，用于管理基于JDBC数据源的事务。它主要用于管理关系型数据库事务。
     * <p>
     * PlatformTransactionManager是Spring事务管理的顶层接口，定义了事务管理的基本操作。除了DataSourceTransactionManager外，Spring还提供了其他实现，如JtaTransactionManager（用于JTA事务管理）和JpaTransactionManager（用于管理JPA事务）等。
     * <p>
     * DataSourceTransactionManager通常用于管理单个数据源上的事务，而PlatformTransactionManager可以用于管理多个数据源、JTA事务、JPA事务等不同类型的事务。
     * <p>
     * 总的来说，DataSourceTransactionManager是PlatformTransactionManager的一种具体实现，主要用于管理基于JDBC数据源的事务，而PlatformTransactionManager是Spring事务管理的顶层接口，提供了更广泛的事务管理功能。
     */
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean testTrans() {
        // 创建多线程处理任务
        List<Runnable> runnableList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            runnableList.add(() -> {
                insertStudents();
            });
        }

        MultiThreadingTransactionManager multiThreadingTransactionManager = new MultiThreadingTransactionManager(platformTransactionManager, 5, TimeUnit.SECONDS);
        ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPool();
        multiThreadingTransactionManager.execute(runnableList, executor);

        //以上方法有一定局限性，即主线程如果再子线程执行后再抛出异常，则子线程无法回滚了，所以要求逻辑写在子线程执行之前
        //int a = 1/0;

        return true;
    }


    private void insertStudents() {

        String threadName = Thread.currentThread().getName();
        log.info("子线程：" + threadName + "开始");
        // 执行业务逻辑
        for (int i = 0; i < 200; i++) {
//            if ("delay-3".equals(threadName) && i == 198) {
//                throw new RuntimeException("手动异常");
//            }
            ApUser apUser = new ApUser();
            apUser.setUsername(threadName + "---" + i);
            apUser.setPassword(threadName + "---" + i);
            apUserService.save(apUser);
        }


    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean testTrans2() {
        List<TransactionStatus> statusList = new Vector<>();

        try {
            System.out.println("主线程为:" + Thread.currentThread().getName());
            //  int a = 1/0;

            ApUser apUser = new ApUser();
            apUser.setUsername("主线程");
            apUser.setPassword("主线程");
            apUserService.save(apUser);
            //线程一抛出异常
            CompletableFuture<Void> futureOne = getFutureOne(statusList);
            //线程二无异常
            CompletableFuture<Void> futureTwo = getFutureTwo(statusList);
            //必须等待所有子线程执行完,抛出异常才能回滚主线程的事务
            CompletableFuture.allOf(futureOne, futureTwo).join();
            statusList.forEach(dataSourceTransactionManager::commit);
        } catch (Exception e) {
            statusList.forEach(dataSourceTransactionManager::rollback);
            throw new RuntimeException(e.getMessage());
        }


        return true;
    }


    public CompletableFuture<Void> getFutureOne(List<TransactionStatus> statusList) {
        return CompletableFuture.runAsync(() -> {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            // 事物隔离级别，开启新事务，这样会比较安全些。
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            // 获得事务状态
            TransactionStatus status = dataSourceTransactionManager.getTransaction(def);
            try {
                String threadName = Thread.currentThread().getName();
                log.info("子线程：" + threadName + "开始");
                // 执行业务逻辑
                for (int i = 0; i < 200; i++) {
                    if ("delay-3".equals(threadName) && i == 198) {
                        throw new RuntimeException("手动异常");
                    }
                    ApUser apUser = new ApUser();
                    apUser.setUsername(threadName + "---" + i);
                    apUser.setPassword(threadName + "---" + i);
                    apUserService.save(apUser);
                }

            } catch (Exception e) {
                //抛出异常供主线程捕获
                throw new RuntimeException(e.getMessage());
            } finally {
                statusList.add(status);
            }
        }).exceptionally(throwable -> {
            throw new RuntimeException(throwable.getCause().getMessage());
        });
    }

    public CompletableFuture<Void> getFutureTwo(List<TransactionStatus> statusList) {
        return CompletableFuture.runAsync(() -> {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            // 事物隔离级别，开启新事务，这样会比较安全些。
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            // 获得事务状态
            TransactionStatus status = dataSourceTransactionManager.getTransaction(def);
            try {
                String threadName = Thread.currentThread().getName();
                log.info("子线程：" + threadName + "开始");
                // 执行业务逻辑
                for (int i = 0; i < 200; i++) {
//            if ("delay-3".equals(threadName) && i == 198) {
//                throw new RuntimeException("手动异常");
//            }
                    ApUser apUser = new ApUser();
                    apUser.setUsername(threadName + "---" + i);
                    apUser.setPassword(threadName + "---" + i);
                    apUserService.save(apUser);
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            } finally {
                statusList.add(status);
            }
        }).exceptionally(throwable -> {
            throw new RuntimeException(throwable.getCause().getMessage());
        });
    }

}
