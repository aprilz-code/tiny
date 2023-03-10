package cn.aprilz.excel.core.handler;

import cn.aprilz.excel.core.Validators;
import cn.aprilz.excel.core.annotations.ExcelLine;
import cn.aprilz.excel.core.annotations.FieldRepeat;
import cn.aprilz.excel.core.exception.ErrorMessage;
import cn.aprilz.excel.core.exception.ExcelException;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.core.annotation.AnnotationUtils;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 默认的 AnalysisEventListener
 *
 * @author lengleng
 * @author L.cm
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

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        Integer totalRowNumber = analysisContext.readSheetHolder().getApproximateTotalRowNumber();
        if (totalRowNumber > 30000) {
            throw new ExcelAnalysisException("超出总行数限制(30000)，总行数为：" + totalRowNumber);
        }

        lineNum++;

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
                throw new ExcelException(message);
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
