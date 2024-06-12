package com.aprilz.tiny.mapper;

import com.aprilz.tiny.model.ApPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户权限表 Mapper 接口
 * </p>
 *
 * @author aprilz
 * @since 2022-08-11
 */
public interface ApPermissionMapper extends BaseMapper<ApPermission> {

    List<ApPermission> getPermissionList(@Param("adminId") Long adminId);
}
