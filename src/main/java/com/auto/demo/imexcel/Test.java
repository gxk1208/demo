package com.auto.demo.imexcel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/10 15:21
 */
public class Test {
    public static void main(String[] args)throws IOException {
        parseExcel("d:\\主实体1591692546772.xlsx");
    }
    private static void parseExcel(String excelFile)throws IOException {

        //创建HSSFWorkbook对象
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));
        //创建HSSFSheet对象
        XSSFSheet sheet = wb.getSheetAt(0);
        //创建HSSFRow对象
        XSSFRow row = sheet.getRow(0);
        //创建HSSFCell对象
        XSSFCell cell=row.getCell(0);
        short lastCellNum = row.getLastCellNum();
        //      cell.setCellValue("单元格中的中文");
        ArrayList<String> str = new ArrayList<>();
        for (int i = 0; i < lastCellNum; i++) {
            String s = cell.getSheet().getRow(0).getCell(i)
                    .getRichStringCellValue().toString();
            str.add(s);

        }

        //设置单元格的值
        //输出Excel文件
 //       FileOutputStream output=new FileOutputStream("d:\\workbook.xls");
        FileOutputStream output= null;
        wb.write(output);
        output.flush();
    }
 }

