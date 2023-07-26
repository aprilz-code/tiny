package com.aprilz.excel.core.handler;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.annotation.ExcelProperty;
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

    private Long lineNum;

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        Integer totalRowNumber = analysisContext.readSheetHolder().getApproximateTotalRowNumber();
        if (totalRowNumber > 30000) {
            throw new ExcelAnalysisException("超出总行数限制(30000)，总行数为：" + totalRowNumber);
        }

        lineNum = analysisContext.readRowHolder().getRowIndex().longValue();


        // 如果一行Excel数据均为空值，则不装载该行数据
        if (isLineNullValue(o)) {
            return;
        }

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


    }

    @Override
    public List<Object> getList() {
        return list;
    }

    @Override
    public List<ErrorMessage> getErrors() {
        return errorMessageList;
    }


    private boolean isLineNullValue(Object data) {
        if (data instanceof String) {
            return Objects.isNull(data);
        }
        try {
            List<Field> fields = Arrays.stream(data.getClass().getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(ExcelProperty.class))
                    .collect(Collectors.toList());
            List<Boolean> lineNullList = new ArrayList<>(fields.size());
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(data);
                if (Objects.isNull(value)) {
                    lineNullList.add(Boolean.TRUE);
                } else {
                    lineNullList.add(Boolean.FALSE);
                }
            }
            return lineNullList.stream().allMatch(Boolean.TRUE::equals);
        } catch (Exception e) {
            log.error("读取数据行[{}]解析失败: {}", data, e.getMessage());
        }
        return true;
    }
}
