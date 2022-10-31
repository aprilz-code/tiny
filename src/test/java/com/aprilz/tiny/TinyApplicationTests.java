package com.aprilz.tiny;


import com.aprilz.tiny.common.api.PageVO;
import com.aprilz.tiny.common.utils.PageUtil;
import com.aprilz.tiny.mapper.ApUserMapper;
import com.aprilz.tiny.mbg.entity.ApUser;
import com.aprilz.tiny.service.strategy.Context;
import com.aprilz.tiny.service.strategy.Strategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SpringBootTest
public class TinyApplicationTests {

    @Resource
    private ApUserMapper apUserMapper;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    @Resource

    @Test
    public void contextLoads() {
        LambdaQueryWrapper<ApUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApUser::getId, 1);
        Page<ApUser> apAdminEntityPage = apUserMapper.selectPage(PageUtil.initPage(new PageVO()), wrapper);
        System.out.println(apAdminEntityPage);
    }

    @Test
    public void testStrategy() {
        Strategy strategyA = Context.getStrategy("strategyA");
        strategyA.show();
    }

    @Test
    public void testTrans() {
        //查询总数据
        List<ApUser> allStudents = apUserMapper.selectList(Wrappers.emptyWrapper());
        // 线程数量
        final Integer threadCount = 100;

        //每个线程处理的数据量
        final Integer dataPartionLength = (allStudents.size() + threadCount - 1) / threadCount;

        // 创建多线程处理任务
        ExecutorService studentThreadPool = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            // 每个线程处理的数据
            List<ApUser> threadDatas = allStudents.stream()
                    .skip(i * dataPartionLength).limit(dataPartionLength).collect(Collectors.toList());
            studentThreadPool.execute(() -> {
                this.updateStudents(threadDatas, countDownLatch);
            });
        }
        try {

            // 倒计时锁设置超时时间 30s
            countDownLatch.await(30, TimeUnit.SECONDS);
            studentThreadPool.shutdown();
            if (countDownLatch.getCount() > 0) {
                System.out.println("线程执行超时。。。");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println("主线程完成");

    }

    private void updateStudents(List<ApUser> admins, CountDownLatch threadLatch) {
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        System.out.println("子线程：" + Thread.currentThread().getName());
        try {
            admins.forEach(s -> {
                //假设更新用户
            });
            transactionManager.commit(transactionStatus);
            threadLatch.countDown();
        } catch (Throwable e) {
            e.printStackTrace();
            transactionManager.rollback(transactionStatus);
        }
    }


}
