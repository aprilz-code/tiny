package com.aprilz.tiny.common.consts;

import cn.hutool.core.collection.CollUtil;
import com.aprilz.tiny.common.cache.Cache;
import com.aprilz.tiny.mapper.ApSystemMapper;
import com.aprilz.tiny.mbg.entity.ApSystem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 缓存常量
 * @author: aprilz
 * @since: 2022/7/19
 **/
@Component
@Slf4j
public class CacheConst {
    public static final boolean ENABLE = false;
    public static final String INDEX = "ap_index";
    public static final String CATALOG = "ap_catalog";
    public static final String REGION = "ap_region";
    public static final String GOODS = "ap_goods";


    private static CacheConst cacheConst;

    @Resource
    private ApSystemMapper systemMapper;

    @Autowired
    private Cache cache;

    private static void refreshCache() {
        List<ApSystem> apSystems = cacheConst.systemMapper.selectList(Wrappers.emptyWrapper());
        if (CollUtil.isNotEmpty(apSystems)) {
            apSystems.parallelStream().forEach(sys -> {
                //4小时
                cacheConst.cache.put(sys.getKeyName(), sys.getKeyValue(), 240L);
            });
        }
        log.info("初始化系统参数到缓存 success");
    }

    /**
     * 启动默认载入 缓存首页数据
     */
    @PostConstruct
    public void loadData() {
        cacheConst = this;
        cacheConst.systemMapper = this.systemMapper;
        cacheConst.cache = this.cache;
        refreshCache();
    }

}
