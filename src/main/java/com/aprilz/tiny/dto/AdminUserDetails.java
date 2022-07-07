package com.aprilz.tiny.dto;

import com.aprilz.tiny.mbg.entity.ApAdminEntity;
import com.aprilz.tiny.mbg.entity.ApPermissionEntity;
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
    private ApAdminEntity apAdminEntity;
    private List<ApPermissionEntity> permissionList;

    public AdminUserDetails(ApAdminEntity apAdminEntity, List<ApPermissionEntity> permissionList) {
        this.apAdminEntity = apAdminEntity;
        this.permissionList = permissionList;
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
        return apAdminEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return apAdminEntity.getUsername();
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
        return apAdminEntity.getStatus() == 1;
    }
}
