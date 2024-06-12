package com.aprilz.tiny.component.mybatis.service;


import com.aprilz.tiny.component.mybatis.base.BaseTreeDO;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IBaseTreeService<T extends BaseTreeDO> extends IService<T> {

    Long saveOrUpdateTree(T entity);

    boolean deleteByParentId(Long parentId, Boolean itself);

    boolean deleteByFindInSetParentId(Long parentId, Boolean notItself);

    List<T> getChildrenByParentId(Long parentId, Boolean itself);

    List<T> findInSetByParentId(Long parentId, Boolean notItself);

    T getParentByParentId(Long parentId);

    List<T> getParentByParentIds(String parentIds);

    /**
     * 是否存在数据
     *
     * @param value     比较值
     * @param eqColumns 比较列
     * @return true=存在,false=不存在
     */
    boolean exist(Object value, SFunction<T, ?>... eqColumns);


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

}
