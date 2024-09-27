package com.ipsos.cd.selenium.projects.cd.portal;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelCreator {
    public static void main(String[] args) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Login3 Sheet");


        Row headerRow = sheet.createRow(0);
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("function");
        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("email");
        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("password");


        Row row1 = sheet.createRow(1);
        Cell cell1 = row1.createCell(0);
        cell1.setCellValue("LoginWithValidCredentials");
        Cell cell2 = row1.createCell(1);
        cell2.setCellValue("Sharmila@prodifyllp.com");
        Cell cell3 = row1.createCell(2);
        cell3.setCellValue("Ipsos@2019");

        Row row2 = sheet.createRow(2);
        Cell cell4 = row2.createCell(0);
        cell4.setCellValue("LoginWithInvalidEmail");
        Cell cell5 = row2.createCell(1);
        cell5.setCellValue("invalidemail@domain.com");
        Cell cell6 = row2.createCell(2);
        cell6.setCellValue("prodi");

        Row row3 = sheet.createRow(3);
        Cell cell7 = row3.createCell(0);
        cell7.setCellValue("LoginWithIncorrectPassword");
        Cell cell8 = row3.createCell(1);
        cell8.setCellValue("Sharmila@prodifyllp.com");
        Cell cell9 = row3.createCell(2);
        cell9.setCellValue("WrongPassword");

        Row row4 = sheet.createRow(4);
        Cell cell10 = row4.createCell(0);
        cell10.setCellValue("ForgotPassword");
        Cell cell11 = row4.createCell(1);
        cell11.setCellValue("geethu1913@gmail.com");
        Cell cell12 = row4.createCell(2);
        cell12.setCellValue("N/A");

        try (FileOutputStream fileOut = new FileOutputStream("Login3.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
