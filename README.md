# tiny

Mybatis-Plus基础框架,包括Mybatis-Plus-Generator。 登录/注册,以及完整的RBAC权限管理系统。

各种设计模式各案例以及编程式事务，以及工具类整理。 待补充。。。

1. TransactionSynchronizationManager.registerSynchronization

2. lombok 1.14版本以上支持全局配置{@see lombok.config}


3. easyexcel导出，数据过多，查询java.lang.OutOfMemoryError: GC overhead limit exceeded，采取分页然后写入excel

写 ： 实现DictDataApi,然后使用例如
@ExcelProperty(value = "性别", converter = DictConvert.class)
@DictFormat("dic_sex")

https://github.com/aprilz-code/tiny.git
