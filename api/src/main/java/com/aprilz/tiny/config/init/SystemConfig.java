package com.aprilz.tiny.config.init;

/**
 * @description: 系统设置
 * @author: aprilz
 * @since: 2022/7/19
 **/
public class SystemConfig {
    // 小程序相关配置
    //新品首发栏目商品显示数量
    public final static String AP_WX_INDEX_NEW = "ap_wx_index_new";
    //人气推荐栏目商品显示数量
    public final static String AP_WX_INDEX_HOT = "ap_wx_index_hot";
    //品牌制造商直供栏目品牌商显示数量
    public final static String AP_WX_INDEX_BRAND = "ap_wx_index_brand";
    //专题精选栏目显示数量
    public final static String AP_WX_INDEX_TOPIC = "ap_wx_index_topic";
    //分类栏目显示数量
    public final static String AP_WX_INDEX_CATLOG_LIST = "ap_wx_catlog_list";
    //分类栏目商品显示数量
    public final static String AP_WX_INDEX_CATLOG_GOODS = "ap_wx_catlog_goods";
    //商品分享功能
    public final static String AP_WX_SHARE = "ap_wx_share";
    // 运费相关配置
    // 运费满减不足所需运费
    public final static String AP_EXPRESS_FREIGHT_VALUE = "ap_express_freight_value";
    //运费满减所需最低消费 (即满多少就包邮)
    public final static String AP_EXPRESS_FREIGHT_MIN = "ap_express_freight_min";
    // 订单相关配置
    //用户下单后超时（分钟）
    public final static String AP_ORDER_UNPAID = "ap_order_unpaid";
    //未确认收货，则订单自动确认收货（天）
    public final static String AP_ORDER_UNCONFIRM = "ap_order_unconfirm";
    //未评价商品，则取消评价资格（天）
    public final static String AP_ORDER_COMMENT = "ap_order_comment";

    // 商场相关配置
    public final static String AP_MALL_NAME = "ap_mall_name";
    public final static String AP_MALL_ADDRESS = "ap_mall_address";
    public final static String AP_MALL_PHONE = "ap_mall_phone";
    public final static String AP_MALL_QQ = "ap_mall_qq";
    //经度
    public final static String AP_MALL_LONGITUDE = "ap_mall_longitude";
    //纬度
    public final static String AP_MALL_LATITUDE = "ap_mall_latitude";

}
