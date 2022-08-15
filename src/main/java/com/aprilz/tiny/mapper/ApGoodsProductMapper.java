package com.aprilz.tiny.mapper;

import com.aprilz.tiny.mbg.entity.ApGoodsProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品货品表 Mapper 接口
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
public interface ApGoodsProductMapper extends BaseMapper<ApGoodsProduct> {

    int reduceStock(@Param("productId") Long productId, @Param("number") Integer number);

    int addStock(@Param("productId") Long productId, @Param("number") Integer number);
}
