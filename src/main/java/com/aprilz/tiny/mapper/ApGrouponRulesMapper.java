package com.aprilz.tiny.mapper;

import com.aprilz.tiny.mbg.entity.ApGrouponRules;
import com.aprilz.tiny.vo.GrouponRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 团购规则表 Mapper 接口
 * </p>
 *
 * @author aprilz
 * @since 2022-07-19
 */
public interface ApGrouponRulesMapper extends BaseMapper<ApGrouponRules> {

    @Select("SELECT g.id,u.`goods_name` as name,u.expire_time,g.brief,g.pic_url,g.counter_price,g.retail_price,(g.retail_price -  u.discount ) AS grouponPrice,u.discount AS grouponDiscount,u.discount_member AS grouponMember  FROM `ap_groupon_rules` u INNER JOIN `ap_goods` g ON u.goods_id = g.`id` ${ew.customSqlSegment}")
    Page<GrouponRuleVo> queryPage(Page<GrouponRuleVo> pages, @Param(Constants.WRAPPER) QueryWrapper<GrouponRuleVo> queryWrapper);
}
