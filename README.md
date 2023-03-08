# tiny

Mybatis-Plus基础框架,包括Mybatis-Plus-Generator。 登录/注册,以及完整的RBAC权限管理系统。

各种设计模式各案例以及编程式事务，以及工具类整理。 待补充。。。

## 1. TransactionSynchronizationManager.registerSynchronization
    编程式事务

## 2. lombok 1.14版本以上支持全局配置{@see lombok.config}
lombok全局配置

## 3. starter包相关 
 
### 3.1 easyexcel导出，数据过多，查询java.lang.OutOfMemoryError: GC overhead limit exceeded，采取分页然后写入excel

具体用法详见/admin/excel/**

**支持字典转换**
写 ： 继承DictDataApi(项目中-DictDataSearch),然后使用例如
@ExcelProperty(value = "性别", converter = DictConvert.class)
@DictFormat("dic_sex")


导入Excel时发现，属性值一直为null。。。。结果发现lombok和easyexcel冲突，解决方案如下
![img.png](docs/imgs/img.png)

//后续看看要不要考虑，在注解上加分页条数，然后根据条数，动态sheet分页吧


### 3.2 dtp 意为动态线程池。基于nacos动态@refresh原理实现


用法如下
nacos新建dataId为dtp.yml，group为DEFAULT_GROUP，修改的需要搜索dtp，然后同步修改nacosListener等位置
```yaml
dtp:
executors:
- name: t1
  core-pool-size: 25
  maximum-pool-Size: 100
- name: t2
  core-pool-size: 20
  maximum-pool-Size: 110
```

```java
import org.springframework.web.bind.annotation.RestController;

@RestController
public class A {
    
    @GetMapping("/test")
    public CommonResult<String> test() {
        DtpExecutor t1 = DtpUtil.get("t1");
        for (int i = 0; i < 50; i++) {
            t1.execute(() -> {
                try {
                    Thread.sleep(30000);
                    log.error(Thread.currentThread().getName() + "===isOk");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        return success("");
    }
}

```

修改dtp.yml里配置，然后查看NacosListener的监听方法

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

## 4.批量插入
### 1. xml <foreach>方式，sql长度有限制，不好排查
### 2. ExecutorType.BATCH 。。。。需要在url后面开启rewriteBatchedStatements=true，不然没效果。。（这里排查了半天，晕了 ）
具体用法参考： ApExcelTest2ServiceImpl 和 MybatisBatchUtils


https://github.com/aprilz-code/tiny.git



util使用：
TreeUtil 使用示例看代码




designMode 下 设计模式代码整理

    observer 观察者模式
    strategy 策略模式
    proxy 代理模式 
