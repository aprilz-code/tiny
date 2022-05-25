package com.aprilz.tiny.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aprilz.tiny.entity.ApUseInfo;
import com.aprilz.tiny.mapper.ApUseInfoMapper;
import com.aprilz.tiny.param.ApUseInfoParam;
import com.aprilz.tiny.service.IApUseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aprilz
 * @since 2022-05-25
 */
@Service
public class ApUseInfoServiceImpl extends ServiceImpl<ApUseInfoMapper, ApUseInfo> implements IApUseInfoService {

    @Override
    public void doIt(ApUseInfoParam param, MultipartFile front, MultipartFile behind) {
        //上传图片，写入表


        ApUseInfo apUseInfo = new ApUseInfo();
        BeanUtil.copyProperties(param, apUseInfo);
        this.save(apUseInfo);
    }
}
