package com.aprilz.tiny.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.aprilz.tiny.common.constrant.Const;
import com.aprilz.tiny.entity.ApCode;
import com.aprilz.tiny.mapper.ApCodeMapper;
import com.aprilz.tiny.service.IApCodeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aprilz
 * @since 2022-05-25
 */
@Service
public class ApCodeServiceImpl extends ServiceImpl<ApCodeMapper, ApCode> implements IApCodeService {

//54661
    @Override
    public boolean verification(String token) {
        LambdaQueryWrapper<ApCode> qw = new LambdaQueryWrapper<ApCode>();
        String sec = DigestUtil.md5Hex(Const.SALE + token);
        qw.eq(ApCode::getToken, sec);
        int count = this.count(qw);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
