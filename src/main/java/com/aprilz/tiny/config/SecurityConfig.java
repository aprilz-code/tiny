package com.aprilz.tiny.config;

import com.aprilz.tiny.common.properties.IgnoredUrlsProperties;
import com.aprilz.tiny.component.mybatis.JwtAuthenticationTokenFilter;
import com.aprilz.tiny.component.mybatis.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;


/**
 * SpringSecurity的配置
 * Created by aprilz on 2018/4/26.
 */
@Configuration
@EnableWebSecurity // 添加 security 过滤器
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法级别的权限认证
public class SecurityConfig {

    /**
     * 忽略验权配置
     */
    @Resource
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                //禁止网页iframe
                .headers().frameOptions().disable()
                .and()
                .logout()
                .permitAll()
                .and()
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                .sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //允许跨域
                .and()
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeRequests()
//                .antMatchers("/sso/login", "/sso/register")// 对登录注册要允许匿名访问
//                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                .permitAll()
                //配置的url 不需要授权
                .antMatchers(ignoredUrlsProperties.getUrls().toArray(new String[0])).permitAll()
//                .antMatchers("/**")//测试时全部运行访问
//                .permitAll()
                .and().authorizeRequests()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .authenticated();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        httpSecurity.exceptionHandling()
                //全局拦截器影响会失效
                //    .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
        return httpSecurity.build();
    }

    //跨域
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许跨域访问的主机
        //    if (environment.acceptsProfiles(Profiles.of("dev"))) {
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        //     } else {
        //          configuration.setAllowedOrigins(Collections.singletonList("http://域名"));
        //     }
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.addExposedHeader(tokenHeader);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
