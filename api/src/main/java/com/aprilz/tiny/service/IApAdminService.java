package com.aprilz.tiny.service;

import com.aprilz.tiny.model.ApAdmin;
import com.aprilz.tiny.model.ApPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
public interface IApAdminService extends IService<ApAdmin> {


    ApAdmin register(ApAdmin apAdminParam);

    String login(String username, String password);

    List<ApPermission> getPermissionList(Long adminId);
}
