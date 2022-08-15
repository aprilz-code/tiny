package com.aprilz.tiny.service.impl;

import com.aprilz.tiny.mapper.ApStorageMapper;
import com.aprilz.tiny.mbg.entity.ApStorage;
import com.aprilz.tiny.service.IApStorageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件存储表 服务实现类
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
@Service
public class ApStorageServiceImpl extends ServiceImpl<ApStorageMapper, ApStorage> implements IApStorageService {

    @Override
    public ApStorage findByKey(String key) {
        return this.lambdaQuery().eq(ApStorage::getKey, key).eq(ApStorage::getDeleteFlag, false).one();

    }
}
