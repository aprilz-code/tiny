package com.aprilz.tiny.cron;

import com.aprilz.tiny.service.IApCodeService;
import com.aprilz.tiny.service.impl.ApCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @description: 定时任务
 * @author: liushaohui
 * @since: 2022/5/25
 **/
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {

    @Resource
    IApCodeService apCodeService;

    //3.添加定时任务
    @Scheduled(cron = "0 0 1 * * ?")
    //每天凌晨一点执行
    private void configureTasks() {
        apCodeService.newCode();
        System.out.println("执行发送邮件定时任务时间: " + LocalDateTime.now());
    }
}
