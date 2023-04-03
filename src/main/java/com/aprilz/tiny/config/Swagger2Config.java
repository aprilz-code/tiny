package com.aprilz.tiny.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

/**
 * Swagger2API文档的配置
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2Config {

    @Bean
    public SpringFoxHandlerProviderBeanPostProcessor springFoxHandlerProviderBeanPostProcessor() {
        return new SpringFoxHandlerProviderBeanPostProcessor();
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包下controller生成API文档
                // .apis(RequestHandlerSelectors.basePackage("com.aprilz.tiny"))
                .apis(basePackage("com.aprilz.tiny.controller"))
                .paths(PathSelectors.any())
                .build()
                //添加登录认证
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

//    @Bean
//    public Docket memberRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("会员")
//                .apiInfo(apiInfo()).select()
//                //扫描所有有注解的api，用这种方式更灵活
//                .apis(RequestHandlerSelectors.basePackage("com.aprilz.tiny.controller.member"))
//                .paths(PathSelectors.any())
//                .build()
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts());
//    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("tiny API接口文档")
                .description("tiny Api Documentation")
                .contact(new Contact("aprilz", "", "liushaohui777@163.com"))
                .version("1.0")
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        //设置请求头信息
        return Collections.singletonList(new ApiKey(HttpHeaders.AUTHORIZATION, "X-AP-Token", "header"));
    }

    private static List<SecurityReference> securityReferences() {
        return Collections.singletonList(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes()));
    }

    private static AuthorizationScope[] authorizationScopes() {
        return new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")};
    }

    /**
     * 安全上下文
     *
     * @see #securitySchemes()
     * @see #authorizationScopes()
     */
    private static List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(securityReferences())
                // 通过 PathSelectors.regex("^(?!auth).*$")，排除包含 "auth" 的接口不需要使用securitySchemes
                .operationSelector(o -> o.requestMappingPattern().matches("^(?!auth).*$"))
                .build());
    }
}
