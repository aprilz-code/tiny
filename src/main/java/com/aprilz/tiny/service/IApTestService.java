package com.aprilz.tiny.service;

import com.aprilz.tiny.mbg.entity.ApExcelTest;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * excel-test表 服务类
 * </p>
 *
 * @author aprilz
 * @since 2023-02-22
 */
public interface IApTestService extends IService<ApExcelTest> {


    Boolean testTrans();

    Boolean testTrans2();
}
