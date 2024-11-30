package com.aprilz.tiny.service.impl;

import com.aprilz.tiny.service.IApStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author Aprilz
 * @date 2024/11/30-17:10
 * @description TODO
 */
@Slf4j
@Component
public class UserListener {

    @Autowired
    private IApStorageService apStorageService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = UserRegisterEvent.class)
    public void onUserRegisterEvent(UserRegisterEvent event) {
        apStorageService.search(event.getId());
    }

}
