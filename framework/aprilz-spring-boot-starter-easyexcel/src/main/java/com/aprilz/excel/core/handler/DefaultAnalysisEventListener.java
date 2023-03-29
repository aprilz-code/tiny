package com.aprilz.excel.core.handler;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.aprilz.excel.core.Validators;
import com.aprilz.excel.core.annotations.ExcelLine;
import com.aprilz.excel.core.annotations.FieldRepeat;
import com.aprilz.excel.core.exception.ErrorMessage;
import com.aprilz.excel.core.exception.ExcelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 默认的 AnalysisEventListener
 *
 * @author Aprilz
 * @date 2021/4/16
 */
@Slf4j
public class DefaultAnalysisEventListener extends ListAnalysisEventListener<Object> {

    private final List<Object> list = new ArrayList<>();

    private final List<ErrorMessage> errorMessageList = new ArrayList<>();

    //防止内存溢出，加到redis？
    private Set<String> sets = new HashSet<>();

    private Long lineNum = 1L;

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        Integer totalRowNumber = analysisContext.readSheetHolder().getApproximateTotalRowNumber();
        if (totalRowNumber > 30000) {
            throw new ExcelAnalysisException("超出总行数限制(30000)，总行数为：" + totalRowNumber);
        }

        lineNum++;

        //自定义参数
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
            list.add(o);
        }
    }

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

        if (Objects.isNull(annotation)) {
            return;
        }
        String[] fields = annotation.fields();
        String message = annotation.message();
        //把所有值塞入sets中
        list.forEach(o -> {
            StringBuilder sb = new StringBuilder();
            for (String field : fields) {
                sb.append(ReflectUtil.getFieldValue(o, field) + "-");
            }
            if (sets.contains(sb.toString())) {
                throw new ExcelException(sb + message);
            }
            sets.add(sb.toString());
        });

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
