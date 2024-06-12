package com.aprilz.tiny.service.impl;

import com.aprilz.tiny.common.utils.JwtTokenUtil;
import com.aprilz.tiny.mapper.ApAdminMapper;
import com.aprilz.tiny.mapper.ApPermissionMapper;
import com.aprilz.tiny.model.ApAdmin;
import com.aprilz.tiny.model.ApPermission;
import com.aprilz.tiny.service.IApAdminService;
import com.aprilz.tiny.vo.Token;
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
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
@Slf4j
@Service
public class ApAdminServiceImpl extends ServiceImpl<ApAdminMapper, ApAdmin> implements IApAdminService {

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
    private ApPermissionMapper permissionMapper;

    @Override
    public ApAdmin register(ApAdmin apAdminParam) {
        ApAdmin apAdmin = new ApAdmin();
        BeanUtils.copyProperties(apAdminParam, apAdmin);
        apAdmin.setStatus(true);
        //查询是否有相同用户名的用户
        LambdaQueryWrapper<ApAdmin> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ApAdmin::getUsername, apAdminParam.getUsername());
        List<ApAdmin> apAdminList = adminMapper.selectList(queryWrapper);
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
            if (userDetails == null) {
                throw new BadCredentialsException("用户不存在");
            }
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Token tk = jwtTokenUtil.generateToken(userDetails);
            token = tk.getToken();
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public List<ApPermission> getPermissionList(Long adminId) {
        return permissionMapper.getPermissionList(adminId);
    }


}
