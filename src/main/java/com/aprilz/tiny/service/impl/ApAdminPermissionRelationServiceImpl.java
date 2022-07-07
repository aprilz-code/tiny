package com.aprilz.tiny.service.impl;

import com.aprilz.tiny.mbg.entity.ApAdminPermissionRelationEntity;
import com.aprilz.tiny.mapper.ApAdminPermissionRelationMapper;
import com.aprilz.tiny.service.IApAdminPermissionRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限) 服务实现类
 * </p>
 *
 * @author aprilz
 * @since 2022-07-07
 */
@Service
public class ApAdminPermissionRelationServiceImpl extends ServiceImpl<ApAdminPermissionRelationMapper, ApAdminPermissionRelationEntity> implements IApAdminPermissionRelationService {

}
