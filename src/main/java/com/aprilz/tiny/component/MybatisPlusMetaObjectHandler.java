package com.aprilz.tiny.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @description: mybatisplus拦截器
 * @author: liushaohui
 * @since: 2022/7/13
 **/
@Slf4j
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //  log.info("start insert fill ....");
        String username = "ADMIN";
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        if (metaObject.hasGetter("createTime")) {
            this.strictInsertFill(metaObject, "createTime", () -> new Date(), Date.class);
        }
        if (metaObject.hasGetter("updateTime")) {
            this.strictUpdateFill(metaObject, "updateTime", () -> new Date(), Date.class);
        }
        if (metaObject.hasGetter("createBy")) {
            this.strictInsertFill(metaObject, "createBy", String.class, username);
        }
        if (metaObject.hasGetter("updateBy")) {
            this.strictUpdateFill(metaObject, "updateBy", String.class, username);
        }

        if (metaObject.hasGetter("status")) {
            //无值则写入
            if (Objects.isNull(metaObject.getValue("status"))) {
                this.setFieldValByName("status", true, metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //    log.info("start update fill ....");
        String username = "ADMIN";
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        if (metaObject.hasGetter("updateTime")) {
            this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
        }
        if (metaObject.hasGetter("updateBy")) {
            this.strictUpdateFill(metaObject, "updateBy", String.class, username);
        }
    }
}
