package com.aprilz.excel.core.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.aprilz.excel.core.Validators;
import com.aprilz.excel.core.annotations.ExcelLine;
import com.aprilz.excel.core.annotations.FieldRepeat;
import com.aprilz.excel.core.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 默认的 AnalysisEventListener
 *
 * @date 2021/4/16
 */
@Slf4j
public class DefaultAnalysisEventListener extends ListAnalysisEventListener<Object> {

    //处理返回数据
    private final List<Object> list = new ArrayList<>();

    //处理返回错误信息
    private final List<ErrorMessage> errorMessageList = new ArrayList<>();

    //防止内存溢出，加到redis？
    private Set<String> sets = new HashSet<>();
    //需要注意的是，T类需要实现equals和hashCode方法，T。如果不重写这两个方法，则默认使用Object类中的equals和hashCode方法，这可能会导致错误的结果。

    private Long lineNum = 1L;

    /**
     * @desc 做单行数据校验
     * @param o
     * @param analysisContext
     */
    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {

        Integer totalRowNumber = analysisContext.readSheetHolder().getApproximateTotalRowNumber();
        if (totalRowNumber > 30000) {
            throw new ExcelAnalysisException("超出总行数限制(30000)，总行数为：" + totalRowNumber);
        }

        lineNum++;

        //自定义参数，自己看是否需要
        String[] custom = (String[]) analysisContext.getCustom();

        Set<ConstraintViolation<Object>> violations = Validators.validate(o);
        if (!violations.isEmpty()) {
            Set<String> messageSet = violations.stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            errorMessageList.add(new ErrorMessage(lineNum, messageSet));
        } else {
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ExcelLine.class) && field.getType() == Long.class) {
                    try {
                        field.setAccessible(true);
                        field.set(o, lineNum);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //如果不需要在doAfterAllAnalysed做校验。以及不需要错误数据，可以放到上两行内
        list.add(o);
    }

    /**
     * @desc 做所有行数据校验，适合做重复数据校验等
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.debug("Excel read analysed");
        //此处加入数据重复校验
        if (list.size() == 0) {
            return;
        }
        //获取类上注释
        Object t = list.get(0);
        Class<?> c = t.getClass();
        FieldRepeat annotation = AnnotationUtils.findAnnotation(c, FieldRepeat.class);
        String[] fields = Objects.nonNull(annotation) ? annotation.fields() : null;
        String message = Objects.nonNull(annotation) ? annotation.message() : null;

        //把所有值塞入sets中
        for (int i = 0; i < list.size(); i++) {
            Set<String> errors = new HashSet<>();
            StringBuilder sb = new StringBuilder();
            if (Objects.nonNull(annotation)) {
                for (String field : fields) {
                    sb.append(ReflectUtil.getFieldValue(list.get(i), field) + "-");
                }
                if (sets.contains(sb.toString())) {
                    errors.add(sb + message);
                }
            }
            //其他校验

            if (CollUtil.isNotEmpty(errors)) {
                int finalI = i;
                ErrorMessage errorMessage = errorMessageList.stream().filter(err -> err.getLineNum() == (finalI + 3)).findFirst().get();
                if (Objects.isNull(errorMessage)) {
                    // 1为headNum行数
                    errorMessage = new ErrorMessage((long) i + 1);
                    errorMessage.setErrors(errors);
                    errorMessageList.add(errorMessage);
                } else {
                    //不为空则添入以前的set集合内
                    Set<String> errors1 = errorMessage.getErrors();
                    errors1.addAll(errors);
                    errorMessage.setErrors(errors);
                }
            }

            sets.add(sb.toString());
        }

        sets.clear();
        //如果需要校验和数据库数据是否有重复，可以新建自定义listener，然后进行查询
    }

    @Override
    public List<Object> getList() {
        return list;
    }

    @Override
    public List<ErrorMessage> getErrors() {
        return errorMessageList;
    }

}
