//package com.aprilz.tiny.config.provider;
//
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//@Service
//public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
//
//    private List<CustomUserDetailService> userDetailsServices;
//
//    public List<CustomUserDetailService> getUserDetailsServices() {
//        return userDetailsServices;
//    }
//
//    public void setUserDetailsServices(List<CustomUserDetailService> userDetailsServices) {
//        this.userDetailsServices = userDetailsServices;
//    }
//
//    /**
//     * 进行身份认证的逻辑
//     *
//     * @param authentication 就是我们传入的Token
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//        //利用UserDetailsService获取用户信息，拿到用户信息后重新组装一个已认证的Authentication
//        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
//
//        //在Filter中获取的请求中的所有参数，在此处拿出
//        Map detail = (Map) authentication.getDetails();
//        UserDetails loadedUser = null;
//        //枚举所有自定义的userDetailsService
//        for (CustomUserDetailService userDetailsService : userDetailsServices) {
//            //在请求中获取平台参数
//            Object platform = detail.get("domainType");
//            Object sysCode = detail.get("sysCode");
//            if (Objects.nonNull(platform) && Objects.nonNull(sysCode)) {
//                platform = platform.toString() + sysCode.toString();
//            }
//            //如果不为null则与userDetailsService匹配，配对成功则使用该userDetailsService的loadUserByUsername
//            if (Objects.nonNull(platform) && userDetailsService.supports(platform.toString())) {
//                loadedUser = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
//                break;
//            }
//        }
//        if (loadedUser == null) {
//            throw new InternalAuthenticationServiceException("无法获取用户信息");
//        }
//        UsernamePasswordAuthenticationToken authenticationResult = new UsernamePasswordAuthenticationToken(loadedUser, loadedUser.getAuthorities());
//        authenticationResult.setDetails(authenticationToken.getDetails());
//        return authenticationResult;
//    }
//
//    /**
//     * AuthenticationManager挑选一个AuthenticationProvider来处理传入进来的Token就是根据supports方法来判断的
//     *
//     * @param aClass
//     * @return
//     */
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);   //判断出入进来的是不是SmsCodeAuthenticationToken类型
//    }
//}
