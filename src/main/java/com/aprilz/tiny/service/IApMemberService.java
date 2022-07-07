package com.aprilz.tiny.service;


import com.aprilz.tiny.common.api.CommonResult;

/**
 * 会员管理Service
 * Created by aprilz on 2018/8/3.
 */
public interface IApMemberService {

    /**
     * 生成验证码
     */
    CommonResult generateAuthCode(String telephone);

    /**
     * 判断验证码和手机号码是否匹配
     */
    CommonResult verifyAuthCode(String telephone, String authCode);

}
