package com.aprilz.tiny.service;

import com.aprilz.tiny.mbg.entity.ApAdminEntity;
import com.aprilz.tiny.mbg.entity.ApPermissionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author aprilz
 * @since 2022-07-07
 */
public interface IApAdminService extends IService<ApAdminEntity> {

    /**
     * 根据用户名获取后台管理员
     */
    ApAdminEntity getAdminByUsername(String username);

    /**
     * 注册功能
     */
    ApAdminEntity register(ApAdminEntity apAdminParam);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<ApPermissionEntity> getPermissionList(Long adminId);

}
