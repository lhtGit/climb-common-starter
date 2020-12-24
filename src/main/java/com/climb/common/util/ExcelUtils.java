package com.climb.common.util;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.List;
import java.util.function.Function;

/**
 * @author lht
 * @since 2020/11/9 17:53
 */
@SuppressWarnings("all")
public class ExcelUtils {


    /**
     * 设置title并设置自适应宽度
     * @author lht
     * @since  2020/11/12 10:19
     * @param sheet
     * @param rowIndex
     * @param vals
     */
    public static void setTitleAutoSizeCol(Sheet sheet, int rowIndex, List<String> vals){
        Row row = sheet.createRow(rowIndex);
        for (int i = 0; i < vals.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(vals.get(i));
            //设置自适应宽度
            sheet.autoSizeColumn(i, true);
            sheet.setColumnWidth(i, vals.get(i).getBytes().length * 256);
        }
    }

    /**
     * 设置正行内容
     * @author lht
     * @since  2020/11/10 13:48
     * @param sheet
     * @param rowIndex
     * @param vals
     */
    public static void setRow(Sheet sheet, int rowIndex, List<String> vals){
        Row row = sheet.createRow(rowIndex);
        for (int i = 0; i < vals.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(vals.get(i));
        }
    }

    /**
     * 设置单元格为日期格斯
     * @author lht
     * @since  2020/11/10 13:47
     * @param sheet
     * @param indexColumn
     * @param dateFormat yyyy-MM-dd
     */
    public static void setDefaultDataFormatToDate(Sheet sheet,int indexColumn,String dateFormat){
        setDefaultDataFormat(sheet,indexColumn,creationHelper -> {
            return creationHelper.createDataFormat().getFormat(dateFormat);
        });
    }
    /**
     * 设置下拉 有效检查.
     * @author lht
     * @since  2020/11/9 18:16
     * @param sheet 要添加此检查的Sheet
     * @param firstRow 开始行
     * @param lastRow 结束行
     * @param firstCol 开始列
     * @param lastCol 结束列
     * @param explicitListValues 有效性检查的下拉列表
     */
    public static void setSelectValidate(Sheet sheet, int firstRow,  int lastRow,
                                         int firstCol,  int lastCol,String[] explicitListValues) {

        CellRangeAddressList addressList = new CellRangeAddressList(firstRow,lastRow,firstCol,lastCol);
        setValidate(sheet,addressList
        ,(dvHelper) -> {
            return dvHelper
                    .createExplicitListConstraint(explicitListValues);
        }
        ,(sheetTemp) -> {
            return DVConstraint.createExplicitListConstraint(explicitListValues);
        });
    }

    /**
     * 设置日期 有效检查
     * @author lht
     * @since  2020/11/12 10:28
     * @param sheet
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @param explicitListValues
     */
    public static void setDateValidate(Sheet sheet, int firstRow,  int lastRow,
                                         int firstCol,  int lastCol) {

        CellRangeAddressList addressList = new CellRangeAddressList(firstRow,lastRow,firstCol,lastCol);
        setValidate(sheet,addressList
        ,(dvHelper) -> {
            return dvHelper.createDateConstraint(DataValidationConstraint.OperatorType.BETWEEN
                    ,"1900-01-01 00:00:00","9999-12-31 00:00:00","yyyy-MM-dd HH:mm:ss");
        }
        ,(sheetTemp) -> {
           return       DVConstraint.createDateConstraint(DataValidationConstraint.OperatorType.BETWEEN
                            ,"1900-01-01 00:00:00","9999-12-31 00:00:00","yyyy-MM-dd HH:mm:ss");
        });
    }




    /**
     *
     * @author lht
     * @since  2020/11/10 9:38
     * @param sheet
     * @param addressList
     * @param XSSFFunction
     * @param HSSFFunction
     */
    private static void setValidate(Sheet sheet, CellRangeAddressList addressList
            , Function<XSSFDataValidationHelper,DataValidationConstraint> XSSFFunction
            , Function<Sheet,DataValidationConstraint> HSSFFunction
    ){

        DataValidation validation = null;
        if(sheet instanceof XSSFSheet){
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet)sheet);
            DataValidationConstraint dataValidationConstraint = XSSFFunction.apply(dvHelper);
            validation = dvHelper.createValidation(dataValidationConstraint, addressList);
        }else{
            DataValidationConstraint dataValidationConstraint = HSSFFunction.apply(sheet);
            validation = new HSSFDataValidation(addressList, dataValidationConstraint);
        }
        // 输入无效值时是否显示错误框
        validation.setShowErrorBox(true);
        // 验证输入数据是否真确
        validation.setSuppressDropDownArrow(true);
        sheet.addValidationData(validation);
    }

    /**
     * 设置整列单元格格式
     * @author lht
     * @since  2020/11/10 13:45
     * @param sheet
     * @param indexColumn
     * @param function
     */
    private static void setDefaultDataFormat(Sheet sheet,int indexColumn, Function<CreationHelper,Short> function){
        Workbook book = sheet.getWorkbook();
        CellStyle cellStyle = book.createCellStyle();
        CreationHelper createHelper = book.getCreationHelper();
        cellStyle.setDataFormat(function.apply(createHelper));
        sheet.setDefaultColumnStyle(indexColumn,cellStyle);
    }
}
