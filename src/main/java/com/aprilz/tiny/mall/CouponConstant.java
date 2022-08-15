package com.aprilz.tiny.mall;

public class CouponConstant {
    //通用券
    public static final Integer TYPE_COMMON = 0;
    //注册券
    public static final Integer TYPE_REGISTER = 1;
    //兑换券
    public static final Integer TYPE_CODE = 2;

    //优惠券适用范围
    //全品类
    public static final Integer GOODS_TYPE_ALL = 0;
    //类目限制
    public static final Integer GOODS_TYPE_CATEGORY = 1;
    // 特定商品限制
    public static final Integer GOODS_TYPE_ARRAY = 2;


    //优惠券状态
    //正常可用
    public static final Integer STATUS_NORMAL = 0;
    //过期
    public static final Integer STATUS_EXPIRED = 1;

    //下架
    public static final Integer STATUS_OUT = 2;

    //优惠卷有效时间类型

    //从领取时间按日推
    public static final Integer TIME_TYPE_DAYS = 0;
    //按表start_time和end_time 开始结束时间计
    public static final Integer TIME_TYPE_TIME = 1;
}
