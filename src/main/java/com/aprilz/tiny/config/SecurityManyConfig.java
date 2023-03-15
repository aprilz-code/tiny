//package com.aprilz.tiny.config;
//
//import com.aprilz.tiny.component.JwtAuthenticationTokenFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * spring security配置
// *
// * @author aprilz  实例配置多策略登录方式
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//public class SecurityConfig {
//
//
//    @Autowired
//    private SmsCodeAuthenticationProvider smsCodeAuthenticationProvider;
//    /**
//     * 注入我们自定义的系统管理的UserDetailService
//     */
//    @Autowired
//    private AdminUserDetailServiceImpl adminUserDetailService;
//
//    /**
//     * 注入我们自定义的学校PC的UserDetailService
//     */
//    @Autowired
//    private SchoolUserDetailServiceImpl schoolUserDetailService;
//
//    /**
//     * 注入我们自定义的学校小程序的UserDetailService
//     */
//    @Autowired
//    private SchoolMiniAppUserDetailServiceImpl schoolMiniAppUserDetailService;
//
//    /**
//     * //注入我们自定义的企业PC的UserDetailService
//     */
//    @Autowired
//    private CompanyUserDetailServiceImpl companyUserDetailService;
//    /**
//     * //注入我们自定义的企业小程序的UserDetailService
//     */
//    @Autowired
//    private CompanyMiniAppUserDetailServiceImpl companyMiniAppUserDetailService;
//
//    /**
//     * 认证失败处理类
//     */
//    @Autowired
//    private AuthenticationEntryPointImpl unauthorizedHandler;
//
//    /**
//     * 退出处理类
//     */
//    @Autowired
//    private LogoutSuccessHandlerImpl logoutSuccessHandler;
//
//    /**
//     * token认证过滤器
//     */
//    @Autowired
//    private JwtAuthenticationTokenFilter authenticationTokenFilter;
//
//    /**
//     * 资源配置
//     */
//    @Autowired
//    private ResourcesConfig resourcesConfig;
//
//
//    /**
//     * anyRequest          |   匹配所有请求路径
//     * access              |   SpringEl表达式结果为true时可以访问
//     * anonymous           |   匿名可以访问
//     * denyAll             |   用户不能访问
//     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
//     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
//     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
//     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
//     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
//     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
//     * permitAll           |   用户可以任意访问
//     * rememberMe          |   允许通过remember-me登录的用户访问
//     * authenticated       |   用户登录后可访问
//     */
//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        List<CustomUserDetailService> userDetailServices = new ArrayList<>();
//        userDetailServices.add(adminUserDetailService);
//        userDetailServices.add(schoolUserDetailService);
//        userDetailServices.add(companyUserDetailService);
//        userDetailServices.add(companyMiniAppUserDetailService);
//        userDetailServices.add(schoolMiniAppUserDetailService);
//        smsCodeAuthenticationProvider.setUserDetailsServices(userDetailServices);
//
//        httpSecurity
//                // CSRF禁用，因为不使用session
//                .csrf().disable()
//                // 认证失败处理类
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                // 基于token，所以不需要session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                // 过滤请求
//                .authorizeRequests()
//                // 对于登录login 注册register 验证码captchaImage 允许匿名访问
//                .antMatchers("/login", "/smsLogin", "/common/imgVerification/*", "/common/validateSms/*", "/captchaImage", "/system/user/forgetPassword").permitAll()
//                //开放API相关
//                .antMatchers("/**/open-api/**").permitAll()
//                .antMatchers("/actuator/**").permitAll()
//                .antMatchers("/**").permitAll()
//                .antMatchers(
//                        HttpMethod.GET,
//                        "/",
//                        "/*.html",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js",
//                        "/profile/**"
//                ).permitAll()
//                .antMatchers("/swagger-ui.html").anonymous()
//                .antMatchers("/swagger-resources/**").anonymous()
//                .antMatchers("/webjars/**").anonymous()
//                .antMatchers("/*/api-docs").anonymous()
//                .antMatchers("/druid/**").anonymous()
//                .antMatchers("/websocket/**").anonymous()
//                .antMatchers("/tests/**").anonymous()
//
//                // 除上面外的所有请求全部需要鉴权认证
//                .anyRequest().authenticated()
//                .and()
//                .headers().frameOptions().disable();
//
//
//        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
//        // 添加JWT filter
//        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//        //自定义
//        httpSecurity.authenticationProvider(smsCodeAuthenticationProvider);
//
//        // 添加CORS filter
////        CorsFilter corsFilter = resourcesConfig.getCorsFilter();
////        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
////        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
//        return httpSecurity.build();
//    }
//
//    /**
//     * 强散列哈希加密实现
//     */
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    /**
////     * 身份认证接口
////     */
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        //创建一个自定义的AbstractUserDetailsAuthenticationProvider对象
////        MyAuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider();
////
////        List<CustomUserDetailService> userDetailServices = new ArrayList<>();
////        userDetailServices.add(adminUserDetailService);
////        userDetailServices.add(schoolUserDetailService);
////        userDetailServices.add(companyUserDetailService);
////        userDetailServices.add(companyMiniAppUserDetailService);
////        userDetailServices.add(schoolMiniAppUserDetailService);
////
////        myAuthenticationProvider.setUserDetailsServices(userDetailServices);
////        myAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
////        //配置我们自定义的AbstractUserDetailsAuthenticationProvider
////        auth.authenticationProvider(myAuthenticationProvider);
////
////    /*    auth.authenticationProvider(myAuthenticationProvider);
////        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
////
////        //配置我们自定义的AbstractUserDetailsAuthenticationProvider
////        auth.authenticationProvider(myAuthenticationProvider);*/
////
////    }
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//}
