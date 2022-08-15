package com.aprilz.tiny.mapper;

import com.aprilz.tiny.mbg.entity.ApFootprint;
import com.aprilz.tiny.vo.ApFootprintVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户浏览足迹表 Mapper 接口
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
public interface ApFootprintMapper extends BaseMapper<ApFootprint> {

    @Select("SELECT p.*,g.`name`,g.`brief`,g.`pic_url`,g.`retail_price` FROM `ap_footprint` p INNER JOIN `ap_goods` g ON p.`goods_id` = g.`id`  ${ew.customSqlSegment}")
    Page<ApFootprintVo> getPageVo(Page<ApFootprintVo> pages, @Param(Constants.WRAPPER) QueryWrapper<ApFootprintVo> queryWrapper);
}
