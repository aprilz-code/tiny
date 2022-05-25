package com.aprilz.tiny.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aprilz.tiny.entity.ApUseInfo;
import com.aprilz.tiny.mapper.ApUseInfoMapper;
import com.aprilz.tiny.param.ApUseInfoParam;
import com.aprilz.tiny.service.IApUseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aprilz
 * @since 2022-05-25
 */
@Service
public class ApUseInfoServiceImpl extends ServiceImpl<ApUseInfoMapper, ApUseInfo> implements IApUseInfoService {

    @Value("${filePath}")
    private String filePath;

    @Override
    public void doIt(ApUseInfoParam param, MultipartFile front, MultipartFile behind) {
        //上传图片，写入表
        String username = param.getUsername();
        String todayStr = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String path = filePath + "/" + todayStr + "/" + username;
        ApUseInfo apUseInfo = new ApUseInfo();
        BeanUtil.copyProperties(param, apUseInfo);
        apUseInfo.setFront(uploadFile(path, front));
        apUseInfo.setBehind(uploadFile(path, behind));
        this.save(apUseInfo);

    }

    private static String uploadFile(String path, MultipartFile multipartFile) {
        path = path + "/" + multipartFile.getOriginalFilename();
        File file = new File(path);
        createFile(file);
        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    /**
     * 判断文件是否存在，不存在就创建
     *
     * @param file
     */
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
