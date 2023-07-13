package com.aprilz.excel.core.drop.handle;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.aprilz.excel.core.drop.entity.ChainDropDown;
import com.aprilz.excel.core.util.ExcelUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * excel 级联下拉框 handler
 */
public class ChainDropDownWriteHandler implements SheetWriteHandler {

    private final Map<Integer, ChainDropDown> map;

    private int headRowNumber = 1;

    public ChainDropDownWriteHandler(Map<Integer, ChainDropDown> map, int headRowNumber) {
        this.map = map;
        this.headRowNumber = headRowNumber;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        // 这里可以对cell进行任何操作
        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        for (Map.Entry<Integer, ChainDropDown> e : map.entrySet()) {
            // k 为存在下拉数据集的单元格下表 v为下拉数据集
            Integer k = e.getKey();
            ChainDropDown v = e.getValue();
            Integer rowIndex = v.getRowIndex();
            CellRangeAddressList rangeList = new CellRangeAddressList(headRowNumber, 65536, k, k);
            Sheet hideSheet = getSheet(workbook, v.getTypeName());
            if (v.isRootFlag()) {
                Row firstRow = hideSheet.createRow(rowIndex);
                List<String> values = v.getDataMap().get(ChainDropDown.ROOT_KEY);
                for (int i = 0; i < values.size(); i++) {
                    Cell rowCell = firstRow.createCell(i);
                    rowCell.setCellValue(values.get(i));
                }
                // 设置级联有效性

                // v 就是下拉列表的具体数据，下拉列表约束数据+，按区域设置不然会超长255字符
                String listFormula = v.getTypeName()+"!$1:$1";
                DataValidationConstraint constraint = helper.createFormulaListConstraint(listFormula);
//                DataValidationConstraint constraint = helper.createExplicitListConstraint(values.toArray(new String[values.size()]));
                // 设置下拉约束
                DataValidation validation = helper.createValidation(constraint, rangeList);
                // 阻止输入非下拉选项的值
                validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
                validation.setShowErrorBox(true);
                validation.setSuppressDropDownArrow(true);
                validation.createErrorBox("提示", "此值与单元格定义格式不一致");
                sheet.addValidationData(validation);



            } else {

                Map<String, List<String>> dataMap = v.getDataMap();

                for (Map.Entry<String, List<String>> entry : dataMap.entrySet()) {
                    String parentValue = entry.getKey();
                    List<String> childValues = entry.getValue();
                    Row row = hideSheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(parentValue);
                    for (int j = 0; j < childValues.size(); j++) {
                        Cell cell = row.createCell(j + 1);
                        cell.setCellValue(childValues.get(j));
                    }
                    // 添加名称管理器
                    String range = getRange(1, rowIndex, childValues.size());
                    Name name = workbook.createName();
                    //key不可重复,+排除部分特殊字符，首字母不可为数字
                    name.setNameName("s"+parentValue.replaceAll("（","_").replaceAll("）","_").replaceAll("-","").replaceAll("“","").replaceAll("”","").replaceAll("、","").replaceAll(" ","").replaceAll("，",""));
                    String formula = v.getTypeName() + "!" + range;
                    name.setRefersToFormula(formula);
                }

            }
            // 这里头有两行 headNUM
            int beginRow = 3;
            // 设置级联有效性
            String listFormula = "INDIRECT(CONCAT(\"s\",SUBSTITUTE(SUBSTITUTE(SUBSTITUTE(SUBSTITUTE(SUBSTITUTE(SUBSTITUTE(SUBSTITUTE(SUBSTITUTE("+ExcelUtils.getColNum(k - 1) + beginRow+",\"（\",\"_\"),\"）\",\"_\"),\"-\",\"\"),\"“\",\"\"),\"”\",\"\"),\"、\",\"\"),\" \",\"\"),\"，\",\"\")))";
            DataValidationConstraint formulaListConstraint = helper.createFormulaListConstraint(listFormula);
            // 设置下拉约束
            DataValidation validation = helper.createValidation(formulaListConstraint, rangeList);
            validation.setEmptyCellAllowed(false);
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            // 设置输入信息提示信息
            validation.createPromptBox("下拉选择提示", "请使用下拉方式选择合适的值！");
            sheet.addValidationData(validation);
        }
        // 设置存储下拉列值得sheet为隐藏
//        int hiddenIndex = workbook.getSheetIndex(sheet.getSheetName());
//        if (!workbook.isSheetHidden(hiddenIndex)) {
//            workbook.setSheetHidden(hiddenIndex, true);
//        }
    }

    public Sheet getSheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (!ObjectUtils.isEmpty(sheet)) {
            return sheet;
        }
        return workbook.createSheet(sheetName);
    }

    /**
     * 计算formula
     *
     * @param offset   偏移量，如果给0，表示从A列开始，1，就是从B列
     * @param rowNum   第几行
     * @param colCount 一共多少列
     * @return 如果给入参 1,1,10. 表示从B1-K1。最终返回 $B$1:$K$1
     */
    public static String getRange(int offset, int rowNum, int colCount) {
        String start = ExcelUtils.getColNum(offset);
        String end = ExcelUtils.getColNum(colCount);
        String format = "$%s$%s:$%s$%s";
        return String.format(format, start, rowNum, end, rowNum);
    }


}
