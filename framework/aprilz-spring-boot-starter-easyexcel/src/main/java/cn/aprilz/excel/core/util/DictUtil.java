package cn.aprilz.excel.core.util;

import cn.aprilz.excel.core.KeyValue;
import cn.aprilz.excel.core.dto.DictDataDTO;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

/**
 * @author Aprilz
 * @date 2023/2/22-16:22
 * @description 字典工具类
 */
@Slf4j
public class DictUtil {

    private static DictDataApi dictDataApi;

    private static final DictDataDTO DICT_DATA_NULL = new DictDataDTO();

    /**
     * 懒加载
     * 针对 {@link #getDictDataByKey(String, String)} 的缓存
     */
    private static final LoadingCache<KeyValue<String, String>, DictDataDTO> GET_DICT_DATA_CACHE = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<KeyValue<String, String>, DictDataDTO>() {

                @Override
                public DictDataDTO load(KeyValue<String, String> key) {
                    return ObjectUtil.defaultIfNull(dictDataApi.getDictDataByKey(key.getKey(), key.getValue()), DICT_DATA_NULL);
                }

            });

    /**
     * 针对 {@link #getDictDataByValue(String, String)} 的缓存
     */
    private static final LoadingCache<KeyValue<String, String>, DictDataDTO> PARSE_DICT_DATA_CACHE = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<KeyValue<String, String>, DictDataDTO>() {

                @Override
                public DictDataDTO load(KeyValue<String, String> key) {
                    return ObjectUtil.defaultIfNull(dictDataApi.getDictDataByValue(key.getKey(), key.getValue()), DICT_DATA_NULL);
                }

            });

    public static void init(DictDataApi dictDataApi) {
        DictUtil.dictDataApi = dictDataApi;
        log.info("[init][初始化 DictUtil 成功]");
    }

    @SneakyThrows
    public static String getDictDataByKey(String dictType, String key) {
        return GET_DICT_DATA_CACHE.get(new KeyValue<>(dictType, key)).getValue();
    }

    @SneakyThrows
    public static String getDictDataByValue(String dictType, String value) {
        return PARSE_DICT_DATA_CACHE.get(new KeyValue<>(dictType, value)).getValue();
    }

}
