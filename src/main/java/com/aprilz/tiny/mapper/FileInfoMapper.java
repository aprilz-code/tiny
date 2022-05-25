package com.aprilz.tiny.mapper;

import com.aprilz.tiny.entity.FileInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lsh
 * @since 2022-05-25
 */
public interface FileInfoMapper extends BaseMapper<FileInfo> {

    Integer getCount();

}
