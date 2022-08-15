package com.aprilz.tiny.mapper;

import com.aprilz.tiny.mbg.entity.ApCouponUser;
import com.aprilz.tiny.vo.CouponVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 优惠券用户使用表 Mapper 接口
 * </p>
 *
 * @author aprilz
 * @since 2022-07-14
 */
public interface ApCouponUserMapper extends BaseMapper<ApCouponUser> {

    @Select("SELECT u.id,c.id AS cid,c.`name`,c.`desc`,c.`tag`,c.`min`,c.`discount`,c.`start_time`,c.`end_time` FROM ap_coupon_user u INNER JOIN `ap_coupon` c ON u.`coupon_id` = c.id ${ew.customSqlSegment}")
    IPage<CouponVo> pageCoupon(Page<CouponVo> objectPage, @Param(Constants.WRAPPER) QueryWrapper<CouponVo> queryWrapper);
}
