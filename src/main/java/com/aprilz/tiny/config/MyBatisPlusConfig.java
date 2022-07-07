package com.aprilz.tiny.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created by aprilz on 2019/4/8.
 */
@Configuration
@MapperScan({"com.aprilz.tiny.mapper"})
public class MyBatisPlusConfig {
    /**
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;

        //阻断解析器，测试环境使用
//       PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//
//       List<ISqlParser> sqlParserList = new ArrayList<>();
//       //攻击 SQL 阻断解析器、加入解析链
//       sqlParserList.add(new BlockAttackSqlParser());
//       paginationInterceptor.setSqlParserList(sqlParserList);
//       return paginationInterceptor;
    }

}
