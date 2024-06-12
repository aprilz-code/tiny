package com.aprilz.tiny.common.utils;

import com.aprilz.tiny.dto.AdminUserDetails;
import com.aprilz.tiny.model.ApAdmin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @description: 人员util
 * @author: aprilz
 * @since: 2022/7/15
 **/
public class SecurityUtils {

    public static ApAdmin getUser() {
        try {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            //基于认证对象获取用户身份信息
            AdminUserDetails principal = (AdminUserDetails) authentication.getPrincipal();
            return principal.getApAdmin();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getUserName() {
        try {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            //基于认证对象获取用户身份信息
            AdminUserDetails principal = (AdminUserDetails) authentication.getPrincipal();
            return Optional.ofNullable(principal.getApAdmin()).map(ApAdmin::getUsername).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
