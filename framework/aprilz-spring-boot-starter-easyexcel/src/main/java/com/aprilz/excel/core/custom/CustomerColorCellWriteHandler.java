package com.aprilz.excel.core.custom;

import com.alibaba.excel.util.BooleanUtils;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import org.apache.poi.ss.usermodel.*;

import java.util.Set;

/**
 * @author Aprilz
 * @date 2023/2/23-9:39
 * @description 根据数据自定义颜色
 */
public class CustomerColorCellWriteHandler implements CellWriteHandler {
    private Set<Long> lineNums;

    public CustomerColorCellWriteHandler(Set<Long> lineNums) {
        this.lineNums = lineNums;
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        if (BooleanUtils.isNotTrue(context.getHead())) {
            Workbook workbook = context.getWriteSheetHolder().getSheet().getWorkbook();
            CellStyle cellStyle = workbook.createCellStyle();
            Cell cell = context.getCell();
            lineNums.forEach(num -> {
                if (cell.getColumnIndex() == 0) {
                    double numericCellValue = cell.getNumericCellValue();
                    if (numericCellValue == num.doubleValue()) {
                        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cell.setCellStyle(cellStyle);
                    }
                }else{
                    cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
                    // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cell.setCellStyle(cellStyle);
                }
            });
            context.getFirstCellData().setWriteCellStyle(null);
        }

    }
}
