package com.ipsos.cd.selenium.projects.cd.portal;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.ipsos.cd.selenium.exceptions.InvalidPathForExcelException;
import com.ipsos.cd.selenium.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Excelutils {

    private static FileInputStream fis;
    private static FileOutputStream fileOut;
    private static Workbook workbook;
    private static Sheet sheet;
    private static Cell cell;
    private static Row row;
    private static String excelFilePath;
    private static Map<String, Integer> columns = new HashMap<>();

    // Set the Excel file and sheet to work with
    public static void setExcelFile(String excelPath, String sheetName) throws IOException {
        try {
            File f = new File(excelPath);
            if (!f.exists()) {
                LogUtils.error("Excel file not found: " + excelPath);
                throw new InvalidPathForExcelException("File Excel path not found.");
            }

            if (sheetName.isEmpty()) {
                LogUtils.error("Sheet name is empty.");
                throw new InvalidPathForExcelException("The sheet name is empty.");
            }

            fis = new FileInputStream(excelPath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                LogUtils.error("Sheet not found: " + sheetName);
                throw new InvalidPathForExcelException("Sheet not found.");
            }

            excelFilePath = excelPath;

            // Store column headers
            sheet.getRow(0).forEach(cell -> columns.put(cell.getStringCellValue(), cell.getColumnIndex()));

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.error("Error in setting the Excel file: " + e.getMessage());
        }
    }

    // Get data from a specific cell
    public static String getCellData(int rowNum, int colNum) {
        try {
            cell = sheet.getRow(rowNum).getCell(colNum);
            String cellData = null;
            switch (cell.getCellType()) {
                case STRING:
                    cellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    cellData = String.valueOf((long) cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    cellData = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    cellData = "";
                    break;
                default:
                    cellData = "";
                    break;
            }
            return cellData;
        } catch (Exception e) {
            LogUtils.error("Error in getting cell data: " + e.getMessage());
            return "";
        }
    }

    // Overloaded method to get cell data using column name
    public static String getCellData(int rowNum, String columnName) {
        return getCellData(rowNum, columns.get(columnName));
    }

    // Write data to a specific cell
    public static void setCellData(String text, int rowNumber, int colNumber) {
        try {
            row = sheet.getRow(rowNumber);
            if (row == null) {
                row = sheet.createRow(rowNumber);
            }
            cell = row.getCell(colNumber);

            if (cell == null) {
                cell = row.createCell(colNumber);
            }
            cell.setCellValue(text);

            // Save changes to the Excel file
            fileOut = new FileOutputStream(excelFilePath);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            LogUtils.error("Error in setting cell data: " + e.getMessage());
        }
    }

    // Overloaded method to set data using column name
    public static void setCellData(String text, int rowNumber, String columnName) {
        setCellData(text, rowNumber, columns.get(columnName));
    }

    // Get the total number of rows
    public static int getTotalRows() {
        return sheet.getLastRowNum() + 1;  // Include the header row
    }

    // Get the total number of columns
    public static int getTotalColumns() {
        row = sheet.getRow(0);
        return row.getLastCellNum();
    }

}