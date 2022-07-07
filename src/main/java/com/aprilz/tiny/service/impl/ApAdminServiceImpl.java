package com.aprilz.tiny.service.impl;

import com.aprilz.tiny.common.utils.JwtTokenUtil;
import com.aprilz.tiny.mapper.ApAdminMapper;
import com.aprilz.tiny.mapper.ApPermissionMapper;
import com.aprilz.tiny.mbg.entity.ApAdminEntity;
import com.aprilz.tiny.mbg.entity.ApPermissionEntity;
import com.aprilz.tiny.service.IApAdminService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author aprilz
 * @since 2022-07-07
 */
@Slf4j
@Service
public class ApAdminServiceImpl extends ServiceImpl<ApAdminMapper, ApAdminEntity> implements IApAdminService {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    private ApAdminMapper adminMapper;
    @Resource
    private ApPermissionMapper apPermissionMapper;

    @Override
    public ApAdminEntity getAdminByUsername(String username) {
        LambdaQueryWrapper<ApAdminEntity> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ApAdminEntity::getUsername, username);
        List<ApAdminEntity> adminList = adminMapper.selectList(queryWrapper);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    @Override
    public ApAdminEntity register(ApAdminEntity apAdminParam) {
        ApAdminEntity apAdmin = new ApAdminEntity();
        BeanUtils.copyProperties(apAdminParam, apAdmin);
        apAdmin.setCreateTime(new Date());
        apAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        LambdaQueryWrapper<ApAdminEntity> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ApAdminEntity::getUsername, apAdminParam.getUsername());
        List<ApAdminEntity> apAdminList = adminMapper.selectList(queryWrapper);
        if (apAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(apAdmin.getPassword());
        apAdmin.setPassword(encodePassword);
        adminMapper.insert(apAdmin);
        return apAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }


    @Override
    public List<ApPermissionEntity> getPermissionList(Long adminId) {
        return apPermissionMapper.getPermissionList(adminId);
    }
}
