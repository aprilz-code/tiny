package com.aprilz.tiny.service.impl;

import com.alibaba.excel.EasyExcel;
import com.aprilz.tiny.excel.ApTestAnalysisEventListener;
import com.aprilz.tiny.mapper.ApExcelTestMapper;
import com.aprilz.tiny.mbg.entity.ApExcelTest;
import com.aprilz.tiny.service.IApExcelTestService;
import com.aprilz.tiny.vo.request.ApExcelTestParam;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * excel-test表 服务实现类
 * </p>
 *
 * @author aprilz
 * @since 2023-02-22
 */
@Service
public class ApExcelTestServiceImpl extends ServiceImpl<ApExcelTestMapper, ApExcelTest> implements IApExcelTestService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void upload3(MultipartFile file) throws IOException {
        ApTestAnalysisEventListener readListener = new ApTestAnalysisEventListener(sqlSessionFactory);
        EasyExcel.read(file.getInputStream(), ApExcelTestParam.class, readListener).ignoreEmptyRow(false)
                //首行头文件
                .sheet().headRowNumber(1).doRead();
    }
}
