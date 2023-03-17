package com.aprilz.excel.config;

import com.aprilz.excel.core.dict.DictDataApi;
import com.aprilz.excel.core.util.DictUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

/**
 * @author Aprilz
 * @date 2023/2/22-20:02
 * @description 装配Dic
 */
@AutoConfiguration
@ConditionalOnBean(value = DictDataApi.class)
public class AprilzDictAutoConfiguration {

//    @Bean
//    @ConditionalOnMissingBean
//    public DictDataApi DictDataApi() {
//        return new DictDataApi();
//    }


    @Bean
    public DictUtil dicUtil(DictDataApi dictDataApi) {
        DictUtil.init(dictDataApi);
        return new DictUtil();
    }
}
