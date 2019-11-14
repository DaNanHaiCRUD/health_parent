package com.itheima;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;

/**
 * @author lizefeng
 * @date 2019/11/14 9:07
 */
public class Mytest {

    @Test
    public void test1() throws IOException {
        XSSFWorkbook sheets = new XSSFWorkbook(new FileInputStream(new File("D:\\abc.xlsx")));
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        for (Row cells : sheetAt) {
            for (Cell cell : cells) {
                System.out.println(cell.getStringCellValue());
            }
        }
    }

    @Test
    public void test2() throws IOException {
        XSSFWorkbook sheets = new XSSFWorkbook(new FileInputStream(new File("D:\\abc.xlsx")));
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheetAt.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                System.out.println(row.getCell(j));
            }
        }
    }

    @Test
    public void test3() throws IOException {
        XSSFWorkbook sheets = new XSSFWorkbook();
        //创建标签页
        XSSFSheet sheet = sheets.createSheet("我的excel");
        //创建行
        XSSFRow row = sheet.createRow(0);
        //创建每一行的数据
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("年龄");

        //创建第二行
        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("001");
        row1.createCell(1).setCellValue("小明");
        row1.createCell(2).setCellValue("18");

        //写入硬盘
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\hello.xlsx"));
        sheets.write(fileOutputStream);

        fileOutputStream.close();
        sheets.close();
    }

    @Test
    public void test4(){

    }
}
