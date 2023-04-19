package com.aprilz.tiny.component.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.aprilz.tiny.service.impl.ApExcelTest2ServiceImpl;
import com.aprilz.tiny.vo.request.ApExcelTestParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 百万数据分批导入的 AnalysisEventListener
 *
 * @author Aprilz
 * @date 2023/2/24
 */
@Slf4j
public class ApTestAnalysisEventListener extends AnalysisEventListener<ApExcelTestParam> {

//    //批量插入使用线程池
//    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 2 + 1;
//    ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
//    //标志位 true代办可以提交事务
//    private static volatile boolean IS_OK = false;

    //2000条分一次插入
    private static final int BATCH_COUNT = 2000;

    private List<ApExcelTestParam> list = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    //头尾数，出错用于记录回滚
    private Integer start = 0;
    private Integer end = 0;


    private ApExcelTest2ServiceImpl excelTestService;

    //构造器注入
    public ApTestAnalysisEventListener(ApExcelTest2ServiceImpl excelTestService) {
        this.excelTestService = excelTestService;
    }

    @Override
    public void invoke(ApExcelTestParam apExcelTest, AnalysisContext analysisContext) {
        //不一定准确的行数
        //   Integer totalRowNumber = analysisContext.readSheetHolder().getApproximateTotalRowNumber();
        //具体行号
        Integer rowIndex = analysisContext.readRowHolder().getRowIndex();
        //记录行号，如果异常，记录到表，二次补偿，实现最终一致性？


//        // 主线程监控
//        CountDownLatch mainLatch = new CountDownLatch(1);
        //mainLatch.await();
//        //用总行数/BATCH_COUNT，确定需要多少个子线程的ContDownLatch 参考分页算法
//        CountDownLatch childLatch = new CountDownLatch(totalRowNumber % BATCH_COUNT == 0 ? totalRowNumber / BATCH_COUNT : totalRowNumber / BATCH_COUNT + 1);
        list.add(apExcelTest);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            start = rowIndex - BATCH_COUNT;
            end = rowIndex;
            writeData(start, end);
            // 存储完成清理 list
            list = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }


    public void writeData(Integer start, Integer end) {
        String msg = excelTestService.writeData(list, start, end);
        log.info(msg);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.debug("Excel read analysed");
    }


}
