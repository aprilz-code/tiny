package com.aprilz.tiny.mapper;

import com.aprilz.tiny.mbg.entity.ApAftersale;
import com.aprilz.tiny.vo.ApAftersaleListVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 售后表 Mapper 接口
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
public interface ApAftersaleMapper extends BaseMapper<ApAftersale> {

    @Select("SELECT * FROM `ap_aftersale` t   ${ew.customSqlSegment}")
    IPage<ApAftersaleListVo> queryList(Page<ApAftersaleListVo> pages, @Param(Constants.WRAPPER) QueryWrapper<ApAftersaleListVo> queryWrapper);
}
