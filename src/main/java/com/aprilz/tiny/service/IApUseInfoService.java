package com.aprilz.tiny.service;

import com.aprilz.tiny.entity.ApUseInfo;
import com.aprilz.tiny.param.ApUseInfoParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aprilz
 * @since 2022-05-25
 */
public interface IApUseInfoService extends IService<ApUseInfo> {

    void doIt(ApUseInfoParam param, MultipartFile front, MultipartFile behind);
}
