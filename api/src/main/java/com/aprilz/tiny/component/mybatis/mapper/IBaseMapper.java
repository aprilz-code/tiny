package com.aprilz.tiny.component.mybatis.mapper;

import com.aprilz.tiny.common.api.PageParam;
import com.aprilz.tiny.common.utils.MyBatisUtils;
import com.aprilz.tiny.component.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 自定义Mapper
 *
 * @author Xjr
 * @version 1.0
 * @date 2023-03-24 15:21
 */
public interface IBaseMapper<T extends BaseEntity> extends BaseMapper<T> {
    default IPage<T> selectPage(PageParam pageParam, @Param("ew") Wrapper<T> queryWrapper) {
        // MyBatis Plus 查询
        IPage<T> mpPage = MyBatisUtils.buildPage(pageParam);
        selectPage(mpPage, queryWrapper);
        return mpPage;
    }

    default T selectOne(String field, Object value) {
        return selectOne(new QueryWrapper<T>().eq(field, value));
    }

    default T selectOne(SFunction<T, ?> field, Object value) {
        return selectOne(new LambdaQueryWrapper<T>().eq(field, value));
    }

    default T selectOne(String field1, Object value1, String field2, Object value2) {
        return selectOne(new QueryWrapper<T>().eq(field1, value1).eq(field2, value2));
    }

    default T selectOne(SFunction<T, ?> field1, Object value1, SFunction<T, ?> field2, Object value2) {
        return selectOne(new LambdaQueryWrapper<T>().eq(field1, value1).eq(field2, value2));
    }

    default Long selectCount() {
        return selectCount(new QueryWrapper<T>());
    }

    default Long selectCount(String field, Object value) {
        return selectCount(new QueryWrapper<T>().eq(field, value));
    }

    default Long selectCount(SFunction<T, ?> field, Object value) {
        return selectCount(new LambdaQueryWrapper<T>().eq(field, value));
    }

    default List<T> selectList() {
        return selectList(new QueryWrapper<>());
    }

    default List<T> selectList(String field, Object value) {
        return selectList(new QueryWrapper<T>().eq(field, value));
    }

    default List<T> selectList(SFunction<T, ?> field, Object value) {
        return selectList(new LambdaQueryWrapper<T>().eq(field, value));
    }

    default List<T> selectList(String field, Collection<?> values) {
        return selectList(new QueryWrapper<T>().in(field, values));
    }

    default List<T> selectList(SFunction<T, ?> field, Collection<?> values) {
        return selectList(new LambdaQueryWrapper<T>().in(field, values));
    }

    default List<T> selectList(SFunction<T, ?> leField, SFunction<T, ?> geField, Object value) {
        return selectList(new LambdaQueryWrapper<T>().le(leField, value).ge(geField, value));
    }

    /**
     * 逐条插入，适合少量数据插入，或者对性能要求不高的场景
     * <p>
     * 如果大量，请使用 {@link com.baomidou.mybatisplus.extension.service.impl.ServiceImpl#saveBatch(Collection)} 方法
     * 使用示例，可见 RoleMenuBatchInsertMapper、UserRoleBatchInsertMapper 类
     *
     * @param entities 实体们
     */
    default void insertBatch(Collection<T> entities) {
        entities.forEach(this::insert);
    }

    default void updateBatch(T update) {
        update(update, new QueryWrapper<>());
    }

    /**
     * 批量插入
     *
     * @param entityList 对象集合
     * @return 影响行数
     */
    int insertBatchSomeColumn(Collection<T> entityList);

    /**
     * 通过ID批量更新数据
     *
     * @param entityList    对象集合
     * @param updateWrapper 更新字段Wrapper对象
     * @return 影响行数
     */
    int updateBatchById(@Param("list") Collection<T> entityList, @Param("ew") Wrapper<T> updateWrapper);
}
