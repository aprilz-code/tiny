# tiny

Mybatis-Plus基础框架,包括Mybatis-Plus-Generator。 登录/注册,以及完整的RBAC权限管理系统。

各种设计模式各案例以及编程式事务，以及工具类整理。 待补充。。。

1. TransactionSynchronizationManager.registerSynchronization

2. lombok 1.14版本以上支持全局配置{@see lombok.config}


3. easyexcel导出，数据过多，查询java.lang.OutOfMemoryError: GC overhead limit exceeded，采取分页然后写入excel

具体用法详见/admin/excel/**

**支持字典转换**
写 ： 继承DictDataApi(项目中-DictDataSearch),然后使用例如
@ExcelProperty(value = "性别", converter = DictConvert.class)
@DictFormat("dic_sex")


导入Excel时发现，属性值一直为null。。。。结果发现lombok和easyexcel冲突，解决方案如下
![img.png](docs/imgs/img.png)

//后续看看要不要考虑，在注解上加分页条数，然后根据条数，动态sheet分页吧


starter springboot2.7.5及以上使用META-INF-spring下文件，2.7.5以下使用spring.factories,也可使用mico-auto包，自动生成META-INF文件

```xml
   <!-- mica-auto -->
        <dependency>
            <groupId>net.dreamlu</groupId>
            <artifactId>mica-auto</artifactId>
            <version>${mica.version}</version>
            <scope>provided</scope>
        </dependency>
```
https://github.com/aprilz-code/tiny.git
