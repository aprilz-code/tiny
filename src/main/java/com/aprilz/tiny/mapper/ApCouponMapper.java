package com.aprilz.tiny.mapper;

import com.aprilz.tiny.mbg.entity.ApCoupon;
import com.aprilz.tiny.vo.CouponVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 优惠券信息及规则表 Mapper 接口
 * </p>
 *
 * @author aprilz
 * @since 2022-07-14
 */
public interface ApCouponMapper extends BaseMapper<ApCoupon> {

    @Select("SELECT u.id,t.`name`,t.`desc`,t.`tag`,t.`total`,t.`discount`,t.`min`,t.`limit`,t.`type`,t.`goods_type`,t.`goods_value`,t.`code`,t.`time_type`,t.`days`,u.`start_time`,u.`end_time` FROM ap_coupon t  INNER JOIN `ap_coupon_user` u ON t.id = u.coupon_id  ${ew.customSqlSegment}")
    IPage<CouponVo> getPageVo(Page<CouponVo> page, @Param(Constants.WRAPPER) QueryWrapper<CouponVo> queryWrapper);


    List<ApCoupon> queryAvailableList(@Param("userId") Long userId);
}
