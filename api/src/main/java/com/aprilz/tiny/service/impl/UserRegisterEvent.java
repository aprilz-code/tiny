package com.aprilz.tiny.service.impl;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @author Aprilz
 * @date 2024/11/30-17:11
 * @description TODO
 */
@Data
public class UserRegisterEvent extends ApplicationEvent {

    private Long id;

    public UserRegisterEvent(Long id) {
        super(id);
        this.id = id;
    }
}
