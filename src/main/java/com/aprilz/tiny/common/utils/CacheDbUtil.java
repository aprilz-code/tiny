package com.aprilz.tiny.common.utils;

import cn.hutool.core.util.StrUtil;
import com.aprilz.tiny.common.cache.Cache;
import com.aprilz.tiny.mapper.ApSystemMapper;
import com.aprilz.tiny.mbg.entity.ApSystem;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;

/**
 * @description: 走缓存，查不到再走db
 * @author: Aprilz
 * @since: 2022/7/20
 **/
@Component
public class CacheDbUtil {

    private static CacheDbUtil cacheDbUtil;

    @Autowired
    private Cache cache;

    @Resource
    private ApSystemMapper systemMapper;

    public static String get(String key) {
        String value = cacheDbUtil.cache.getString(key);
        if (StrUtil.isNotBlank(value)) {
            return value;
        }
        LambdaQueryWrapper<ApSystem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApSystem::getKeyName, key).last("limit 1");
        ApSystem apSystem = cacheDbUtil.systemMapper.selectOne(queryWrapper);
        if (Objects.nonNull(apSystem)) {
            return apSystem.getKeyValue();
        }
        return null;
    }

    public static Integer getInteger(String key) {
        String value = cacheDbUtil.cache.getString(key);
        if (StrUtil.isNotBlank(value)) {
            return Integer.valueOf(value);
        }
        LambdaQueryWrapper<ApSystem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApSystem::getKeyName, key).last("limit 1");
        ApSystem apSystem = cacheDbUtil.systemMapper.selectOne(queryWrapper);
        if (Objects.nonNull(apSystem) && StrUtil.isNotBlank(apSystem.getKeyValue())) {
            return Integer.valueOf(apSystem.getKeyValue());
        }
        return null;
    }

    @PostConstruct
    public void init() {
        cacheDbUtil = this;
        cache = this.cache;
        systemMapper = this.systemMapper;
    }

}
