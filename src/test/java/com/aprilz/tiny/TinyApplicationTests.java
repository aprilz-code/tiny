package com.aprilz.tiny;


import com.aprilz.tiny.common.api.PageVO;
import com.aprilz.tiny.common.cache.LocalCache;
import com.aprilz.tiny.common.utils.PageUtil;
import com.aprilz.tiny.designMode.observer.impl.MySubject;
import com.aprilz.tiny.designMode.observer.impl.ObserverA;
import com.aprilz.tiny.designMode.observer.impl.ObserverB;
import com.aprilz.tiny.designMode.proxy.intf.Star;
import com.aprilz.tiny.designMode.proxy.intf.impl.CaiXuHun;
import com.aprilz.tiny.designMode.proxy.proxy.CglibProxy;
import com.aprilz.tiny.designMode.proxy.proxy.StarProxy;
import com.aprilz.tiny.designMode.strategy.Context;
import com.aprilz.tiny.designMode.strategy.Strategy;
import com.aprilz.tiny.mbg.entity.ApUser;
import com.aprilz.tiny.service.IApUserService;
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
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SpringBootTest
public class TinyApplicationTests {

    @Resource
    private IApUserService apUserService;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;


    /**
     * @param
     * @return void
     * @author aprilz
     * @description test 分页
     * @since 2022/11/10
     **/
    @Test
    public void contextLoads() {
        LambdaQueryWrapper<ApUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApUser::getId, 1);
        Page<ApUser> apAdminEntityPage = apUserService.page(PageUtil.initPage(new PageVO()), wrapper);
        System.out.println(apAdminEntityPage);
    }

    /**
     * @param
     * @return void
     * @author aprilz
     * @description //test 策略模式
     * @since 2022/11/10
     **/
    @Test
    public void testStrategy() {
        Strategy strategyA = Context.getStrategy("strategyA");
        strategyA.show();
    }

    /**
     * @param
     * @return void
     * @author aprilz
     * @description //test 观察者模式
     * @since 2022/11/10
     **/
    @Test
    public void testObserver() {
        MySubject mySubject = new MySubject();
        mySubject.add(new ObserverA());
        mySubject.add(new ObserverB());
        mySubject.operation();
    }

    /**
     * @param
     * @return void
     * @author aprilz
     * @description test 编程式事务
     * @since 2022/11/10
     **/
    @Test
    public void testTrans() {
        //查询总数据
        List<ApUser> allStudents = apUserService.list(Wrappers.emptyWrapper());
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


    /**
     * @param
     * @return void
     * @author aprilz
     * @description TransactionSynchronizationManager.registerSynchronization
     * @since 2022/11/16
     **/
    @Test
    public void testTs() {
        apUserService.testTs();
    }

    /**
     * @param
     * @return void
     * @author aprilz
     * @description lombok 1.14版本以上支持全局配置{@see lombok.config}
     * @since 2022/11/17
     **/
    @Test
    public void testLombokConfig() {
        //new ApAdmin().setUrl("").setContent("");
    }

    //testJDKProxy testCglibProxy 两种动态代理区别如下
    @Test
    void testJDKProxy() {
        Star cxk = new CaiXuHun();
        StarProxy proxy = new StarProxy();
        proxy.setTarget(cxk);
        Object obj = proxy.CreatProxyObj();
        //获取到代理对象
        Star star = (Star) obj;
        star.sing();
    }

    //jdk代理效率高于CGLIB代理
    //不同于jdk动态代理，jdk动态代理要求对象必须实现接口（三个参数的第二个参数 ---CreatProxyObj中的target.getClass().getInterfaces()），cglib对此没有要求。
    //JDK动态代理只能对实现了接口的类生成代理，而不能针对类。
    //CGLIB是针对类实现代理
    @Test
    void testCglibProxy() {
        CglibProxy proxy = new CglibProxy();
        // Object obj = proxy.CreatProxyedObj(Star.class);
        Object obj = proxy.CreatProxyedObj(CaiXuHun.class);
        //获取到代理对象
        Star star = (Star) obj;
        star.sing();
    }

    @Test
    void localCache() {
        // 创建一个用户缓存
        LocalCache<ApUser> userCache = new LocalCache<ApUser>().setParameters(100, 10, TimeUnit.MINUTES).build();

        Optional<ApUser> userOptional = userCache.query("10", user -> Optional.ofNullable(apUserService.getById(10)));
        userOptional = userCache.query("10", user -> Optional.ofNullable(apUserService.getById(10)));
        userOptional = userCache.query("10", user -> Optional.ofNullable(apUserService.getById(10)));
        Long aNull = userOptional.map(ApUser::getId).orElse(99999L);
        System.out.println(aNull);
        userCache.invalidateAll();
        userCache.query("10", user -> Optional.ofNullable(apUserService.getById(10)));
        aNull = userOptional.map(ApUser::getId).orElse(99999L);
        System.out.println(aNull);
    }

    @Test
    void localCache2() {
        // 创建一个用户缓存
        LocalCache<ApUser> userCache = new LocalCache<ApUser>().setParameters(100, 10, TimeUnit.MINUTES).build();
        for (Integer i = 10; i < 13; i++) {
            Integer finalI = i;
            if (i == 11) {
                finalI = 10;
            }
            Integer finalI1 = finalI;
            Optional<ApUser> userOptional = userCache.query(finalI1.toString(), user -> Optional.ofNullable(apUserService.getById(finalI1)));
            Long aNull = userOptional.map(ApUser::getId).orElse(99999L);
            System.out.println(aNull);
        }
    }


}
