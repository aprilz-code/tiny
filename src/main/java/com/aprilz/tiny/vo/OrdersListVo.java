package com.aprilz.tiny.vo;

import com.aprilz.tiny.mall.utils.OrderHandleOption;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @description: 订单list vo
 * @author: Aprilz
 * @since: 2022/8/1
 **/
@Data
public class OrdersListVo {
    private Long id;
    private String orderSn;
    private BigDecimal actualPrice;
    private Integer orderStatus;
    private String orderStatusText;
    private Integer aftersaleStatus;
    //根据状态处理---订单可操作的行为
    private OrderHandleOption handleOption;
    private boolean isGroupin;
    private List<Map<String, Object>> goodsList;

}
