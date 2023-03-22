package com.aprilz.tiny.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ：Aprilz
 * @description：本地缓存
 */
public class LocalCache<T> {
    /**
     * guava cache
     */
    private Cache<String, Optional<T>> cacheHolder;

    private int maximumSize = 500;

    private int duration = 7;

    private TimeUnit timeUnit = TimeUnit.MINUTES;

    /**
     * 配置缓存参数
     * @param size
     * @param duration
     * @return
     */
    public LocalCache<T> setParameters(int size, int duration, TimeUnit timeUnit) {
        this.maximumSize = size;
        this.duration = duration;
        this.timeUnit = timeUnit;
        return this;
    }


    /**
     * 构建一个缓存
     * @return
     */
    public LocalCache<T> build() {
        cacheHolder = CacheBuilder.newBuilder().maximumSize(maximumSize).expireAfterAccess(duration, timeUnit).build();
        return this;
    }

    /**
     * 查询
     * 如果缓存中没有数据, 则执行业务方法进行查询
     * @param key
     * @param service
     * @return
     */
    public Optional<T> query(String key, ICache<T> service) {
        try{
            if(StringUtils.isBlank(key)) {
                return Optional.empty();
            }
            Optional<T> value = cacheHolder.get(key, () -> service.query(key));
            // 设置进入缓存
            put(key, value);
            return value;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    /**
     * 把值推到缓存中
     * @param key
     * @param optional
     */
    public void put(String key, Optional<T> optional) {
        cacheHolder.put(key, optional);
    }


    public void invalidateAll(){
        cacheHolder.invalidateAll();
    }


    /**
     * 通用接口, 利用模板方法设计模式, 将业务方法抽取出来. 不同的业务 传递不同的查询逻辑.
     * @param <T>
     */
    public interface ICache<T> {
        /**
         * 通用接口
         * @param key
         * @return
         */
        Optional<T> query(String key);
    }
}
