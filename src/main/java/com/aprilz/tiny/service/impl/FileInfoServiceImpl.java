package com.aprilz.tiny.service.impl;

import com.aprilz.tiny.entity.FileInfo;
import com.aprilz.tiny.mapper.FileInfoMapper;
import com.aprilz.tiny.service.IFileInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lsh
 * @since 2022-05-25
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {

}
