package com.aprilz.tiny.component.mybatis.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.aprilz.tiny.common.api.PageParam;
import com.aprilz.tiny.common.api.PageResult;
import com.aprilz.tiny.component.mybatis.base.BaseEntity;
import com.aprilz.tiny.component.mybatis.mapper.IBaseMapper;
import com.aprilz.tiny.component.mybatis.service.IBaseService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 基础服务实现
 *
 * @author Xjr
 * @version 1.0
 * @date 2023-03-24 16:46
 */
public abstract class BaseService<M extends IBaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements IBaseService<T> {

    /**
     * 批量新增一次性最大条数
     * <p>
     * 查看以下MySql配置判断最大条数优先级
     * SHOW VARIABLES LIKE '%max_allowed_packet%';
     * SHOW VARIABLES LIKE '%net_buffer_length%';
     * SHOW VARIABLES LIKE '%innodb_buffer_pool_size%';
     * SHOW VARIABLES LIKE '%innodb_log_buffer_size%';
     * </p>
     */
    private static final int MAX_BATCH_SIZE = 5000;

    @Override
    public PageResult<T> page(PageParam param, Wrapper<T> queryWrapper) {
        IPage<T> page = new Page<>(param.getPageNo(), param.getPageSize());
        baseMapper.selectPage(page, queryWrapper);
        // 转换返回
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<T> entityList) {
        this.saveBatch(entityList, MAX_BATCH_SIZE);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        if (CollectionUtil.isEmpty(entityList)) {
            // 为空也返回true
            return true;
        }

        // 每片数
        int burstSize = batchSize > MAX_BATCH_SIZE || batchSize <= 0 ? MAX_BATCH_SIZE : batchSize;

        // 分片数
        int limit = (entityList.size() + burstSize - 1) / burstSize;

        // 批量拆分
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            List<T> list = entityList.stream().skip((long) i * burstSize).limit(burstSize).collect(Collectors.toList());
            baseMapper.insertBatchSomeColumn(list);
        });
        return true;
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper) {
        return this.getOne(queryWrapper, true);
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        if (queryWrapper instanceof LambdaQueryWrapper) {
            ((LambdaQueryWrapper<T>) queryWrapper).last("LIMIT 1");
        }
        return super.getOne(queryWrapper, throwEx);
    }

    @Override
    public boolean duplicateName(String compareValue, SFunction<T, ?>... eqColumns) {
        return duplicateName(null, compareValue, eqColumns);
    }

    @Override
    public boolean duplicateName(Long selfId, String compareValue, SFunction<T, ?>... eqColumns) {
        if (StrUtil.isBlank(compareValue) || ArrayUtil.isEmpty(eqColumns)) {
            return false;
        }
        LambdaQueryWrapper<T> lambdaQueryWrapper = new QueryWrapper<T>().lambda();
        for (SFunction<T, ?> eqColumn : eqColumns) {
            lambdaQueryWrapper.eq(eqColumn, StrUtil.trim(compareValue));
        }
        lambdaQueryWrapper.ne(Objects.nonNull(selfId), T::getId, selfId);
        lambdaQueryWrapper.last("LIMIT 1");
        return this.count(lambdaQueryWrapper) > 0;
    }

    /**
     * 关联表操作
     *
     * @param relationColumn 关联字段
     * @param flagColumn     比较字段
     * @param relationId     关联ID
     * @param entityList     需要列表
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdate(SFunction<T, ?> relationColumn, SFunction<T, ?> flagColumn, Long relationId, Collection<T> entityList) {
        // 当前数据库数据
        List<T> dataList = this.list(Wrappers.<T>query().lambda().eq(relationColumn, relationId));

        // 数据库不存在数据则直接进行保存,不需要继续向下执行
        if (CollectionUtil.isEmpty(dataList)) {
            this.saveBatch(entityList);
            return true;
        }

        Map<?, T> dataMap = dataList.stream().collect(Collectors.toMap(flagColumn, Function.identity(), (k1, k2) -> k1));

        // 当前数据集合
        Map<?, T> currentDataMap = entityList.stream().collect(Collectors.toMap(flagColumn, Function.identity(), (k1, k2) -> k1));

        // 待新增
        List<T> addList = new ArrayList<>();

        // 待修改
        List<T> updateList = new ArrayList<>();

        // 当前数据不包含数据库数据,则删除
        List<Long> removeList = dataList.stream().filter(item -> !currentDataMap.containsKey(flagColumn.apply(item))).map(T::getId).collect(Collectors.toList());

        // 数据比较
        for (T item : entityList) {
            if (dataMap.containsKey(flagColumn.apply(item))) {
                // 数据库存在,则修改
                T data = dataMap.get(flagColumn.apply(item));
                item.setId(data.getId());
                updateList.add(item);
            } else {
                // 数据库不存在,则新增
                addList.add(item);
            }
        }
        if (CollectionUtil.isNotEmpty(addList)) {
            this.saveBatch(addList);
        }
        if (CollectionUtil.isNotEmpty(updateList)) {
            this.updateBatchById(updateList);
        }
        if (CollectionUtil.isNotEmpty(removeList)) {
            this.removeByIds(removeList);
        }
        return true;
    }

    @Override
    public boolean exist(Object value, SFunction<T, ?>... eqColumns) {
        if (Objects.isNull(value) || ArrayUtil.isEmpty(eqColumns)) {
            return false;
        }
        LambdaQueryWrapper<T> lambdaQueryWrapper = new QueryWrapper<T>().lambda();
        for (SFunction<T, ?> eqColumn : eqColumns) {
            lambdaQueryWrapper.eq(eqColumn, value);
        }
        lambdaQueryWrapper.last("LIMIT 1");
        return this.count(lambdaQueryWrapper) > 0;
    }

    @Override
    public boolean remove(SFunction<T, ?> eqColumn, Object value) {
        return this.remove(Wrappers.<T>query().lambda().eq(eqColumn, value));
    }
}
