package com.aprilz.tiny.mapper;

import com.aprilz.tiny.mbg.entity.ApPermissionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户权限表 Mapper 接口
 * </p>
 *
 * @author aprilz
 * @since 2022-07-07
 */
public interface ApPermissionMapper extends BaseMapper<ApPermissionEntity> {


    /**
     * 获取用户所有权限(包括+-权限)
     */
    List<ApPermissionEntity> getPermissionList(@Param("adminId") Long adminId);

}
