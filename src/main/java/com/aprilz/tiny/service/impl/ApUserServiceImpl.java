package com.aprilz.tiny.service.impl;

import com.aprilz.tiny.mapper.ApUserMapper;
import com.aprilz.tiny.mbg.entity.ApUser;
import com.aprilz.tiny.service.IApUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements IApUserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testTs() {
        //随意插入一个人员
        ApUser apUser = new ApUser().setNickname("bob");
        save(apUser);

        //不需要受事务回滚 ，比如发短信，mq等

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            //suspend：在spring开启新事务，获取connection之前会调用（未执行registCustomer）
            //resume：开启新事务失败时会调用（未执行registCustomer）
            //flush：没调用
            //beforeCommit：事务提交前会调用（已执行registCustomer）
            //beforeCompletion：事务提交前会调用，在beforeCommit之后（已执行registCustomer）
            //afterCommit：事务提交后会调用（已执行registCustomer）
            //afterCompletion：事务提交后会调用，在afterCommit之后（已执行registCustomer）
            //   事务异常状态 TransactionSynchronization.STATUS_UNKNOWN
            @Override
            public void afterCompletion(int status) {
                switch (status) {
                    case TransactionSynchronization.STATUS_COMMITTED:
                        // 外部方法事务提交后执行的业务逻辑
                        break;
                    case TransactionSynchronization.STATUS_ROLLED_BACK:
                        // 外部方法事务回滚后执行的业务逻辑
                        break;
                    case TransactionSynchronization.STATUS_UNKNOWN:
                        // 外部方法事务异常时执行的业务逻辑
                        break;
                    default:
                        break;
                }

            }
        });

    }
}
