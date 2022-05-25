package com.aprilz.tiny;

import com.aprilz.tiny.entity.FileInfo;
import com.aprilz.tiny.mapper.FileInfoMapper;
import com.aprilz.tiny.service.IFileInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MallTinyApplicationTests {

    @Resource
    IFileInfoService fileInfoService;


    @Resource
    FileInfoMapper fileInfoMapper;

    @Test
    public void contextLoads() {
        FileInfo byId = fileInfoService.getById(1);
        System.out.println(byId);
        Integer count = fileInfoMapper.getCount();
        System.out.println(count);
    }

}
