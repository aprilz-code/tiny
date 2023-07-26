//package com.aprilz.tiny.component.excel.validator;
//
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.util.ReflectUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.extra.spring.SpringUtil;
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.exception.ExcelAnalysisException;
//import com.x.basic.common.domain.HjRegionTree;
//import com.x.excel.core.Validators;
//import com.x.excel.core.annotations.ExcelLine;
//import com.x.excel.core.annotations.FieldRepeat;
//import com.x.excel.core.exception.ErrorMessage;
//import com.x.excel.core.handler.ListAnalysisEventListener;
//import com.x.train.component.region.RegionTreeUtil;
//import com.x.train.controller.courseorder.vo.NewOrderStudentWorkerVO;
//import com.x.train.old.cloud.company.domain.PxjdCompanyProject;
//import com.x.train.old.cloud.company.mapper.PxjdCompanyProjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.stereotype.Component;
//
//import javax.validation.ConstraintViolation;
//import java.lang.reflect.Field;
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * @author Aprilz
// * @date 2023/3/10-15:38
// * @description 批量导入在线报名
// */
//@Slf4j
//@Component
//public class OrderAnalysisEventListener extends ListAnalysisEventListener<NewOrderStudentWorkerVO> {
//
//    private final List<NewOrderStudentWorkerVO> list = new ArrayList<>();
//
//    private final List<ErrorMessage> errorMessageList = new ArrayList<>();
//
//    //TODO 防止内存溢出，加到redis？
//    private Set<String> sets = new HashSet<>();
//
//    private Long lineNum = 2L;
//
//
//    @Override
//    public void invoke(NewOrderStudentWorkerVO o, AnalysisContext analysisContext) {
//        Integer totalRowNumber = analysisContext.readSheetHolder().getApproximateTotalRowNumber();
//        if (totalRowNumber > 30000) {
//            throw new ExcelAnalysisException("超出总行数限制(30000)，总行数为：" + totalRowNumber);
//        }
//
//        lineNum++;
//
//        //自定义参数
//        String[] custom = (String[]) analysisContext.getCustom();
//
//        if (custom.length == 0 || StrUtil.isBlank(custom[0])) {
//            throw new RuntimeException("课程id不能为空");
//        }
//
//        Set<ConstraintViolation<Object>> violations = Validators.validate(o);
//        if (!violations.isEmpty()) {
//            Set<String> messageSet = violations.stream().map(ConstraintViolation::getMessage)
//                    .collect(Collectors.toSet());
//            errorMessageList.add(new ErrorMessage(lineNum, messageSet));
//        } else {
//            Field[] fields = o.getClass().getDeclaredFields();
//            for (Field field : fields) {
//                if (field.isAnnotationPresent(ExcelLine.class) && field.getType() == Long.class) {
//                    try {
//                        field.setAccessible(true);
//                        field.set(o, lineNum);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        list.add(o);
//    }
//
//    @Override
//    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        log.debug("Excel read analysed");
//        //此处加入数据重复校验
//        if (list.size() == 0) {
//            return;
//        }
//
//        //获取类上注释
//        NewOrderStudentWorkerVO t = list.get(0);
//        Class<? extends Object> c = t.getClass();
//        FieldRepeat annotation = AnnotationUtils.findAnnotation(c, FieldRepeat.class);
//
//        if (Objects.isNull(annotation)) {
//            return;
//        }
//        String[] fields = annotation.fields();
//        String message = annotation.message();
//        PxjdCompanyProjectMapper companyProjectMapper = SpringUtil.getBean(PxjdCompanyProjectMapper.class);
//        //把所有值塞入sets中
//        for (int i = 0; i < list.size(); i++) {
//            Set<String> errors = new HashSet<>();
//            StringBuilder sb = new StringBuilder();
//            for (String field : fields) {
//                sb.append(ReflectUtil.getFieldValue(list.get(i), field) + "-");
//            }
//            if (sets.contains(sb.toString())) {
//                errors.add(sb + message);
//                //   errorMessageList.add(new ErrorMessage((long) (i + 3), errors));
//            }
//
//            //校验项目属于企业
//            List<PxjdCompanyProject> pxjdCompanyProjects = companyProjectMapper.selectListByCompanyNameAndProjectName(list.get(i).getStudentCompanyName(), list.get(i).getProjectName());
//            if (CollUtil.isEmpty(pxjdCompanyProjects)) {
//                errors.add("企业或者项目信息不符");
//                //   throw new ExcelException("第" + (i + 3) + "行企业或者项目信息不符");
//            }
//
//            if (CollUtil.isNotEmpty(errors)) {
//                int finalI = i;
//                ErrorMessage errorMessage = errorMessageList.stream().filter(err -> err.getLineNum() == (finalI + 3)).findFirst().orElse(null);
//                if (Objects.isNull(errorMessage)) {
//                    errorMessage = new ErrorMessage((long) i + 3);
//                    errorMessage.setErrors(errors);
//                    errorMessageList.add(errorMessage);
//                } else {
//                    Set<String> errors1 = errorMessage.getErrors();
//                    errors1.addAll(errors);
//                    errorMessage.setErrors(errors1);
//                }
//            }
//            sets.add(sb.toString());
//        }
//        sets.clear();
//
////        for (int i = 0; i < list.size(); i++) {
////            Set<String> errors = new HashSet<>();
////
////
////            if (CollUtil.isNotEmpty(errors)) {
////                int finalI = i;
////                ErrorMessage errorMessage = errorMessageList.stream().filter(err -> err.getLineNum() == (finalI + 3)).findFirst().get();
////                if (Objects.isNull(errorMessage)) {
////                    errorMessage = new ErrorMessage((long) i + 3);
////                    errorMessage.setErrors(errors);
////                    errorMessageList.add(errorMessage);
////                } else {
////                    Set<String> errors1 = errorMessage.getErrors();
////                    errors1.addAll(errors);
////                    errorMessage.setErrors(errors);
////                }
////            }
////        }
//
//
//        //全部校验通过，再赋值
//        List<HjRegionTree> tree = RegionTreeUtil.getTree();
//        list.forEach(o -> {
//            String regionName = o.getRegionName();
//            List<String> split = StrUtil.split(regionName, "-");
//            List<HjRegionTree> collect = RegionTreeUtil.valid(tree, split.get(0));
//            o.setProjectProvinceId((Long) collect.get(0).getId());
//            o.setProjectProvinceName(split.get(0));
//            collect = RegionTreeUtil.valid(collect.get(0).getChildren(), split.get(1));
//            o.setProjectCityId((Long) collect.get(0).getId());
//            o.setProjectCityName(split.get(1));
//            collect = RegionTreeUtil.valid(collect.get(0).getChildren(), split.get(2));
//            o.setProjectDistrictId((Long) collect.get(0).getId());
//            o.setProjectDistrictName(split.get(2));
//        });
//
//        //如果需要校验和数据库数据是否有重复，可以新建自定义listener，然后进行查询
//    }
//
//    @Override
//    public List<NewOrderStudentWorkerVO> getList() {
//        return list;
//    }
//
//    @Override
//    public List<ErrorMessage> getErrors() {
//        return errorMessageList;
//    }
//
//
//    private List<HjRegionTree> valid(List<HjRegionTree> tree, String label) {
//        return tree.stream().filter(t -> label.equals(t.getLabel())).collect(Collectors.toList());
//    }
//}
