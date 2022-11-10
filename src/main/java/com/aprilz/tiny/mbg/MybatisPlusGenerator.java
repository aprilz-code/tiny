package com.aprilz.tiny.mbg;

import com.aprilz.tiny.mbg.base.BaseEntity;
import com.aprilz.tiny.mbg.config.CodeGeneratorBo;
import com.aprilz.tiny.mbg.config.Config;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description: 执行main
 * @author: aprilz
 * @since: 2022/7/7
 **/
public class MybatisPlusGenerator {

    public static void main(String[] args) {
        //DB 配置
        String dbUrl = "jdbc:mysql://119.91.87.105:3306/tinymall";
        String driver = "com.mysql.cj.jdbc.Driver";
        String username = "tinymall";
        String password = "tinymall123456";


        //逗号分割
        String tableName = "ap_role_permission_relation";

        CodeGeneratorBo bo = new CodeGeneratorBo();
        bo.setDbUrl(dbUrl).setDriver(driver).setUsername(username).setPassword(password)
                .setTableNames(split(tableName))
                .setTablePrefixes(split(tableName))
                //   .setFieldPrefixes()
                //    .setExcludeTableNames(split(requestVo.getExcludeTableNames()))
                .setIgnoreColumns(split("id"));

        new MyAutoGenerator(bo).execute();
    }

    private static String[] split(String value) {
        if (!StringUtils.hasText(value)) {
            return new String[]{};
        }
        List<String> valueList = new ArrayList<>();
        String[] values;
        if (value.contains(",")) {
            values = value.split(",");
        } else if (value.contains("\n")) {
            values = value.split("\n");
        } else {
            values = value.split(" ");
        }
        for (String str : values) {
            str = str.trim();
            if (StringUtils.hasText(str)) {
                valueList.add(str);
            }
        }
        String[] result = new String[valueList.size()];
        return valueList.toArray(result);
    }

    static class MyAutoGenerator {

        private final CodeGeneratorBo bo;

        public MyAutoGenerator(CodeGeneratorBo bo) {
            this.bo = bo;
        }

        public void execute() {
            FastAutoGenerator.create(dataSourceBuilder())
                    .globalConfig(this::globalConfigBuilder)
                    .packageConfig(this::packageConfigBuilder)
                    .strategyConfig(this::strategyConfigBuilder)
                    .execute();
        }

        public DataSourceConfig.Builder dataSourceBuilder() {
            return new DataSourceConfig.Builder(bo.getDbUrl(), bo.getUsername(), bo.getPassword())
                    .typeConvert(new MySqlTypeConvert())
                    .keyWordsHandler(new MySqlKeyWordsHandler());
        }

        public void globalConfigBuilder(GlobalConfig.Builder builder) {

            builder.fileOverride().author(Config.AUTHOR).disableOpenDir();

            String outDir = Config.OUTPUT_DIR;
            builder.outputDir(outDir);
            DateType dateType = DateType.TIME_PACK;
            dateType = DateType.ONLY_DATE;
            builder.dateType(dateType);
            //开启swagger
            builder.enableSwagger();

        }

        public void packageConfigBuilder(PackageConfig.Builder builder) {
            builder
                    .parent(Config.PACKAGE_NAME)
                    // builder.moduleName("");
                    .controller(Config.PACKAGE_NAME_CONTROLLER)
                    .entity(Config.PACKAGE_NAME_ENTITY)
                    .mapper(Config.PACKAGE_NAME_DAO)
                    .xml(Config.DIR_NAME_XML)
                    .service(Config.PACKAGE_NAME_SERVICE)
                    .serviceImpl(Config.PACKAGE_NAME_SERVICE_IMPL)
                    .pathInfo(Collections.singletonMap(OutputFile.xml, Config.PROJECT_PATH + "/src/main/resources/mapper")); // 设置mapperXml生成路径;
        }

        public void strategyConfigBuilder(StrategyConfig.Builder builder) {
            builder.addInclude(bo.getTableNames())
                    .entityBuilder()
                    .enableChainModel()
                    .enableLombok()
                    .enableActiveRecord()
                    .enableTableFieldAnnotation()
                    .formatFileName(Config.FILE_NAME_ENTITY)
                    .idType(IdType.AUTO)
                    .logicDeleteColumnName(Config.FIELD_LOGIC_DELETE_NAME)
                    .logicDeletePropertyName(Config.FIELD_LOGIC_DELETE_NAME)
                    .superClass(BaseEntity.class)
                    .addSuperEntityColumns("id", "createBy", "createTime", "updateBy", "updateTime", "deleteFlag")
                    .controllerBuilder()
                    .enableRestStyle()
                    .enableHyphenStyle();


        }

    }
}
