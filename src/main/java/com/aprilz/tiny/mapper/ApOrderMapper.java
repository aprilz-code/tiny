package com.aprilz.tiny.mapper;

import com.aprilz.tiny.mbg.entity.ApOrder;
import com.aprilz.tiny.vo.OrdersListVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
public interface ApOrderMapper extends BaseMapper<ApOrder> {

    @Select("SELECT id,order_sn,order_status,actual_price,aftersale_status FROM `ap_order`  ${ew.customSqlSegment}")
    Page<OrdersListVo> pageVo(Page<OrdersListVo> pages, @Param(Constants.WRAPPER) QueryWrapper<OrdersListVo> queryWrapper);
}
