package com.aprilz.tiny;


import com.aprilz.tiny.common.api.PageVO;
import com.aprilz.tiny.common.utils.PageUtil;
import com.aprilz.tiny.mapper.ApAdminMapper;
import com.aprilz.tiny.mbg.entity.ApAdminEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class TinyApplicationTests {

    @Resource
    private ApAdminMapper apAdminMapper;

    @Test
    public void contextLoads() {
        LambdaQueryWrapper<ApAdminEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApAdminEntity::getId, 1);
        Page<ApAdminEntity> apAdminEntityPage = apAdminMapper.selectPage(PageUtil.initPage(new PageVO()), wrapper);
        System.out.println(apAdminEntityPage);

    }

}
