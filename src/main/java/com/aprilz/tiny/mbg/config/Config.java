package com.aprilz.tiny.mbg.config;

/**
 * @author Erwin Feng
 * @since 2019-04-17 12:04
 */
public class Config {

    public static final String PACKAGE_NAME = "com.aprilz.tiny";

    /** 包名：controller */
    public static final String PACKAGE_NAME_CONTROLLER = "controller";

    /** 包名：service */
    public static final String PACKAGE_NAME_SERVICE = "service";

    /** 包名：service.impl */
    public static final String PACKAGE_NAME_SERVICE_IMPL = "service.impl";

    /** 包名：ENTITY */
    public static final String PACKAGE_NAME_ENTITY = "mbg.entity";

    /** 包名：dao */
    public static final String PACKAGE_NAME_DAO = "mapper";

    /** 目录名：xml */
    public static final String DIR_NAME_XML = "mapper";

    /** 文件名后缀：Entity */
    public static final String FILE_NAME_ENTITY = "%sEntity";

    /** 文件名后缀：Dao */
    public static final String FILE_NAME_DAO = "I%sDao";

    /** 文件名后缀：Mapper */
    public static final String FILE_NAME_XML = "%sMapper";

    /** Service结尾 */
    public static final String FILE_NAME_SERVICE = "%sService";

    /** 文件名后缀：ServiceImpl */
    public static final String FILE_NAME_SERVICE_IMPL = "%sServiceImpl";

    /** 文件名后缀：Controller */
    public static final String FILE_NAME_CONTROLLER = "%sController";

    /** 逻辑删除字段 */
    public static final String FIELD_LOGIC_DELETE_NAME = "status";


    /** 乐观锁字段名 */
    public static final String FIELD_VERSION_NAME = "version";

    /** 作者 */
    public static final String AUTHOR = "aprilz";

    /** 生成文件的输出目录 */
    public static String PROJECT_PATH = System.getProperty("user.dir");

    /** 输出目录 */
    public static final String OUTPUT_DIR = PROJECT_PATH + "/src/main/java";

    /** 模板引擎。velocity / freemarker / beetl */
    public static final String TEMPLATE_ENGINE = "velocity";

    /** 是否支持Swagger，默认不支持 */
    public static final Boolean SWAGGER_SUPPORT = false;

}
