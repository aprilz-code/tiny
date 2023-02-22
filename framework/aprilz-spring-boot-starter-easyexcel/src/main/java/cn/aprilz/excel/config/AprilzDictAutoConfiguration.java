package cn.aprilz.excel.config;

import cn.aprilz.excel.core.util.DictDataApi;
import cn.aprilz.excel.core.util.DictUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author Aprilz
 * @date 2023/2/22-20:02
 * @description 装配Dic
 */
@AutoConfiguration
public class AprilzDictAutoConfiguration {


    @Bean
    public DictUtil dicUtil(DictDataApi dictDataApi) {
        DictUtil.init(dictDataApi);
        return new DictUtil();
    }
}
