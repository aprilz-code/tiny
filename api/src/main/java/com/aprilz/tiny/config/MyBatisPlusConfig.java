package com.aprilz.tiny.config;

import com.aprilz.tiny.common.utils.MybatisBatchUtils;
import com.aprilz.tiny.component.mybatis.MybatisPlusMetaObjectHandler;
import com.aprilz.tiny.component.mybatis.UpdateByFillPlugin;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * MyBatis配置类
 * Created by aprilz on 2019/4/8.
 */
@Slf4j
@Configuration
@MapperScan({"com.aprilz.tiny.mapper"})
public class MyBatisPlusConfig {

    /**
     * 设置最大单页限制数量(对外接口分页数必须加限制条件)
     */
    private static final long PAGE_MAX_LIMIT = 200;


    private final String idWorker = "WorkerId";

    @Value("${spring.application.name}")
    private String serviceName;

    @Resource
    private RedisTemplate<String, Long> redisTemplate;


    private final String script =
            "local now = redis.call('TIME')[1]\n" +
                    "local idWordsKey = KEYS[1]\n" +
                    "local sp = ':'\n" +
                    "for i = 0, 1023 do\n" +
                    "    local serviceKey = idWordsKey..sp..i\n" +
                    "    if redis.call('SETNX', serviceKey, now) == 1 then\n" +
                    "        redis.call('Expire', serviceKey, 30)\n" +
                    "        return i;\n" +
                    "    end\n" +
                    "end\n" +
                    "return -1";


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 多租户插件
        //interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(() -> new LongValue(1)));
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        // 攻击 SQL 阻断解析器,防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    /**
     * 分页插件，自动识别数据库类型
     */
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置最大单页限制数量(对外接口分页数必须加限制条件,最大设置为1000)
        paginationInnerInterceptor.setMaxLimit(PAGE_MAX_LIMIT);
        // 分页合理化
        paginationInnerInterceptor.setOverflow(true);
        return paginationInnerInterceptor;
    }

    /**
     * 乐观锁插件
     */
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    @Bean
    public UpdateByFillPlugin updateByFillPlugin() {
        return new UpdateByFillPlugin();
    }


    /*
     * 元对象字段填充控制器
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MybatisPlusMetaObjectHandler();
    }


    /**
     * 使用网卡信息绑定雪花生成器
     * 防止集群雪花ID重复
     */
    @Primary
    @Bean
    public IdentifierGenerator idGenerator() {
        if (serviceName == null || serviceName.length() == 0) {
            log.error("雪花算法初始化失败，【 app.idKey 】配置为空。");
            return null;
        }
        long num = getWorkerIdNum(serviceName);
        // 获取前  5 位
        long dataCenterId = num >> 5;
        // 获 取 后 5 位
        long workerId = num & (~(-1L << 5L));
        // 自定义初始化雪花算法
        log.info("==== [Init Snowflake suceessfully]: dataCenterId:{},workerId:{}", dataCenterId, workerId);
        return new DefaultIdentifierGenerator(workerId, dataCenterId);
    }


    /**
     * 获取机器标识号
     * param serviceName 服务名称,不再需要,r edis 框架自动添加服务名
     */
    private Long getWorkerIdNum(String serviceName) {
        // 实例化脚本对象
        DefaultRedisScript<Long> lua = new DefaultRedisScript<>();
        lua.setResultType(Long.class);
        lua.setScriptText(script);
        List<String> keys = new ArrayList<>(2);
        keys.add(String.join("-", serviceName, idWorker));

        // 获取序列号
        Long num = redisTemplate.execute(lua, keys, keys.size());
        String targetKey = String.join(":", keys) + ":" + num;

        // -1 代表机器用完了，重试
        if (num < 0) {
            log.error("目前 WorkerId 已用完，请重新启动试试");
            System.exit(0);
        }

        // 自动续期
        this.autoExpire(targetKey);

        return num;
    }

    /**
     * 自动续期
     */
    private void autoExpire(String key) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("snowFlaskId_auto_expire-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(() -> {
            redisTemplate.expire(key, 30, TimeUnit.SECONDS);
            log.debug("自动续期 WorkerId 成功:{}", key);
        }, 0, 10, TimeUnit.SECONDS);
    }


}
