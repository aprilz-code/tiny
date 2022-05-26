package com.aprilz.tiny.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.extra.mail.MailUtil;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @description: TODO
 * @author: liushaohui
 * @since: 2022/5/25
 **/
@Slf4j
@Component
public class MailUtils {




    private final static String PATH = "static\\mail\\mailTemplate.html";

    /**
     * @param to,subject,params
     * @return void
     * @author liushaohui
     * @description to ,subject标题  ，  params模板中替换字符
     * @since 2022/5/25
     **/
    public static void sendMail(ArrayList to, String subject, Map<String, String> params) {
        ClassPathResource resource = new ClassPathResource(PATH);
        String result = null;
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            result = IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException("读取模版文件失败");
        } finally {
            IoUtil.close(inputStream);
        }
        Set<String> stringSet = params.keySet();
        Iterator<String> iterator = stringSet.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            result = result.replace("${" + key + "}", params.get(key));
        }
        MailUtil.send(to, subject, result, true);
        log.info("邮件发送成功");
    }
}
