package com.aprilz.tiny.service.impl;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.Objects;

import com.aprilz.tiny.mapper.ApUserMapper;
import com.aprilz.tiny.model.ApUser;
import com.aprilz.tiny.service.IApStorageService;
import com.aprilz.tiny.service.IApUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
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


    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testTs() {
        //随意插入一个人员
        ApUser apUser = new ApUser();
        apUser.setNickname("bob");
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

    @Override
    @Transactional(rollbackFor = Exception.class )
    public void testTransactionalEventListener() {
        eventPublisher.publishEvent(new UserRegisterEvent(1L));
        eventPublisher.publishEvent(new UserRegisterEvent(2L));
        ApUser apUser = new ApUser();
        apUser.setId(1L);
        apUser.setUsername("bob");
        apUser.setPassword("2323");
        apUser.setGender(0);
        apUser.setBirthday(new Date());
        apUser.setLastLoginTime(new Date());
        apUser.setLastLoginIp("");
        apUser.setUserLevel(0);
        apUser.setNickname("bob");
        apUser.setMobile("23");
        apUser.setAvatar("23");
        apUser.setWxOpenid("23");
        apUser.setSessionKey("23");
        apUser.setStatus(0);
        save(apUser);

        ApUser apUser2 = new ApUser();
        apUser2.setId(2L);
        apUser2.setUsername("bob222");
        apUser2.setPassword("2323");
        apUser2.setGender(0);
        apUser2.setBirthday(new Date());
        apUser2.setLastLoginTime(new Date());
        apUser2.setLastLoginIp("");
        apUser2.setUserLevel(0);
        apUser2.setNickname("bob222");
        apUser2.setMobile("23");
        apUser2.setAvatar("23");
        apUser2.setWxOpenid("23");
        apUser2.setSessionKey("23");
        apUser2.setStatus(0);
        save(apUser2);

       // throw new RuntimeException("抛出异常");
//        apUser.setUsername("bobbbbbbb");
//        updateById(apUser);
    }

    @Async
    //@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
     ApUser search( Long id){
        ApUser byId = getById(id);
        if(Objects.isNull(byId)){
            throw new RuntimeException("未查询到");
        }
        return byId;
    }
}
