package com.aprilz.tiny.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.aprilz.tiny.common.constrant.Const;
import com.aprilz.tiny.entity.ApCode;
import com.aprilz.tiny.mapper.ApCodeMapper;
import com.aprilz.tiny.service.IApCodeService;
import com.aprilz.tiny.utils.IpUtils;
import com.aprilz.tiny.utils.MailUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

    @Override
    public boolean newCode() {
        QueryWrapper qw;
        try {
            qw = new QueryWrapper();
            this.remove(qw);
            String s = RandomUtil.randomString(6);
            String sec = DigestUtil.md5Hex(Const.SALE + s);
            ApCode apCode = new ApCode();
            apCode.setToken(sec);
            this.save(apCode);

            //二维码 生成base64
            QrConfig qrConfig = new QrConfig();
            qrConfig.setWidth(300);
            qrConfig.setHeight(300);
            String imgStr = QrCodeUtil.generateAsBase64("http://" + IpUtils.getOutIp() + ":11000?token=" + s, qrConfig, ImgUtil.IMAGE_TYPE_PNG);
            HashMap<String, String> map = MapUtil.newHashMap();
            map.put("img", imgStr);
            MailUtils.sendMail(CollUtil.newArrayList("1870103727@qq.com,ylzcdh@163.com"), "二维码", map);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
