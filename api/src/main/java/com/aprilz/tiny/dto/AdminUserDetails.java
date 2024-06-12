package com.aprilz.tiny.dto;

import com.aprilz.tiny.model.ApAdmin;
import com.aprilz.tiny.model.ApPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 * Created by aprilz on 2018/4/26.
 */
public class AdminUserDetails implements UserDetails {
    private ApAdmin apAdmin;
    private List<ApPermission> permissionList;

    public AdminUserDetails(ApAdmin apAdmin, List<ApPermission> permissionList) {
        this.apAdmin = apAdmin;
        this.permissionList = permissionList;
    }

    public ApAdmin getApAdmin() {
        return apAdmin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return permissionList.stream()
                .filter(permission -> permission.getValue() != null)
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return apAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return apAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return apAdmin.getStatus();
    }
}
