package com.aprilz.tiny.service;

import com.aprilz.tiny.entity.ApCode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aprilz
 * @since 2022-05-25
 */
public interface IApCodeService extends IService<ApCode> {

    boolean verification(String token);

    /**
     * @author liushaohui
     * @description 生成新的二维码
     * @since  2022/5/25
      * @param
     * @return
     **/
    boolean newCode();
}
