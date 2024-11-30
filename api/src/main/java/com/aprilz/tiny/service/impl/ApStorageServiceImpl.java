package com.aprilz.tiny.service.impl;

import com.aprilz.tiny.mapper.ApStorageMapper;
import com.aprilz.tiny.mapper.ApUserMapper;
import com.aprilz.tiny.model.ApStorage;
import com.aprilz.tiny.model.ApUser;
import com.aprilz.tiny.service.IApStorageService;
import com.aprilz.tiny.service.IApUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;
import java.util.Objects;

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

    @Resource
    private ApUserMapper apiUserMapper;
    @Override
    public ApStorage findByKey(String key) {
        return this.lambdaQuery().eq(ApStorage::getKey, key).eq(ApStorage::getDeleteFlag, false).one();

    }

    @Override

    public ApUser search(long id) {
        ApUser byId = apiUserMapper.selectById(id);
        if(Objects.isNull(byId)){
            throw new RuntimeException("未查询到");
        }
        return byId;
    }
}
