package com.aprilz.tiny.component.mybatis.service;

import com.aprilz.tiny.common.api.PageParam;
import com.aprilz.tiny.common.api.PageResult;
import com.aprilz.tiny.component.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.Collection;

/**
 * 基础Service接口
 *
 * @author Xjr
 * @version 1.0
 * @date 2023-03-24 16:45
 */
public interface IBaseService<T extends BaseEntity> extends IService<T> {

    /**
     * 单表分页查询封装
     *
     * @param param param
     * @return PageVO<T>
     */
    PageResult<T> page(PageParam param, Wrapper<T> queryWrapper);

    /**
     * 重写MyBatisPlus批量新增方法
     *
     * @param entityList 对象集合
     * @return true=成功,false=失败
     */
    @Override
    boolean saveBatch(Collection<T> entityList);

    /**
     * 重写MyBatisPlus批量新增方法
     *
     * @param entityList 对象集合
     * @param batchSize  批量提交条数
     * @return true=成功,false=失败
     */
    @Override
    boolean saveBatch(Collection<T> entityList, int batchSize);

    /**
     * 重写MyBatisPlus获取单个对象方法
     *
     * @param queryWrapper queryWrapper
     * @return T
     */
    @Override
    T getOne(Wrapper<T> queryWrapper);

    /**
     * 重写MyBatisPlus获取单个对象方法
     *
     * @param queryWrapper queryWrapper
     * @param throwEx      throwEx
     * @return T
     */
    @Override
    T getOne(Wrapper<T> queryWrapper, boolean throwEx);

    /**
     * 判断是否存在重复名称(新增)
     *
     * @param compareValue 比较重复值
     * @param eqColumns    比较重复列
     * @return true=存在重复,false=不存在重复
     */
    boolean duplicateName(String compareValue, SFunction<T, ?>... eqColumns);

    /**
     * 判断是否存在重复名称(编辑)
     *
     * @param selfId       自身ID
     * @param compareValue 比较重复值
     * @param eqColumns    比较重复列
     * @return true=存在重复,false=不存在重复
     */
    boolean duplicateName(Long selfId, String compareValue, SFunction<T, ?>... eqColumns);

    /**
     * 针对原有数据变更进行增删改操作
     *
     * @param relationColumn 关联字段
     * @param flagColumn     关联标识字段
     * @param relationId     关联ID
     * @param entityList     对象集合
     * @return true=操作成功,false=操作失败
     */
    boolean saveOrUpdate(SFunction<T, ?> relationColumn, SFunction<T, ?> flagColumn, Long relationId, Collection<T> entityList);

    /**
     * 是否存在数据
     *
     * @param value     比较值
     * @param eqColumns 比较列
     * @return true=存在,false=不存在
     */
    boolean exist(Object value, SFunction<T, ?>... eqColumns);

    /**
     * 删除
     *
     * @param eqColumn 比较列
     * @param value    比较值
     * @return true=操作成功,false=操作失败
     */
    boolean remove(SFunction<T, ?> eqColumn, Object value);
}
