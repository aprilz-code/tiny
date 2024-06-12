package com.aprilz.tiny.service.login;

import com.aprilz.tiny.dto.AdminUserDetails;
import com.aprilz.tiny.mapper.ApAdminMapper;
import com.aprilz.tiny.mapper.ApPermissionMapper;
import com.aprilz.tiny.model.ApAdmin;
import com.aprilz.tiny.model.ApPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Aprilz
 * @date 2023/3/10-10:51
 * @description 登录实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private ApAdminMapper adminMapper;

    @Resource
    private ApPermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<ApAdmin> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ApAdmin::getUsername, username).or().eq(ApAdmin::getMobile, username).last("limit 1");
        ApAdmin admin = adminMapper.selectOne(queryWrapper);
        if (admin == null) {
            return null;
        }
        List<ApPermission> permissionList = this.getPermissionList(admin.getId());
        return new AdminUserDetails(admin, permissionList);
    }

    public List<ApPermission> getPermissionList(Long adminId) {
        return permissionMapper.getPermissionList(adminId);
    }
}
