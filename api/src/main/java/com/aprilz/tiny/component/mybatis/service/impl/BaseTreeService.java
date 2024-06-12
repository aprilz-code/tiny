package com.aprilz.tiny.component.mybatis.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.aprilz.tiny.component.mybatis.base.BaseTreeDO;
import com.aprilz.tiny.component.mybatis.mapper.IBaseMapper;
import com.aprilz.tiny.component.mybatis.service.IBaseTreeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class BaseTreeService<M extends IBaseMapper<T>, T extends BaseTreeDO> extends ServiceImpl<M, T> implements IBaseTreeService<T> {
    @Override
    public Long saveOrUpdateTree(T entity) {
        T parent;
        if (Objects.isNull(entity.getId())) {
            /**添加的数据先添加在获取id后执行更改pids数据*/
            super.save(entity);
        }
        /** 如果没有设置父节点，则代表为跟节点，有则获取父节点实体 */
        if (Objects.isNull(entity.getParentId()) || entity.getParentId() == 0) {
            entity.setParentIds(new StringBuffer(T.ROOT_NODE).append(T.SEPARATOR).append(entity.getId()).toString());
        } else {
            parent = super.getById(entity.getParentId());
            /** 设置新的父节点串*/
            String parentIds = new StringBuffer(parent.getParentIds()).append(T.SEPARATOR).append(entity.getId()).toString();
            entity.setParentIds(parentIds);
        }
        /** 获取修改前的parentIds，用于更新子节点的parentIds*/
        String oldParentIds = super.getById(entity.getId()).getParentIds();
        /** 更新实体 且更新所有自己点的parentIds*/
        super.updateById(entity);
        List<T> list = super.lambdaQuery().like(T::getParentIds, entity.getId()).list();
        for (T e : list) {
            if (StringUtils.isNotBlank(e.getParentIds()) && StringUtils.isNotBlank(oldParentIds)) {
                e.setParentIds(e.getParentIds().replace(oldParentIds, entity.getParentIds()));
                super.updateById(e);
            }
        }
        return entity.getId();
    }


    /**
     * 删除下一层子级
     *
     * @param parentId
     * @param itself   true包含自身
     * @return
     */
    @Override
    public boolean deleteByParentId(Long parentId, Boolean itself) {
        List<T> tList = getChildrenByParentId(parentId, itself);
        if (CollUtil.isEmpty(tList)) {
            return true;
        }
        List<Long> rmIds = tList.stream().map(T::getId).collect(Collectors.toList());
        super.removeByIds(rmIds);
        return true;
    }

    /**
     * 删除所有层子级
     *
     * @param parentId
     * @param notItself
     * @return
     */
    @Override
    public boolean deleteByFindInSetParentId(Long parentId, Boolean notItself) {
        List<T> tList = findInSetByParentId(parentId, notItself);
        if (CollUtil.isEmpty(tList)) {
            return true;
        }
        List<Long> rmIds = tList.stream().map(T::getId).collect(Collectors.toList());
        super.removeByIds(rmIds);
        return true;
    }

    /**
     * @param parentId 获取下一层子级
     * @param itself   包含parentId的那条数据吗？ true 包含自身
     * @return
     */
    @Override
    public List<T> getChildrenByParentId(Long parentId, Boolean itself) {
        LambdaQueryChainWrapper<T> queryWrapper = super.lambdaQuery();
        queryWrapper.eq(T::getParentId, parentId);
        if (itself) {
            queryWrapper.or().eq(T::getId, parentId);
        }
        return queryWrapper.list();
    }

    /**
     * @param parentId
     * @param notItself 不包含parentId的那条数据吗？ true,不包含自身
     * @return
     */
    @Override
    public List<T> findInSetByParentId(Long parentId, Boolean notItself) {
        LambdaQueryChainWrapper<T> queryWrapper = super.lambdaQuery();
        queryWrapper.apply("FIND_IN_SET({0}, parent_ids)", parentId).orderByAsc(T::getId);
        if (notItself) {
            queryWrapper.ne(T::getId, parentId);
        }
        return queryWrapper.list();
    }

    /***
     * 获取当前节点的父对象
     * @param parentId
     * @return
     */
    @Override
    public T getParentByParentId(Long parentId) {
        return super.getById(parentId);
    }

    /**
     * 查询当前节点的所有父id集合 按照id 升序排序
     *
     * @param parentIds
     * @return
     */
    @Override
    public List<T> getParentByParentIds(String parentIds) {
        String[] parentIdsArray = parentIds.split(T.SEPARATOR);
        LambdaQueryChainWrapper<T> queryWrapper = super.lambdaQuery();
        queryWrapper.in(T::getId, parentIdsArray).orderByAsc(T::getId);
        return queryWrapper.list();
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


}
