# tiny

代码地址 ： https://github.com/aprilz-code/tiny.git  
代码生成器地址：https://github.com/aprilz-code/code-generate.git

Mybatis-Plus基础框架,包括Mybatis-Plus-Generator。 登录/注册,以及完整的RBAC权限管理系统。

各种设计模式各案例以及编程式事务，以及工具类整理。 待补充。。。

## 1. TransactionSynchronizationManager.registerSynchronization

    编程式事务

## 2. lombok 1.14版本以上支持全局配置{@see lombok.config}

lombok全局配置

## 3. starter包相关

### 3.1 easyexcel导出，数据过多，查询java.lang.OutOfMemoryError: GC overhead limit exceeded，采取分页然后写入excel

具体用法详见ApExcelTestController 内

```java
package com.aprilz.tiny.controller;

import com.aprilz.excel.core.annotations.RequestExcel;
import com.aprilz.excel.core.annotations.ResponseExcel;
import com.aprilz.excel.core.annotations.Sheet;
import com.aprilz.excel.core.exception.ErrorMessage;
import com.aprilz.excel.core.util.ExcelUtil;
import cn.hutool.core.collection.CollUtil;
import com.aprilz.tiny.common.api.CommonResult;
import com.aprilz.tiny.mbg.entity.ApExcelTest;
import com.aprilz.tiny.service.IApExcelTestService;
import com.aprilz.tiny.service.impl.ApExcelTest2ServiceImpl;
import com.aprilz.tiny.vo.request.ApExcelTestParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * excel-test表 前端控制器
 * </p>
 *
 * @author Aprilz
 * @since 2023-02-22
 */
@RestController
@RequestMapping("/excel")
public class ApExcelTestController {

    @Resource
    private IApExcelTestService iApExcelTestService;

    @Resource
    private ApExcelTest2ServiceImpl test2Service;

    @GetMapping("/list")
    public CommonResult<List<ApExcelTest>> getAll() {
        return CommonResult.success(iApExcelTestService.list());
    }

    /**
     * 普通导出数据
     * @param response
     * @throws IOException
     */
    @GetMapping("/test")
    public void test(HttpServletResponse response) throws IOException {
        List<ApExcelTest> datas = iApExcelTestService.lambdaQuery().last("limit 2000").list();
        // 输出
        ExcelUtil.write(response, "test", "数据", ApExcelTest.class, datas);

    }


    /**
     * 注解式导出单sheet数据
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/responseExcelTest")
    @ResponseExcel(name = "数据")
    // @ResponseExcel(name = "数据", sheets = @Sheet(sheetName = "testSheet1"))
    public List<ApExcelTest> test2(HttpServletResponse response) throws IOException {
        List<ApExcelTest> datas = iApExcelTestService.lambdaQuery().last("limit 2000").list();
        return datas;
    }

    /**
     * 注解式导出多页数据
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/sheetTest")
    @ResponseExcel(name = "数据", sheets = {@Sheet(sheetName = "testSheet1"), @Sheet(sheetName = "testSheet2")})
    public List<List<ApExcelTest>> test3(HttpServletResponse response) throws IOException {
        List<List<ApExcelTest>> lists = new ArrayList<>();
        List<ApExcelTest> datas1 = iApExcelTestService.lambdaQuery().last("limit 2000").list();
        lists.add(datas1);
        List<ApExcelTest> datas2 = iApExcelTestService.lambdaQuery().last("limit 2001,2000").list();
        lists.add(datas2);
        return lists;
    }

    /**
     *  普通导入数据
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/import")
    public CommonResult<String> importExcel(@RequestParam("file") MultipartFile file
    ) throws Exception {
//        List<ApExcelTestParam> list = EasyExcel.read(file.getInputStream(), ApExcelTestParam.class, BeanUtils.instantiateClass(DefaultAnalysisEventListener.class)).sheet()
//                .doReadSync();
        List<ApExcelTestParam> list = ExcelUtil.read(file, ApExcelTestParam.class);
        System.out.println(list);
        //入库
        return CommonResult.success();
    }

    /**
     * 注解式导入数据
     * @param dataList
     * @param bindingResult
     * @return
     */
    @PostMapping("/requestExcelImport")
    public CommonResult<String> upload(@RequestExcel List<ApExcelTestParam> dataList, BindingResult bindingResult) {
        // JSR 303 校验通用校验获取失败的数据
        List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();
        if (CollUtil.isNotEmpty(errorMessageList)) {
            // System.out.println(errorMessageList.toString());
            return CommonResult.error(errorMessageList.toString());
        }
        //  System.out.println(errorMessageList);
        System.out.println(dataList);
        //入库
        return CommonResult.success();
    }


    /**
     * 注解式导入数据  **支持传入自定义参数去检验 **
     * @param dataList
     * @param bindingResult
     * @return
     */
    @PostMapping("/requestExcelImport2")
    public CommonResult<String> requestExcelImport2(@RequestExcel List<ApExcelTestParam> dataList, String excelCustom, BindingResult bindingResult) {
        // JSR 303 校验通用校验获取失败的数据
        List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();
        if (CollUtil.isNotEmpty(errorMessageList)) {
            // System.out.println(errorMessageList.toString());
            return CommonResult.error(errorMessageList.toString());
        }
        //  System.out.println(errorMessageList);
        System.out.println(dataList);
        //入库
        return CommonResult.success();
    }


    /**
     * 模拟大批量数据导入
     * @return
     */
    @PostMapping("/upload3")
    public CommonResult<String> upload3(@RequestParam("file") MultipartFile file) throws IOException {
        iApExcelTestService.upload3(file);
        return CommonResult.success();
    }


    /**
     * 模拟大批量数据导入
     * @return
     */
    @PostMapping("/test3")
    public CommonResult<String> test3() throws IOException {
        test2Service.writeData(new ArrayList<ApExcelTestParam>(), 0, 2000);
        return CommonResult.success();
    }
}

```

**支持传入自定义参数去检验 **
假如商品excel导入入库时，需校验商品必须是同一分类下，则需要传入分类id。（例子可能不恰当，意思自行理解）
则传入excelCustom参数，可传入string或者array接收

```java
package com.aprilz.excel.core.annotations;

import com.aprilz.excel.core.handler.DefaultAnalysisEventListener;
import com.aprilz.excel.core.handler.ListAnalysisEventListener;

import java.lang.annotation.*;

/**
 * 导入excel
 *
 * @date 2021/4/16
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestExcel {

    /**
     * 前端上传字段名称 file
     */
    String fileName() default "file";

    /**
     * 自定义扩展字段
     */
    String excelCustom() default "excelCustom";

    /**
     * 读取的监听器类
     *
     * @return readListener
     */
    Class<? extends ListAnalysisEventListener<?>> readListener() default DefaultAnalysisEventListener.class;

    /**
     * 是否跳过空行
     *
     * @return 默认跳过
     */
    boolean ignoreEmptyRow() default false;

    /**
     * 指定读取的标题行
     *
     * @return
     */
    int headRowNumber() default 1;

}

```

![img.png](docs/imgs/img_fox.png)

![img_1.png](docs/imgs/img_1.png)

![img_2.png](docs/imgs/img_2.png)

**支持字典转换**
写 ： 继承DictDataApi(项目中-DictDataSearch),然后使用例如

```java
    @ExcelProperty(value = "性别", converter = DictConvert.class)
@DictFormat("dic_sex")
private Integer sex;
```

**新增注解 @FieldRepeat，处理excel导入数据时的重复校验字段**

```java
package com.aprilz.excel.core.annotations;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Aprilz
 * @date 2023/3/10-9:33
 * @description 导入数据字段重复性校验使用
 *  使用示例 @FieldRepeat(fields={"name","typeCode"},message="字典名称重复，请重新输入！")
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldRepeat {
    /**
     * 需要校验的字段
     * @return
     */
    String[] fields() default {};

    String message() default "存在重复数据";

}

```

**新增@DropDownFields 注解，处理自定义数据下拉框**
使用如ApExcelTest，支持固定值，动态值下拉框，实现IDropDownService接口，重写 getSource接口，可参考 DictDataSearch类

**ExcelLine给导入时Long类型字段写入行号，其他类型不可用**

```java
package com.aprilz.excel.core.annotations;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelLine {

}

```

**新增@ChainDropDownFields 注解，处理级联自定义数据下拉框**

用法如下,实体类中：  
示例1 ：

```java
@Data
// 内容行高度
@ContentRowHeight(20)
// 头部行高度
@HeadRowHeight(25)
// 列宽，可在类或属性中使用
@ColumnWidth(25)
@Data
// 内容行高度
@ContentRowHeight(20)
// 头部行高度
@HeadRowHeight(25)
// 列宽，可在类或属性中使用
@ColumnWidth(25)
public class ChainTestTemplate {

    @ExcelProperty("用户名称")
    private String name;

    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("国家")
    @ChainDropDownFields(isRoot = true,sourceClass = TestChainDropDownService.class,type = ChainDropDownType.TEST)
    private String country;

    @ExcelProperty("省份")
    @ChainDropDownFields(sourceClass = TestChainDropDownService.class,type = ChainDropDownType.TEST,params = {"2"})
    private String province;

    @ExcelProperty("城市")
    @ChainDropDownFields(sourceClass = TestChainDropDownService.class,type = ChainDropDownType.TEST,params = {"3"})
    private String city;

    @ExcelProperty("区域")
    @ChainDropDownFields(sourceClass = TestChainDropDownService.class,type = ChainDropDownType.TEST,params = {"4"})
    private String zone;
}
```

然后重写IChainDropDownService接口，公司对项目表设计 （1对多）

```java
/**
 * 区域级联下拉 实现类
 */
public class TestChainDropDownService implements IChainDropDownService{

    /**
     * 第一层，key=root,value=可选数组
     */
    @Override
    public List<String> getRoot(String... params){
        return Arrays.asList(new String[]{"中国", "美国"});
    }

    /**
     * 获取子类的Map
     */
    @Override
    public Map<String,List<String>> getParentBindSubMap(String... params){
        int level = Integer.parseInt(params[0]);
        // key 是父级，value 是父级的子类
        Map<String,List<String>> dataMap = new HashMap<>();
        if(level==2){
            dataMap.put("中国",Arrays.asList(new String[]{"北京2", "广东2"}));
            dataMap.put("美国",Arrays.asList(new String[]{"阿拉斯加州", "阿拉巴马州"}));
        }else if(level == 3){
            dataMap.put("北京2",Arrays.asList(new String[]{"北京市2"}));
            dataMap.put("广东2",Arrays.asList(new String[]{"广州2","深圳2"}));
            dataMap.put("阿拉斯加州",Arrays.asList(new String[]{"阿拉斯加","雅库塔特"}));
            dataMap.put("阿拉巴马州",Arrays.asList(new String[]{"马伦戈县"}));
        }else if(level == 4){
            dataMap.put("北京市2",Arrays.asList(new String[]{"朝阳区2","密云区2"}));
            dataMap.put("广州2",Arrays.asList(new String[]{"天河区2","白云区2"}));
            dataMap.put("深圳2",Arrays.asList(new String[]{"福田区2","南山区2"}));
            dataMap.put("阿拉斯加",Arrays.asList(new String[]{"瞎编区","编不下去了"}));
            dataMap.put("雅库塔特",Arrays.asList(new String[]{"瞎编区","编不下去了"}));
            dataMap.put("马伦戈县",Arrays.asList(new String[]{"马勒戈壁"}));
        }
        return dataMap;
    }
}

```

示例2 ：

```java
    @NotBlank(message = "单位名称不能为空")
    @ChainDropDownFields(isRoot = true, sourceClass = CPChainDropDownService.class, type = ChainDropDownType.COMPANY_PROJECT)
    private String CompanyName;


    @NotBlank(message = "项目名称不能为空")
    @ChainDropDownFields(sourceClass = CPChainDropDownService.class, type = ChainDropDownType.COMPANY_PROJECT, params = {"2"})
    private String projectName;
```

重写接口

```java
public class CPChainDropDownService implements IChainDropDownService {

    @Override
    public List<String> getRoot(String... params) {
        return SpringUtil.getBean(CompanyMapper.class).hasProjects();
    }

    @Override
    public Map<String, List<String>> getParentBindSubMap(String... params) {
        int level = Integer.parseInt(params[0]);
        HashMap<String, List<String>> map = new HashMap<>();
        if (level == 2) {
            List<String> companys = SpringUtil.getBean(CompanyMapper.class).hasProjects();
            for (String name : companys) {
                map.put(name, SpringUtil.getBean(CompanyProjectMapper.class).getProjectNamesByCName(name));
            }
        }
        return map;

    }
}

```

参考： https://rstyro.github.io/blog/2021/05/28/Easyexcel%E5%B8%B8%E7%94%A8%E7%A4%BA%E4%BE%8B%E4%BB%A3%E7%A0%81/

小tips： 导入Excel时发现，属性值一直为null。。。。结果发现lombok和easyexcel冲突，解决方案如下
![img.png](docs/imgs/img.png)

//后续看看要不要考虑，在注解上加分页条数，然后根据条数，动态sheet分页吧

### 3.2 dtp 意为动态线程池。基于nacos动态@refresh原理实现

用法如下 nacos新建dataId为dtp.yml，group为DEFAULT_GROUP，修改的需要搜索dtp，然后同步修改nacosListener等位置

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

util使用： TreeUtil 使用示例看代码

designMode 下 设计模式代码整理

    observer 观察者模式
    strategy 策略模式
    proxy 代理模式 

springboot集成hibernate-validator实现校验,自定义校验中无法注入spring bean 使用validator时，发现加了@compent，但是service无法注入
![img.png](docs/imgs/valid_1.png)

解决办法如下：

```java
package com.aprilz.tiny.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author Aprilz
 * @date 2023/3/13-14:37
 * @description 自定义校验config
 */
@Configuration
public class ValidatorConfig {

    /**
     * 快速返回校验器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = Validator.class)
    public Validator validator(AutowireCapableBeanFactory beanFactory) {
        //hibernate-validator 6.x没问题，7.x有问题
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(beanFactory))// 使用spring代理，
             //   .failFast(true) //不需要快速失败,需要则打开
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

}

```

excel中同上，Validators类中使用自定义validators

```java
package com.aprilz.excel.core;

import cn.hutool.extra.spring.SpringUtil;

import javax.validation.*;
import java.util.Set;

/**
 * 校验工具
 *
 * @author L.cm
 */
public final class Validators {

    private Validators() {
    }

    private static final Validator VALIDATOR;

    static {
        
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        VALIDATOR = factory.getValidator();
        //使用自定义validator
        VALIDATOR= SpringUtil.getBean("validator");
    }

    /**
     * Validates all constraints on {@code object}.
     *
     * @param object object to validate
     * @param <T>    the type of the object to validate
     * @return constraint violations or an empty set if none
     * @throws IllegalArgumentException if object is {@code null} or if {@code null} is
     *                                  passed to the varargs groups
     * @throws ValidationException      if a non recoverable error happens during the
     *                                  validation process
     */
    public static <T> Set<ConstraintViolation<T>> validate(T object) {
        return VALIDATOR.validate(object);
    }

}

```

再次尝试，发现已经可以注入bean了
![img.png](docs/imgs/valid_2.png)

原因分析 ConstraintValidatorFactory 默认实现类ConstraintValidatorFactoryImpl ，使用INSTANCE创建对象，不受spring管理
，所以这里改用SpringConstraintValidatorFactory即可

### 3.2 elasticsearch springboot2.7.5对应ES版本 7.17.6

### 杂记

全局脱敏 aprilz-spring-boot-starter-desensitize 这个后期应该要移到一个core包中，不需要用starter
基于jackson 实现了全局注解式脱敏

使用案例

```java
package com.aprilz.tiny.mbg.entity;

import com.aprilz.desensitize.core.annotations.Desensitize;
import com.aprilz.desensitize.core.enums.DesensitizeRuleEnums;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.aprilz.tiny.mbg.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
@Data
@TableName("ap_admin")
@ApiModel(value = "ApAdmin对象", description = "后台用户表")
public class ApAdmin extends BaseDO {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    @ApiModelProperty(value = "唯一标识")
    @ExcelIgnore
    private Long id;

    @ApiModelProperty("帐号启用状态：0->禁用；1->启用")
    @TableField("status")
    private Boolean status;

    @TableField("username")
    private String username;

    @TableField("password")
    @Desensitize(rule = DesensitizeRuleEnums.PASSWORD)
    private String password;

    @ApiModelProperty("手机")
    @TableField("mobile")
    @Desensitize(rule = DesensitizeRuleEnums.MOBILE_PHONE)
    private String mobile;

    @ApiModelProperty("性别：0->女；1->男 2-未知")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("邮箱")
    @TableField("email")
    @Desensitize(rule = DesensitizeRuleEnums.EMAIL)
    private String email;

    @ApiModelProperty("昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty("最后登录时间")
    @TableField("login_time")
    private Date loginTime;

    @ApiModelProperty("备注")
    @TableField("description")
    private String description;

}

```

调用http://localhost:8084//sso/user后,可看到字段已脱敏

```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "createBy": "ADMIN",
      "createTime": "2022-08-11 15:04:32",
      "updateBy": "ADMIN",
      "updateTime": "2022-08-11 15:04:39",
      "deleteFlag": false,
      "id": "1",
      "status": true,
      "username": "admin",
      "password": "************************************************************",
      "mobile": "176****0000",
      "sex": 2,
      "avatar": "https://thirdwx.qlogo.cn/mmopen/vi_32/2KOBFlndeR5aIzSMFAzfQewiawkmT6LnZpiaf5DAKWAcTn0qaXCmI6wzP71qXHL55xAwqZLVvvs9j7wUYNlmmpiaw/132",
      "email": "l************@163.com",
      "nickName": "aprilz",
      "loginTime": null,
      "description": null
    }
  ]
}
```

### 使用CompletableFuture和自定义线程池加速接口响应。（空间换时间）

```java
 private static ThreadPoolExecutor executor=new ThreadPoolExecutor(10,10,1000,TimeUnit.MILLISECONDS,WORK_QUEUE,HANDLER);

public PageResult<RespVO>  test(PageReqVO pageVO){
        Page<PageReqVO> pages=MyBatisUtils.buildPage(pageVO);
        IPage<RespVO> mpPage=baseMapper.selectPage(pages,pageVO);
        if(mpPage.getTotal()==0){
        return pageResult;
        }
        PageResult<RespVO> pageResult=new PageResult(mpPage.getRecords(),mpPage.getTotal());
        List<CompletableFuture<Void>>completableFutures=new ArrayList<>();
        pageResult.getRows().forEach(res->{
        // 假设查看page分页下的内容,走并行
        CompletableFuture<Void> future=CompletableFuture.runAsync(()->{
        List<RespVO.DetailRespVO>details=childMapper.selectByCId(res.getId());
        res.setExLists(details);
        },executor);
        completableFutures.add(future);
        });
        //等待所有结果返回
        try {
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).get(2, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
        } catch (TimeoutException e) {
        throw new RuntimeException("请求超时");
        }
        return pageResult;
        }
```



### excel导出成Zip

```java
    @PostMapping("/excelZip")
    @ApiOperation("导出excel成zip")
    public void excelZip(@RequestBody List<Long> ids, HttpServletRequest request,
                         HttpServletResponse response) {
        takeStockService.excelZip(ids, request, response);
    }
    
    
```
//    service


```java
 public void downloadFiles(HttpServletRequest request, HttpServletResponse response, File file) {
        //File file = new File(filePath);
        //创建输出流
        OutputStream out = null;
        ZipOutputStream zos = null;
        try {
            out = response.getOutputStream();
            zos = new ZipOutputStream(out);
            compress(file, zos, file.getName()); //压缩文件方法
            //刷新流和关闭流,注意流的关闭顺序，否则压缩文件出来会损坏
            zos.flush();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (file.exists()) {
                removeDirectory(file);
            }
        }
    }


    private static void compress(File sourceFile, ZipOutputStream zos, String name) throws IOException {
        byte[] buf = new byte[1024];
        if (sourceFile.isFile()) { //判断是否为文件
            // 压缩单个文件，压缩后文件名为当前文件名
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) > 0) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else { //路径文件为文件夹，用递归的方法压缩文件夹下的文件
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 空文件夹的处理
            } else {
                // 递归压缩文件夹下的文件
                for (File file : listFiles) {
                    compress(file, zos, name + "/" + file.getName());
                }
            }
        }
    }


    //多sheet
    public void excelZip(List<Long> ids, HttpServletRequest request, HttpServletResponse response) {
        ApiAssert.isFalse(ResultEnum.CRUD_VALID_NOT.overrideMsg("请需要导出的数据！"), CollUtil.isEmpty(ids));
        String dirName = getPath("成本盘点");
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        TakeStockDO byId = this.getById(ids.get(0));
        ProjectDTO projectDTO = projectApi.getProjectInfoById(byId.getProjectId());
        String projectName = projectDTO.getProjectName();
        List<CompletableFuture<Void>> futures = ids.stream().map(id -> CompletableFuture.runAsync(() -> {
            TakeStockDO one = this.getById(id);
            LocalDate yearMonth = one.getYearMonth();
            //项目名称 + 年 + 月
            String fileName = dirName + "/" + projectName + yearMonth.getYear() + yearMonth.getMonthValue() + ExcelTypeEnum.XLSX.getValue();
            ExcelWriter excelWriter = EasyExcel.write(fileName).excelType(ExcelTypeEnum.XLSX).build();
            //成本合计
            List<TakeStockTotalDO> totalDOS = takeStockTotalService.lambdaQuery().eq(TakeStockTotalDO::getTakeStockId, id)
                    .orderByAsc(TakeStockTotalDO::getType)
                    .orderByAsc(TakeStockTotalDO::getCreateTime).list();
            List<TakeStockTotalExcelVO> totalExcelVOS = TakeStockTotalConvert.INSTANCE.convertExcelList(totalDOS);
            WriteSheet writeSheet = EasyExcel.writerSheet("成本合计").head(TakeStockTotalExcelVO.class)
                    .registerWriteHandler(BeanUtils.instantiateClass(AutoHeadColumnWidthStyleStrategy.class))
                    .registerConverter(new LongStringConverter())
                    .registerConverter(LocalDateStringConverter.INSTANCE)
                    .registerConverter(LocalDateTimeStringConverter.INSTANCE)
                    .registerConverter(new BigDecimalStringConverter())
                    .build();
            excelWriter.write(totalExcelVOS, writeSheet);

            //
            List<LabourDO> labourDOS = labourService.lambdaQuery().eq(LabourDO::getTakeStockId, id)
                    .orderByAsc(LabourDO::getType)
                    .orderByAsc(LabourDO::getCreateTime).list();
            List<LabourExcelVO> labourExcelVOS = LabourConvert.INSTANCE.convertExcelList(labourDOS);
            // 使用 Stream 获取所有 type=1 元素的索引
            List<Integer> indexs = IntStream.range(0, labourDOS.size())
                    .filter(i -> labourDOS.get(i).getType() == 1)
                    .boxed()
                    .collect(Collectors.toList());

            writeSheet = EasyExcel.writerSheet("费用1")
                    .registerWriteHandler(BeanUtils.instantiateClass(AutoHeadColumnWidthStyleStrategy.class))
                    .registerConverter(new LongStringConverter())
                    .registerConverter(LocalDateStringConverter.INSTANCE)
                    .registerConverter(LocalDateTimeStringConverter.INSTANCE)
                    .registerConverter(new BigDecimalStringConverter())
                    .registerWriteHandler(new CustomMergeStrategy(1, indexs, 0, 1))
                    .head(LabourExcelVO.class)
                    .build();
            excelWriter.write(labourExcelVOS, writeSheet);

            //
            List<MaterialsDO> materialsDOS = materialsService.lambdaQuery().eq(MaterialsDO::getTakeStockId, id)
                    .orderByAsc(MaterialsDO::getType)
                    .orderByAsc(MaterialsDO::getCreateTime).list();
            List<MaterialsExcelVO> materialsExcelVOS = MaterialsConvert.INSTANCE.convertExcelList(materialsDOS);
            materialsExcelVOS.forEach(tmp -> {
                if (StrUtil.isAllNotBlank(tmp.getResourceStandardName(), tmp.getResourceModelNoName())) {
                    tmp.setSpecificationsAndModels(Optional.ofNullable(tmp).map(MaterialsExcelVO::getResourceStandardName).orElse("") + "-" + Optional.ofNullable(tmp).map(MaterialsExcelVO::getResourceModelNoName).orElse(""));
                }
            });
            indexs = IntStream.range(0, materialsDOS.size())
                    .filter(i -> materialsDOS.get(i).getType() == 1)
                    .boxed()
                    .collect(Collectors.toList());
            writeSheet = EasyExcel.writerSheet("费用2")
                    .registerWriteHandler(BeanUtils.instantiateClass(AutoHeadColumnWidthStyleStrategy.class))
                    .registerConverter(new LongStringConverter())
                    .registerConverter(LocalDateStringConverter.INSTANCE)
                    .registerConverter(LocalDateTimeStringConverter.INSTANCE)
                    .registerConverter(new BigDecimalStringConverter())
                    .registerWriteHandler(new CustomMergeStrategy(2, indexs, 0, 3))
                    .head(MaterialsExcelVO.class)
                    .build();
            excelWriter.write(materialsExcelVOS, writeSheet);

            excelWriter.finish();

        }, excelThreadPool)).collect(Collectors.toList());
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        allFutures.thenRun(() -> {
            downloadFiles(request, response, dir);

        });
        allFutures.join();
    }


    private static void removeDirectory(File directory) {
        if (directory == null || !directory.exists()) {
            return;
        }

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    removeDirectory(file);
                }
            }
        }

        directory.delete();
    }

    private static String getPath(String s) {
        return System.getProperty("java.io.tmpdir") + "/" + s + "_" + System.currentTimeMillis();
    }

```


    
         .and(StrUtil.isNotBlank(reqVO.getKeyword()), wrapper-> {
                    wrapper.or().like(ADO::getDescription,reqVO.getKeyword())
                            .or().like(ADO::getName,reqVO.getKeyword());
                })


### sql定时生成excel并发送邮箱

```sql
DROP TABLE IF EXISTS `ap_excel_job`;
CREATE TABLE `ap_excel_job`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
    `card_no`     VARCHAR(64)          DEFAULT NULL COMMENT '卡密',
    `cron`        VARCHAR(64)          DEFAULT NULL COMMENT '执行计划',
    `execute_type`    INT         NOT NULL DEFAULT 0 COMMENT '执行类型 0按结束时间 1按执行次数',
    `end_time`    TIMESTAMP   DEFAULT NULL COMMENT '结束时间',
    `frequency`    INT         DEFAULT NULL COMMENT '执行次数(不包含手动执行)',
    `execute_sql`      LONGTEXT         NOT NULL COMMENT '执行sql',
    `excel_log`         LONGTEXT          DEFAULT NULL COMMENT '存放执行记录,json格式[{"time":xx,"path":xx}]',
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='exceljob表';
```



###  screw 使用

        <dependency>
            <groupId>cn.smallbun.screw</groupId>
            <artifactId>screw-core</artifactId>
            <version>1.0.5</version>
        </dependency>

    后参考ScrewTest


### 解决MapStruct转换时间默认时区UTC问题

@Mapper(uses = MapStructUtil.class)



#### 在事务成功提交后执行

@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
http://localhost:8084/test/testTransactionalEventListener