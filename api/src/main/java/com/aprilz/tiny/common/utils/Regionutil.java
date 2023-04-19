package com.aprilz.tiny.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.aprilz.tiny.common.cache.Cache;
import com.aprilz.tiny.common.consts.CacheConst;
import com.aprilz.tiny.mapper.ApRegionMapper;
import com.aprilz.tiny.mbg.entity.ApRegion;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 地区信息util
 * @author: Aprilz
 * @since: 2022/7/21
 **/

@Component
public class Regionutil {

    private static Regionutil regionutil;

    @Autowired
    private Cache cache;

    @Resource
    private ApRegionMapper regionMapper;

    private static List<ApRegion> refreshKeys() {
        regionutil.cache.remove(CacheConst.REGION);
        LambdaQueryWrapper<ApRegion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApRegion::getDeleteFlag, false);
        List<ApRegion> apRegions = regionutil.regionMapper.selectList(queryWrapper);
        if (CollUtil.isNotEmpty(apRegions)) {
            regionutil.cache.put(CacheConst.REGION, JSONUtil.toJsonStr(apRegions));
        }
        return apRegions;
    }

    private static List<ApRegion> getAll() {
        String region = regionutil.cache.getString(CacheConst.REGION);
        if (StrUtil.isNotBlank(region)) {
            return JSONUtil.toBean(region, ArrayList.class);
        }
        return refreshKeys();
    }

    @PostConstruct
    public void init() {
        regionutil = this;
        cache = this.cache;
        regionMapper = this.regionMapper;
        refreshKeys();
    }

}
