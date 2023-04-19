package com.aprilz.tiny.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aprilz.tiny.common.utils.MybatisBatchUtils;
import com.aprilz.tiny.mapper.ApExcelTestMapper;
import com.aprilz.tiny.mbg.entity.ApExcelTest;
import com.aprilz.tiny.vo.request.ApExcelTestParam;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * excel-test表 服务实现类
 * </p>
 *
 * @author aprilz
 * @since 2023-02-22
 */
@Service
@Transactional
public class ApExcelTest2ServiceImpl {

    @Autowired
    private MybatisBatchUtils batchUtils;

    @Resource
    private ApExcelTestMapper testMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

//    public String writeData(List<ApExcelTestParam> list, Integer start, Integer end) {
//        for (int i=0;i<2000;i++){
//            ApExcelTestParam param = new ApExcelTestParam();
//            param.setAge(1);
//            param.setTestTime(new Date());
//            param.setUrl("BAIDU.COM");
//            param.setSex(1);
//            if(i!=1955){
//                param.setName("MZ");
//            }
//            list.add(param);
//        }
//        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
//        ApExcelTestMapper mapper = sqlSession.getMapper(ApExcelTestMapper.class);
//
//        try {
//            list.forEach(result -> {
//                ApExcelTest apExcelTest = new ApExcelTest();
//                BeanUtil.copyProperties(result, apExcelTest);
//                mapper.insert(apExcelTest);
//            });
//            sqlSession.commit();
//        } catch (Exception e) {
//            sqlSession.rollback();
//            System.out.println(start + "-------" + end);
//            throw new RuntimeException(e);
//            // insertTable()  start,end
//        } finally {
//            sqlSession.close();
//        }
//        return "导入成功";
//    }

    public String writeData(List<ApExcelTestParam> list, Integer start, Integer end) {
        for (int i = 0; i < 2000; i++) {
            ApExcelTestParam param = new ApExcelTestParam();
            param.setAge(1);
            param.setTestTime(new Date());
            param.setUrl("BAIDU.COM");
            param.setName("name" + i);
            param.setSex(1);
            if (i != 1955) {
                param.setName("MZ");
            }
            list.add(param);
        }
        ArrayList<ApExcelTest> list1 = new ArrayList<>();
        list.forEach(result -> {
            ApExcelTest apExcelTest = new ApExcelTest();
            BeanUtil.copyProperties(result, apExcelTest);
            list1.add(apExcelTest);
        });
        batchUtils.batchUpdateOrInsert(list1, ApExcelTestMapper.class, (item, testMapper) -> testMapper.insert(item));
        return "导入成功";
    }
}
