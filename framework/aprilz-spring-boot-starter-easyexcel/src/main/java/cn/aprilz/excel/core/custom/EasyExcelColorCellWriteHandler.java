package cn.aprilz.excel.core.custom;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * @author Aprilz
 * @date 2023/2/23-9:39
 * @description 根据数据自定义颜色
 */
public class EasyExcelColorCellWriteHandler implements CellWriteHandler {


    //状态
    private static final Integer COLUMN_INDEX = 0;
    private static final String NORMAL = "在场";
    private static final String OVERTIME = "离场";

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        //跳过表头
        if (isHead) {
            return;
        }
        Workbook workbook = writeSheetHolder.getSheet().getWorkbook();

        CellStyle cellStyle = workbook.createCellStyle();
        if (cell.getColumnIndex() == COLUMN_INDEX) {
            String stringCellValue = cell.getStringCellValue();
            Font writeFont = workbook.createFont();

            if (NORMAL.equals(stringCellValue)) {
                writeFont.setColor(IndexedColors.GREEN.getIndex());
            } else if (OVERTIME.equals(stringCellValue)) {
                writeFont.setColor(IndexedColors.RED.getIndex());
            } else {
                return;
            }
            cellStyle.setFont(writeFont);
            cell.setCellStyle(cellStyle);
        }


    }
}
