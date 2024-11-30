package com.aprilz.tiny.service;

import com.aprilz.tiny.model.ApStorage;
import com.aprilz.tiny.model.ApUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文件存储表 服务类
 * </p>
 *
 * @author aprilz
 * @since 2022-07-20
 */
public interface IApStorageService extends IService<ApStorage> {

    ApStorage findByKey(String key);

    ApUser search(long id);
}
