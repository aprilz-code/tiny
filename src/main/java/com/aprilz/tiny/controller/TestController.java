package com.aprilz.tiny.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @description: 测试控制器
 * @author: liushaohui
 * @since: 2022/5/25
 **/
@Controller
@Slf4j
public class TestController {



    @GetMapping("/test")
    public String getInsertUser(){
        return "test";
    }


//    @Value("${filePath}")
//    private String filePath;
//    @PostMapping("/test")
//    public String getInsertUser(@RequestParam("front") MultipartFile front){
//        String todayStr = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        String path = filePath + "/" + todayStr + "/zb" ;
//        path = path + "/" + front.getOriginalFilename();
//        String s = uploadFile(path, front);
//        log.error("--------------------" + s);
//        return "test";
//    }




    private static String uploadFile(String path, MultipartFile multipartFile){
        File file = new File(path);
        createFile(file);
        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    private static void createFile(File file) {
        if (file.exists()) {
            System.out.println("File exists");
        } else {
            System.out.println("File not exists, create it ...");
            //getParentFile() 获取上级目录(包含文件名时无法直接创建目录的)
            if (!file.getParentFile().exists()) {
                System.out.println("not exists");
                //创建上级目录
                file.getParentFile().mkdirs();
            }
//            try {
//               // 在上级目录里创建文件
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

}
