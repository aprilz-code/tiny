package com.aprilz.tiny.common.utils;

/**
 * @description: expire  hashmap
 * @author: Aprilz
 * @since: 2022/10/21
 **/

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class ExpireHashMap<K, V> extends HashMap<K, V> {

    private static volatile ExpireHashMap instance = null;
    /**
     * 记录每个entry的到期时间
     */
    private HashMap<K, Date> expireMap = new HashMap<K, Date>();

    private ExpireHashMap() {
    }

    public static ExpireHashMap getInstance() {
        if (instance == null) {
            synchronized (ExpireHashMap.class) //b
            {
                if (instance == null) {
                    instance = new ExpireHashMap();
                }
            }
        }
        return instance;
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        ExpireHashMap instance = ExpireHashMap.getInstance();
        ExpireHashMap instance2 = ExpireHashMap.getInstance();
        instance.put("name", new BigDecimal("100"), 1000L);
        instance2.put("value", new BigDecimal("200"), 3000L);
        System.out.println(instance == instance2);
        System.out.println(instance.get("name"));
        System.out.println(instance.get("value"));
        Thread.sleep(2000);
        System.out.println(instance.get("name"));
        System.out.println(instance2.get("name"));
        System.out.println(instance.get("value"));
        System.out.println(instance2.get("value"));
    }

    /**
     * 判断entry是否到期
     */
    private boolean isExpire(K key) {
        if (!super.containsKey(key)) return true;

        Date expireDate = this.expireMap.get(key);
        return DateUtil.now().after(expireDate);
    }

    /**
     * 存
     *
     * @param survivalTime 该entry的存活时间，单位毫秒
     */
    public V put(K key, V value, Long survivalTime) {
        this.expireMap.put(key, DateUtil.afterDate(DateUtil.now(), survivalTime));
        return super.put(key, value);
    }

    /**
     * 取
     */
    @Override
    public V get(Object key) {
        K k = (K) key;
        if (isExpire(k)) return null;
        return super.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        K k = (K) key;
        if (isExpire(k)) return false;
        return super.containsKey(key);
    }

    private static class DateUtil {
        private static Date now() {
            return new Date();
        }

        /**
         * date后ms毫秒的时间
         *
         * @param ms 毫秒数
         */
        private static Date afterDate(Date date, Long ms) {
            return new Date(date.getTime() + ms);
        }
    }
}

