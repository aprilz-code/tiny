package com.aprilz.tiny.service;

import com.aprilz.tiny.mbg.entity.ApExcelTest;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * excel-test表 服务类
 * </p>
 *
 * @author aprilz
 * @since 2023-02-22
 */
public interface IApExcelTestService extends IService<ApExcelTest> {

    void upload3(MultipartFile file) throws IOException;
}
