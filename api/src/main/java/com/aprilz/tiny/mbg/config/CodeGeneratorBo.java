package com.aprilz.tiny.mbg.config;

import lombok.Data;

/**
 * @description: 基础配置类
 * @author: aprilz
 * @since: 2022/7/7
 **/
@Data
public class CodeGeneratorBo {

    // 包名
    private String packageName;

    // 数据库类型
    private String dbType;
    // 数据库连接地址
    private String dbUrl;
    // 数据库名称
    private String username;
    // 数据库密码
    private String password;
    // 数据库驱动
    private String driver;

    // 表名
    private String[] tableNames;
    // 表前缀
    private String[] tablePrefixes;
    // 字段前缀
    private String fieldPrefixes;
    // 排出表的表名
    private String[] excludeTableNames;
    // 忽略的字段
    private String[] ignoreColumns;

    // 作者
    private String author;
    // 输入目录
    private String outDir;

    // 实体类包名
    private String packageEntity;
    // mapper包名
    private String packageMapper;
    // mapper XML目录名
    private String packageMapperXml;
    // service包名
    private String packageService;
    // serviceImpl包名
    private String packageServiceImpl;
    // controller包名
    private String packageController;

    // 实体类文件名格式
    private String fileNamePatternEntity;
    // mapper文件名格式
    private String fileNamePatternMapper;
    // mapper XML文件名格式
    private String fileNamePatternMapperXml;
    // service文件名格式
    private String fileNamePatternService;
    // serviceImpl文件名格式
    private String fileNamePatternServiceImpl;
    // controller文件名格式
    private String fileNamePatternController;

    // 逻辑删除字段
    private String fieldLogicDelete;
    // 乐观锁字段
    private String fieldVersion;

    // 模版引擎
    private String templateEngine;
    // 是否支持Swagger
    private Boolean swaggerSupport;
    // JDK版本
    private String jdkVersion;

    // 是否开启Lombok
    private Boolean lombokModel;

    // 是否使用构建者模型
    private Boolean lombokChainModel;

    // 字段注解
    private Boolean fieldAnnotation;

    // 列常量
    private Boolean columnConstant;

    // 二级缓存
    private Boolean enableCache;

    // 基础结果Map
    private Boolean baseResultMap;

    // 基础列名List
    private Boolean baseColumnList;

    // 实体父类的全类名
    private String superClassName;

    // mapper注解
    private Boolean mapperAnnotation;


}
