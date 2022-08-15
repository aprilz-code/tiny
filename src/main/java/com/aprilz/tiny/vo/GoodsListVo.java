package com.aprilz.tiny.vo;

import com.aprilz.tiny.mbg.entity.ApGoods;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @description: 查询商品list vo
 * @author: Aprilz
 * @since: 2022/7/20
 **/
@Data
public class GoodsListVo {

    Page<ApGoods> goodsPage;
}
