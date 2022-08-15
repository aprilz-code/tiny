package com.aprilz.tiny;


import com.aprilz.tiny.common.api.PageVO;
import com.aprilz.tiny.common.utils.PageUtil;
import com.aprilz.tiny.mapper.ApUserMapper;
import com.aprilz.tiny.mbg.entity.ApUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class TinyApplicationTests {

    @Resource
    private ApUserMapper apUserMapper;

    @Test
    public void contextLoads() {
        LambdaQueryWrapper<ApUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApUser::getId, 1);
        Page<ApUser> apAdminEntityPage = apUserMapper.selectPage(PageUtil.initPage(new PageVO()), wrapper);
        System.out.println(apAdminEntityPage);

    }

}
