package com.auto.demo.utils;


import com.auto.demo.common.Excel;
import com.auto.demo.common.ExcelFormat;
import com.auto.demo.common.ExcelResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Excel处理
 *
 * @version 1.0
 * @since 2019年7月1日
 */
public class ExcelUtil {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 读取Excel
     *
     * @param filePath 文件路径
     * @return 文件内容
     * @throws IOException 文件操作异常
     */
    public static List<String[]> readExcelXlsx(String filePath) throws IOException {

        try (XSSFWorkbook wb = new XSSFWorkbook(filePath);) {
            List<String[]> returnlist = new ArrayList<String[]>();

            Sheet sheet = null;
            sheet = wb.getSheetAt(0);

            // 获取最后行号
            int lastRowNum = sheet.getLastRowNum();

            if (lastRowNum > 0) { // 如果>0，表示有数据
                // System.out.println("\n开始读取名为【" + sheet.getSheetName() + "】的内容：");
                logger.debug("\n开始读取名为【{}】的内容：", sheet.getSheetName());
            }
            Row row = null;
            // 循环读取
            String ss[] = null;
            for (int i = 0; i <= lastRowNum; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    // rowList.add(row);
                    ss = new String[row.getLastCellNum()];
                    // System.out.print("第" + (i + 1) + "行：");
                    logger.debug("第{}行：", (i + 1));
                    // 获取每一单元格的值
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        String value = row.getCell(j).getStringCellValue();
                        ss[j] = value;
                        if (!("").equals(value)) {
                            // System.out.print(value + " | ");
                            logger.debug("{} | ", value);
                        }
                    }
                    // System.out.println("");
                    logger.debug("");
                    returnlist.add(ss);
                }
            }
            // ======================
            return returnlist;
        }
    }

    /***
     * 导入Excel(.xls)
     * @param bytes Excel文件内容
     * @param classz 实例类
     * @return
     */
    public static ExcelResult excelImport_xls(byte[] bytes, Class<?> classz, Integer sheetIndex) {
        int i = 0, j = 0;
        List<Object> list = null;
        StringBuilder message_empty = null;
        StringBuilder message_regexp = null;
        //StringBuilder message_type = null;
        try (InputStream inputStream = new ByteArrayInputStream(bytes);
             // 读取工作簿
             HSSFWorkbook workBook = new HSSFWorkbook(inputStream);) {
            message_empty = new StringBuilder("");
            message_regexp = new StringBuilder("");
            //message_type = new StringBuilder("");
            //读取工作表
            HSSFSheet rs = workBook.getSheetAt(sheetIndex);
            int clos = rs.getRow(0).getPhysicalNumberOfCells();//得到所有的列
            int rows = rs.getLastRowNum() + 1;//得到所有的行
            Map<String, String> fieldMap = new HashMap<String, String>();
            StringBuilder fieldNames = new StringBuilder("");
            for (Field field : classz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Excel.class) && field.getAnnotation(Excel.class).imp()) {
                    Excel excel = field.getAnnotation(Excel.class);
                    fieldMap.put(excel.name(), field.getName());
                }
            }
            for (j = 0; j < clos; j++) {
                String name = rs.getRow(0).getCell(j).getStringCellValue();
                fieldNames.append(fieldMap.get(name.trim()) + ",");
            }
            if (fieldNames.length() > 1) {
                fieldNames.setLength(fieldNames.length() - 1);
            }
            String filedNames[] = fieldNames.toString().split(",");
            list = new ArrayList<Object>();
            for (i = 1; i < rows; i++) {
                Object entity = classz.newInstance();
                //行前2列数据为空则跳过此行
                if ((null == rs.getRow(i).getCell(0) && null == rs.getRow(i).getCell(1))) {
                    continue;
                }
                if (rs.getRow(i).getCell(0).getCellType() == CellType.BLANK && rs.getRow(i).getCell(1).getCellType() == CellType.BLANK) {
                    continue;
                }
                for (j = 0; j < clos; j++) {
                    HSSFRow row = rs.getRow(i);
                    HSSFCell cell = row.getCell(j);
                    Field field = classz.getDeclaredField(filedNames[j]);
                    //行数据是否为空
                    if (null == cell || cell.getCellType() == CellType.BLANK) {
                        if (field.getAnnotation(Excel.class).require()) {
                            message_empty.append((i + 1) + cloName[j] + ",");
                        }
                        continue;
                    }
                    Method method = classz.getMethod("set" + captureName(filedNames[j]), field.getType());
                    Object object = null;
                    if (!field.getAnnotation(Excel.class).dateFormat().isEmpty()) {
                        String value = cell.getStringCellValue();
                        //日期型
                        if (field.getType() == Date.class) {
                            object = DateUtil.toDate(value, field.getAnnotation(Excel.class).dateFormat());
                        }
                        //整形
                        else {
                            object = (DateUtil.toDate(value, field.getAnnotation(Excel.class).dateFormat()).getTime());
                        }
                    }
                    //单元格数据类型校验
                    else if (cell.getCellType() == CellType.NUMERIC) {
                        DecimalFormat df = new DecimalFormat("0.00");
                        String value = df.format(cell.getNumericCellValue());
                        if (field.getType() == String.class) {
                            //message_type.append((i + 1) + cloName[j] + ",");
                            //continue;
                            object = new DecimalFormat("0").format(cell.getNumericCellValue());
                        } else if (field.getType() == int.class || field.getType() == Integer.class) {
                            DecimalFormat dfInt = new DecimalFormat("0");
                            object = Integer.parseInt(dfInt.format(cell.getNumericCellValue()));
                        } else if (field.getType() == long.class || field.getType() == Long.class) {
                            object = Long.parseLong(value);
                        } else if (field.getType() == float.class || field.getType() == Float.class) {
                            object = Float.parseFloat(value);
                        } else if (field.getType() == double.class || field.getType() == Double.class) {
                            object = Double.parseDouble(value);
                        } else if (field.getType() == short.class || field.getType() == Short.class) {
                            object = Short.parseShort(value);
                        } else if (field.getType() == byte.class || field.getType() == Byte.class) {
                            object = Byte.parseByte(value);
                        } else {
                            object = value;
                        }
                    } else if (cell.getCellType() == CellType.STRING) {
                        if (StringUtils.isEmpty(cell.getStringCellValue())) {
                            continue;
                        }
                        if (field.getType() != String.class) {
                            message_regexp.append((i + 1) + cloName[j] + ",");
                            continue;
                        }
                        if (null != cell.getStringCellValue() && !cell.getStringCellValue().trim().isEmpty()) {
                            object = cell.getStringCellValue();
                        }
                    }
                    if (null != object) {
                        String regexp = field.getAnnotation(Excel.class).regexp();
                        if (!regexp.isEmpty()) {
                            if (!Pattern.compile(regexp).matcher(object.toString()).matches()) {
                                message_regexp.append((i + 1) + cloName[j] + ",");
                                continue;
                            }
                        }
                        method.invoke(entity, object);
                    }
                }
                list.add(entity);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel读取出错", e);
            return new ExcelResult((i + 1) + cloName[j] + "列数据格式不正确！", true);
        }
        try {
            String message = "";
            if (message_empty.length() != 0) {
                message_empty.setLength(message_empty.length() - 1);
                message += message_empty.toString() + "不能为空！";
            }
//            if(message_type.length()!=0){
//                message_type.setLength(message_type.length()-1);
//                message += message_type.toString()+"数据类型错误！";
//            }
            if (message_regexp.length() != 0) {
                message_regexp.setLength(message_regexp.length() - 1);
                message += message_regexp.toString() + "格式错误！";
            }
            return new ExcelResult(list, message, false);
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel读取出错", e);
            return new ExcelResult("导入失败", true);
        }
    }

    /***
     * 导入Excel(.xlsx)
     * @param bytes Excel文件内容
     * @param classz 实例类
     * @param sheetIndex sheet表索引
     * @return
     */
    public static ExcelResult excelImport_xlsx(byte[] bytes, Class<?> classz, Integer sheetIndex) {
        int i = 0, j = 0;
        List<Object> list = null;
        StringBuilder message_empty = null;
        StringBuilder message_regexp = null;
        //StringBuilder message_type = null;
        try (InputStream inputStream = new ByteArrayInputStream(bytes);
             // 读取工作簿
             XSSFWorkbook workBook = new XSSFWorkbook(inputStream)) {
            message_empty = new StringBuilder("");
            message_regexp = new StringBuilder("");
            //message_type = new StringBuilder("");

            //读取工作表
            XSSFSheet rs = workBook.getSheetAt(sheetIndex);
            int clos = rs.getRow(0).getPhysicalNumberOfCells();//得到所有的列
            int rows = rs.getLastRowNum() + 1;//得到所有的行
            Map<String, String> fieldMap = new HashMap<String, String>();
            StringBuilder fieldNames = new StringBuilder("");
            for (Field field : classz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Excel.class) && field.getAnnotation(Excel.class).imp()) {
                    Excel excel = field.getAnnotation(Excel.class);
                    fieldMap.put(excel.name(), field.getName());
                }
            }
            for (j = 0; j < clos; j++) {
                String name = rs.getRow(0).getCell(j).getStringCellValue();
                fieldNames.append(fieldMap.get(name.trim()) + ",");
            }
            if (fieldNames.length() > 1) {
                fieldNames.setLength(fieldNames.length() - 1);
            }
            String filedNames[] = fieldNames.toString().split(",");
            list = new ArrayList<Object>();
            for (i = 1; i < rows; i++) {
                Object entity = classz.newInstance();
                //行前2列数据为空则跳过此行
                if ((null == rs.getRow(i).getCell(0) && null == rs.getRow(i).getCell(1))) {
                    continue;
                }
                if (rs.getRow(i).getCell(0).getCellType() == CellType.BLANK && rs.getRow(i).getCell(1).getCellType() == CellType.BLANK) {
                    continue;
                }
                for (j = 0; j < clos; j++) {
                    XSSFRow row = rs.getRow(i);
                    XSSFCell cell = row.getCell(j);
                    Field field = classz.getDeclaredField(filedNames[j]);
                    //行数据是否为空
                    if (null == cell || cell.getCellType() == CellType.BLANK) {
                        if (field.getAnnotation(Excel.class).require()) {
                            message_empty.append((i + 1) + cloName[j] + ",");
                        }
                        continue;
                    }
                    Method method = classz.getMethod("set" + captureName(filedNames[j]), field.getType());
                    Object object = null;
                    if (!field.getAnnotation(Excel.class).dateFormat().isEmpty()) {
                        String value = cell.getStringCellValue();
                        //日期型
                        if (field.getType() == Date.class) {
                            object = DateUtil.toDate(value, field.getAnnotation(Excel.class).dateFormat());
                        }
                        //整形
                        else {
                            object = (DateUtil.toDate(value, field.getAnnotation(Excel.class).dateFormat()).getTime());
                        }
                    }
                    //单元格数据类型校验
                    else if (cell.getCellType() == CellType.NUMERIC) {
                        DecimalFormat df = new DecimalFormat("0.00");
                        String value = df.format(cell.getNumericCellValue());
                        if (field.getType() == String.class) {
                            //message_type.append((i + 1) + cloName[j] + ",");
                            //continue;
                            object = new DecimalFormat("0").format(cell.getNumericCellValue());
                        } else if (field.getType() == int.class || field.getType() == Integer.class) {
                            DecimalFormat dfInt = new DecimalFormat("0");
                            object = Integer.parseInt(dfInt.format(cell.getNumericCellValue()));
                        } else if (field.getType() == long.class || field.getType() == Long.class) {
                            object = Long.parseLong(value);
                        } else if (field.getType() == float.class || field.getType() == Float.class) {
                            object = Float.parseFloat(value);
                        } else if (field.getType() == double.class || field.getType() == Double.class) {
                            object = Double.parseDouble(value);
                        } else if (field.getType() == short.class || field.getType() == Short.class) {
                            object = Short.parseShort(value);
                        } else if (field.getType() == byte.class || field.getType() == Byte.class) {
                            object = Byte.parseByte(value);
                        } else {
                            object = value;
                        }
                    } else if (cell.getCellType() == CellType.STRING) {
                        if (StringUtils.isEmpty(cell.getStringCellValue())) {
                            continue;
                        }
                        if (field.getType() != String.class) {
                            message_regexp.append((i + 1) + cloName[j] + ",");
                            continue;
                        }
                        object = cell.getStringCellValue();
                    }
                    if (null != object) {
                        String regexp = field.getAnnotation(Excel.class).regexp();
                        if (!regexp.isEmpty()) {
                            if (!Pattern.compile(regexp).matcher(object.toString()).matches()) {
                                message_regexp.append((i + 1) + cloName[j] + ",");
                                continue;
                            }
                        }
                        method.invoke(entity, object);
                    }
                }
                list.add(entity);
            }
        } catch (Exception e) {
             e.printStackTrace();
            logger.warn("Excel读取出错", e);
            return new ExcelResult((i + 1) + cloName[j] + "列数据格式不正确！", true);
        }
        try {
            String message = "";
            if (message_empty.length() != 0) {
                message_empty.setLength(message_empty.length() - 1);
                message += message_empty.toString() + "不能为空！";
            }
//            if(message_type.length()!=0){
//                message_type.setLength(message_type.length()-1);
//                message += message_type.toString()+"数据类型错误！";
//            }
            if (message_regexp.length() != 0) {
                message_regexp.setLength(message_regexp.length() - 1);
                message += message_regexp.toString() + "格式错误！";
            }
            return new ExcelResult(list, message, false);
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel读取出错", e);
            return new ExcelResult("导入失败", true);
        }
    }

    public static ExcelResult excelImport_xlsx(File file, Class<?> classz, Integer sheetIndex) {
        int i = 0, j = 0;
        List<Object> list = null;
        StringBuilder message_empty = null;
        StringBuilder message_regexp = null;
        //StringBuilder message_type = null;
        try (FileInputStream inputStream = new FileInputStream(file);
             // 读取工作簿
             XSSFWorkbook workBook = new XSSFWorkbook(inputStream);) {
            message_empty = new StringBuilder("");
            message_regexp = new StringBuilder("");
            //message_type = new StringBuilder("");

            //读取工作表
            XSSFSheet rs = workBook.getSheetAt(sheetIndex);
            int clos = rs.getRow(0).getPhysicalNumberOfCells();//得到所有的列
            int rows = rs.getLastRowNum() + 1;//得到所有的行
            Map<String, String> fieldMap = new HashMap<String, String>();
            StringBuilder fieldNames = new StringBuilder("");
            for (Field field : classz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Excel.class) && field.getAnnotation(Excel.class).imp()) {
                    Excel excel = field.getAnnotation(Excel.class);
                    fieldMap.put(excel.name(), field.getName());
                }
            }
            for (j = 0; j < clos; j++) {
                String name = rs.getRow(0).getCell(j).getStringCellValue();
                fieldNames.append(fieldMap.get(name.trim()) + ",");
            }
            if (fieldNames.length() > 1) {
                fieldNames.setLength(fieldNames.length() - 1);
            }
            String filedNames[] = fieldNames.toString().split(",");
            list = new ArrayList<Object>();
            for (i = 1; i < rows; i++) {
                Object entity = classz.newInstance();
                //行前2列数据为空则跳过此行
                if ((null == rs.getRow(i).getCell(0) && null == rs.getRow(i).getCell(1))) {
                    continue;
                }
                if (rs.getRow(i).getCell(0).getCellType() == CellType.BLANK && rs.getRow(i).getCell(1).getCellType() == CellType.BLANK) {
                    continue;
                }
                for (j = 0; j < clos; j++) {
                    XSSFRow row = rs.getRow(i);
                    XSSFCell cell = row.getCell(j);
                    Field field = classz.getDeclaredField(filedNames[j]);
                    //行数据是否为空
                    if (null == cell || cell.getCellType() == CellType.BLANK) {
                        if (field.getAnnotation(Excel.class).require()) {
                            message_empty.append((i + 1) + cloName[j] + ",");
                        }
                        continue;
                    }
                    Method method = classz.getMethod("set" + captureName(filedNames[j]), field.getType());
                    Object object = null;
                    if (!field.getAnnotation(Excel.class).dateFormat().isEmpty()) {
                        String value = cell.getStringCellValue();
                        //日期型
                        if (field.getType() == Date.class) {
                            object = DateUtil.toDate(value, field.getAnnotation(Excel.class).dateFormat());
                        }
                        //整形
                        else {
                            object = (DateUtil.toDate(value, field.getAnnotation(Excel.class).dateFormat()).getTime());
                        }
                    }
                    //单元格数据类型校验
                    else if (cell.getCellType() == CellType.NUMERIC) {
                        DecimalFormat df = new DecimalFormat("0.00");
                        DecimalFormat dfInt = new DecimalFormat("0");
                        String value = df.format(cell.getNumericCellValue());
                        if (field.getType() == String.class) {
                            //message_type.append((i + 1) + cloName[j] + ",");
                            //continue;
                            object = new DecimalFormat("0").format(cell.getNumericCellValue());
                        } else if (field.getType() == int.class || field.getType() == Integer.class) {
                            object = Integer.parseInt(dfInt.format(cell.getNumericCellValue()));
                        } else if (field.getType() == long.class || field.getType() == Long.class) {
                            object = Long.parseLong(value);
                        } else if (field.getType() == float.class || field.getType() == Float.class) {
                            object = Float.parseFloat(value);
                        } else if (field.getType() == double.class || field.getType() == Double.class) {
                            object = Double.parseDouble(value);
                        } else if (field.getType() == short.class || field.getType() == Short.class) {
                            object = Short.parseShort(value);
                        } else if (field.getType() == byte.class || field.getType() == Byte.class) {
                            object = Byte.parseByte(dfInt.format(cell.getNumericCellValue()));
                        } else {
                            object = value;
                        }
                    } else if (cell.getCellType() == CellType.STRING) {
                        if (field.getType() != String.class) {
                            message_regexp.append((i + 1) + cloName[j] + ",");
                            continue;
                        }
                        if (null != cell.getStringCellValue() && !cell.getStringCellValue().trim().isEmpty()) {
                            object = cell.getStringCellValue();
                        }
                    }
                    if (null != object) {
                        String regexp = field.getAnnotation(Excel.class).regexp();
                        if (!regexp.isEmpty()) {
                            if (!Pattern.compile(regexp).matcher(object.toString()).matches()) {
                                message_regexp.append((i + 1) + cloName[j] + ",");
                                continue;
                            }
                        }
                        method.invoke(entity, object);
                    }
                }
                list.add(entity);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel读取出错", e);
            return new ExcelResult((i + 1) + cloName[j] + "列数据格式不正确！", true);
        }
        try {
            String message = "";
            if (message_empty.length() != 0) {
                message_empty.setLength(message_empty.length() - 1);
                message += message_empty.toString() + "不能为空！";
            }
//            if(message_type.length()!=0){
//                message_type.setLength(message_type.length()-1);
//                message += message_type.toString()+"数据类型错误！";
//            }
            if (message_regexp.length() != 0) {
                message_regexp.setLength(message_regexp.length() - 1);
                message += message_regexp.toString() + "格式错误！";
            }
            return new ExcelResult(list, message, false);
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel读取出错", e);
            return new ExcelResult("导入失败", true);
        }
    }

    /**
     * 导入excel文件
     *
     * @param file     文件
     * @param classz   类
     * @param startRow 从第几行读取
     * @param mapClos  动态数据列数
     * @return
     */
    public static ExcelResult excelImport_xlsx(File file, Class<?> classz, Integer startRow, Integer mapClos) {
        int i = 0, j = 0;
        List<Object> list = null;
        StringBuilder message = null;
        try (FileInputStream inputStream = new FileInputStream(file);
             XSSFWorkbook workBook = new XSSFWorkbook(inputStream);) {
            message = new StringBuilder("");
            //读取工作表
            XSSFSheet rs = workBook.getSheetAt(0);
            int clos = rs.getRow(startRow).getPhysicalNumberOfCells();//得到所有的列
            int rows = rs.getLastRowNum() + 1;//得到所有的行
            Map<Integer, String> fieldMap = new HashMap<Integer, String>();
            StringBuilder fieldIndex = new StringBuilder("");
            for (Field field : classz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Excel.class) && field.getAnnotation(Excel.class).imp()) {
                    Excel excel = field.getAnnotation(Excel.class);
                    fieldMap.put(excel.index(), field.getName());
                }
            }
            StringBuilder stringBuffer = new StringBuilder();
            XSSFRow firstReadRow = rs.getRow(startRow);
            for (i = mapClos; i < clos; i++) {
                stringBuffer.append(firstReadRow.getCell(i).getStringCellValue() + ",");
            }
            stringBuffer.setLength(stringBuffer.length() - 1);
            String[] strings_clo = stringBuffer.toString().split(",");
            list = new ArrayList<Object>();
            for (i = startRow + 1; i < rows; i++) {
                Object entity = classz.newInstance();
                Field mapfield = classz.getDeclaredField("map");
                Method mapMethod = classz.getMethod("set" + captureName(mapfield.getName()), mapfield.getType());
                Map<String, String> map = new HashMap<>();
                //行前2列数据为空则跳过此行
                if (null == rs.getRow(i).getCell(0) && null == rs.getRow(i).getCell(1)) {
                    continue;
                }
                for (j = 0; j < clos; j++) {

                    XSSFRow row = rs.getRow(i);
                    XSSFCell cell = row.getCell(j);
                    if (j >= mapClos) {
                        map.put(strings_clo[j - mapClos], getCellValue(cell));
                        continue;
                    }
                    Field field = classz.getDeclaredField(fieldMap.get(j));
                    Method method = classz.getMethod("set" + captureName(fieldMap.get(j)), field.getType());
                    //行第一列数据是否为空
                    if (null == cell) {
                        if (!field.getAnnotation(Excel.class).require()) {
                            continue;
                        } else {
                            message.append("第" + (i + 1) + "行" + (j + 1) + "列数据不能为空！");
                        }
                    } else {
                        // cell不为空
                        Object object = null;
                        if (!field.getAnnotation(Excel.class).dateFormat().isEmpty()) {
                            String value = cell.getStringCellValue();
                            // 日期型
                            if (field.getType() == Date.class) {
                                object = DateUtil.toDate(value, field.getAnnotation(Excel.class).dateFormat());
                            }
                            // 整形
                            else {
                                object = (int) (DateUtil.toDate(value, field.getAnnotation(Excel.class).dateFormat())
                                        .getTime());
                            }
                        }
                        // 单元格数据类型校验
                        else if (cell.getCellType() == CellType.NUMERIC) {
                            DecimalFormat df = new DecimalFormat("0.00");
                            String value = df.format(cell.getNumericCellValue());
                            if (field.getType() == String.class) {
                                object = value;
                            } else if (field.getType() == int.class || field.getType() == Integer.class) {
                                DecimalFormat dfInt = new DecimalFormat("0");
                                object = Integer.parseInt(dfInt.format(cell.getNumericCellValue()));
                            } else if (field.getType() == long.class || field.getType() == Long.class) {
                                object = Long.parseLong(value);
                            } else if (field.getType() == float.class || field.getType() == Float.class) {
                                object = Float.parseFloat(value);
                            } else if (field.getType() == short.class || field.getType() == Short.class) {
                                object = Short.parseShort(value);
                            } else if (field.getType() == byte.class || field.getType() == Byte.class) {
                                object = Byte.parseByte(value);
                            } else {
                                object = value;
                            }
                        } else if (cell.getCellType() == CellType.BOOLEAN) {
                            object = cell.getBooleanCellValue();
                        } else if (cell.getCellType() == CellType.STRING) {
                            if (null != cell.getStringCellValue() && !cell.getStringCellValue().trim().isEmpty()) {
                                object = cell.getStringCellValue();
                            }
                        }
                        if (null != object) {
                            method.invoke(entity, object);
                        }
                    }
                }
                if (map.size() != 0) {
                    mapMethod.invoke(entity, map);
                }
                list.add(entity);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel读取出错", e);
            return new ExcelResult("第" + (i + 1) + "行" + (j + 1) + "列数据格式不正确！", true);
        }
        try {
            return new ExcelResult(list, message.toString(), false);
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel读取出错", e);
            return new ExcelResult("导入失败", true);
        }
    }

    /**
     * 导入excel文件
     *
     * @param file     文件
     * @param classz   类
     * @param startRow 从第几行读取
     * @param mapClos  动态数据列数
     * @return
     */
    public static ExcelResult excelImport_xls(File file, Class<?> classz, Integer startRow, Integer mapClos) {
        int i = 0, j = 0;
        List<Object> list = null;
        StringBuilder message = null;
        try (FileInputStream inputStream = new FileInputStream(file);
             // 读取工作簿
             HSSFWorkbook workBook = new HSSFWorkbook(inputStream);) {
            message = new StringBuilder("");
            //读取工作表
            HSSFSheet rs = workBook.getSheetAt(0);
            int clos = rs.getRow(startRow).getPhysicalNumberOfCells();//得到所有的列
            int rows = rs.getLastRowNum() + 1;//得到所有的行
            Map<Integer, String> fieldMap = new HashMap<Integer, String>();
            StringBuilder fieldIndex = new StringBuilder("");
            for (Field field : classz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Excel.class) && field.getAnnotation(Excel.class).imp()) {
                    Excel excel = field.getAnnotation(Excel.class);
                    fieldMap.put(excel.index(), field.getName());
                }
            }
            StringBuilder stringBuffer = new StringBuilder();
            HSSFRow firstReadRow = rs.getRow(startRow);
            for (i = mapClos; i < clos; i++) {
                stringBuffer.append(firstReadRow.getCell(i).getStringCellValue() + ",");
            }
            stringBuffer.setLength(stringBuffer.length() - 1);
            String[] strings_clo = stringBuffer.toString().split(",");
            list = new ArrayList<Object>();
            for (i = startRow + 1; i < rows; i++) {
                Object entity = classz.newInstance();
                Field mapfield = classz.getDeclaredField("map");
                Method mapMethod = classz.getMethod("set" + captureName(mapfield.getName()), mapfield.getType());
                Map<String, String> map = new HashMap<>();
                //行前2列数据为空则跳过此行
                //行前2列数据为空则跳过此行
                if ((null == rs.getRow(i).getCell(0) && null == rs.getRow(i).getCell(1))) {
                    continue;
                }
                if (rs.getRow(i).getCell(0).getCellType() == CellType.BLANK && rs.getRow(i).getCell(1).getCellType() == CellType.BLANK) {
                    continue;
                }
                for (j = 0; j < clos; j++) {
                    HSSFRow row = rs.getRow(i);
                    HSSFCell cell = row.getCell(j);
                    if (j >= mapClos) {
                        map.put(strings_clo[j - mapClos], getCellValue(cell));
                        continue;
                    }
                    Field field = classz.getDeclaredField(fieldMap.get(j));
                    Method method = classz.getMethod("set" + captureName(fieldMap.get(j)), field.getType());
                    //行第一列数据是否为空
                    //行第一列数据是否为空
                    if (null == cell || cell.getCellType() == CellType.BLANK) {
                        if (field.getAnnotation(Excel.class).require()) {
                            message.append((i + 1) + cloName[j] + ",");
                        }
                        continue;
                    }
                    Object object = null;
                    if (!field.getAnnotation(Excel.class).dateFormat().isEmpty()) {
                        String value = cell.getStringCellValue();
                        //日期型
                        if (field.getType() == Date.class) {
                            object = DateUtil.toDate(value, field.getAnnotation(Excel.class).dateFormat());
                        }
                        //整形
                        else {
                            object = (int) (DateUtil.toDate(value, field.getAnnotation(Excel.class).dateFormat()).getTime());
                        }
                    }
                    //单元格数据类型校验
                    else if (cell.getCellType() == CellType.NUMERIC) {
                        DecimalFormat df = new DecimalFormat("0.00");
                        String value = df.format(cell.getNumericCellValue());
                        if (field.getType() == String.class) {
                            object = value;
                        } else if (field.getType() == int.class || field.getType() == Integer.class) {
                            DecimalFormat dfInt = new DecimalFormat("0");
                            object = Integer.parseInt(dfInt.format(cell.getNumericCellValue()));
                        } else if (field.getType() == long.class || field.getType() == Long.class) {
                            object = Long.parseLong(value);
                        } else if (field.getType() == float.class || field.getType() == Float.class) {
                            object = Float.parseFloat(value);
                        } else if (field.getType() == short.class || field.getType() == Short.class) {
                            object = Short.parseShort(value);
                        } else if (field.getType() == byte.class || field.getType() == Byte.class) {
                            object = Byte.parseByte(value);
                        } else {
                            object = value;
                        }
                    } else if (cell.getCellType() == CellType.BOOLEAN) {
                        object = cell.getBooleanCellValue();
                    } else if (cell.getCellType() == CellType.STRING) {
                        if (null != cell.getStringCellValue() && !cell.getStringCellValue().trim().isEmpty()) {
                            object = cell.getStringCellValue();
                        }
                    }
                    if (null != object) {
                        method.invoke(entity, object);
                    }
                }
                if (map.size() != 0) {
                    mapMethod.invoke(entity, map);
                }
                list.add(entity);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel读取出错", e);
            return new ExcelResult("第" + (i + 1) + "行" + (j + 1) + "列数据格式不正确！", true);
        }
        try {
            return new ExcelResult(list, message.toString(), false);
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel读取出错", e);
            return new ExcelResult("导入失败", true);
        }
    }

    /***
     * 导出Excel
     * @param file Excel文件
     * @param obj 待导出数据（只支持列表或实体）
     * @param sheetName（Excel表Sheet名称）
     */
    @SuppressWarnings("unchecked")
    public static void excelExport_xlsx(Class<?> clazz, File file, Object obj, String sheetName) {
        //创建工作簿
        XSSFWorkbook workBook = null;
        try {
            List<Object> list;
            //传入对象是否为列表
            if (obj instanceof List) {
                list = (List<Object>) obj;
            } else {
                list = new ArrayList<Object>();
                list.add(obj);
            }
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            workBook = new XSSFWorkbook();
            createSheet(workBook, sheetName, list, clazz);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            workBook.close();
            return;
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel导出出错", e);
        }
        try {
            if (null != workBook) {
                workBook.close();
            }
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel导出出错", e);
        }
    }

    /**
     * @param clazzList
     * @param file
     * @param objList
     * @param sheetName
     */
    @SuppressWarnings("unchecked")
    public static void excelExport_xlsx(List<Class<?>> clazzList, File file, List<Object> objList, String[] sheetName) {
        XSSFWorkbook workBook = null;
        //创建工作簿
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            workBook = new XSSFWorkbook();
            for (int i = 0; i < sheetName.length; i++) {
                Object obj = objList.get(i);
                Class<?> clazz = clazzList.get(i);
                List<Object> list;
                //传入对象是否为列表
                if (obj instanceof List) {
                    list = (List<Object>) obj;
                } else {
                    list = new ArrayList<Object>();
                    list.add(obj);
                }
                createSheet(workBook, sheetName[i], list, clazz);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            workBook.close();
            return;
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel导出出错", e);
        }
        try {
            if (null != workBook) {
                workBook.close();
            }
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel导出出错", e);
        }
    }

    /***
     * 导出Excel
     * @param file Excel文件
     * @param obj 待导出数据（只支持列表或实体）
     * @param sheetName（Excel表Sheet名称）
     */
    @SuppressWarnings("unchecked")
    public static void excelExport_xls(Class<?> clazz, File file, Object obj, String sheetName) {
        //创建工作簿
        HSSFWorkbook workBook = null;
        try {
            List<Object> list;
            //传入对象是否为列表
            if (obj instanceof List) {
                list = (List<Object>) obj;
            } else {
                list = new ArrayList<Object>();
                list.add(obj);
            }
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            workBook = new HSSFWorkbook();
            createSheet(workBook, sheetName, list, clazz);
            workBook.write(new FileOutputStream(file));
            workBook.close();
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel导出出错", e);
        }
        try {
            if (null != workBook) {
                workBook.close();
            }
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel导出出错", e);
        }
    }

    /**
     * @param clazzList
     * @param file
     * @param objList
     * @param sheetName
     */
    @SuppressWarnings("unchecked")
    public static void excelExport_xls(List<Class<?>> clazzList, File file, List<Object> objList, String[] sheetName) {
        //创建工作簿
        try (HSSFWorkbook workBook = new HSSFWorkbook();) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            for (int i = 0; i < sheetName.length; i++) {
                Object obj = objList.get(i);
                Class<?> clazz = clazzList.get(i);
                List<Object> list;
                //传入对象是否为列表
                if (obj instanceof List) {
                    list = (List<Object>) obj;
                } else {
                    list = new ArrayList<Object>();
                    list.add(obj);
                }
                createSheet(workBook, sheetName[i], list, clazz);
            }
            workBook.write(new FileOutputStream(file));
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel导出出错", e);
        }
    }

    /**
     * 创建Sheet(xlsx)
     *
     * @param workBook
     * @param sheetName
     * @param list
     * @param clazz
     */
    private static void createSheet(XSSFWorkbook workBook, String sheetName, List<Object> list, Class<?> clazz) {
        //创建工作表
        XSSFSheet sheet = workBook.createSheet(sheetName);
        //单元格格式为字符（防止科学计数法）
        XSSFCellStyle strCellStyle = workBook.createCellStyle();
        strCellStyle.setDataFormat(workBook.createDataFormat().getFormat("@"));
        //单元格格式为整数
        XSSFCellStyle numCellStyle = workBook.createCellStyle();
        numCellStyle.setDataFormat(workBook.createDataFormat().getFormat("0"));
        //单元格格式为浮点数（保留2位小数）
        XSSFCellStyle floCellStyle = workBook.createCellStyle();
        floCellStyle.setDataFormat(workBook.createDataFormat().getFormat("0.00"));
        //注解@excel属性集合
        List<Field> fields = new ArrayList<Field>();
        //写入表头信息
        int col = 0;
        XSSFRow row = sheet.createRow(0);
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Excel.class) && field.getAnnotation(Excel.class).exp()) {
                Excel excel = field.getAnnotation(Excel.class);
                fields.add(field);
                XSSFCell cell = row.createCell(col++, CellType.STRING);
                cell.setCellValue(excel.name());
            }
        }
        //写入元数据
        for (int i = 0; i < list.size(); i++) {
            XSSFRow row_ = sheet.createRow(i + 1);
            for (int j = 0; j < fields.size(); j++) {
                try {
                    XSSFCell cell = row_.createCell(j, CellType.STRING);
                    Field field = fields.get(j);
                    Method method = clazz.getMethod("get" + captureName(field.getName()), new Class[]{});
                    Object value = method.invoke(list.get(i), new Object[]{});
                    Excel excelAnnotation = field.getAnnotation(Excel.class);
                    if (null == value) {
                        if(excelAnnotation.format().equals(ExcelFormat.STRING)){
                            cell.setCellStyle(strCellStyle);
                        }else if(excelAnnotation.format().equals(ExcelFormat.INT)){
                            cell.setCellStyle(numCellStyle);
                        }else if(excelAnnotation.format().equals(ExcelFormat.DOUBLE)){
                            cell.setCellStyle(floCellStyle);
                        }
                        continue;
                    }
                    //日期格式处理
                    if (!excelAnnotation.dateFormat().isEmpty()) {

                        cell.setCellStyle(strCellStyle);
                        //日期型
                        if (field.getType() == Date.class) {
                            cell.setCellValue(DateUtil.toDate((Date) value, field.getAnnotation(Excel.class).dateFormat()));
                        }
                        //整形
                        else {
                            cell.setCellValue(DateUtil.toDate(new Date(Long.parseLong(value.toString())), field.getAnnotation(Excel.class).dateFormat()));
                        }
                    }
                    //单元格数据类型校验
                    else if (field.getType() == int.class || field.getType() == Integer.class) {
                        cell.setCellStyle(numCellStyle);
                        cell.setCellValue(Integer.parseInt(value.toString()));
                    } else if (field.getType() == long.class || field.getType() == Long.class) {
                        cell.setCellStyle(numCellStyle);
                        cell.setCellValue(Long.parseLong(value.toString()));
                    } else if (field.getType() == float.class || field.getType() == Float.class) {
                        cell.setCellStyle(floCellStyle);
                        cell.setCellValue(Float.parseFloat(value.toString()));
                    } else if (field.getType() == short.class || field.getType() == Short.class) {
                        cell.setCellStyle(numCellStyle);
                        cell.setCellValue(Short.parseShort(value.toString()));
                    } else if (field.getType() == byte.class || field.getType() == Byte.class) {
                        cell.setCellStyle(numCellStyle);
                        cell.setCellValue(Byte.parseByte(value.toString()));
                    } else if (field.getType() == double.class || field.getType() == Double.class) {
                        cell.setCellStyle(floCellStyle);
                        cell.setCellValue(Double.parseDouble(value.toString()));
                    } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                        cell.setCellStyle(strCellStyle);
                        cell.setCellValue(Boolean.parseBoolean(value.toString()));
                    } else {
                        cell.setCellStyle(strCellStyle);
                        if (isENum(value.toString())) {
                            BigDecimal bigDecimal = new BigDecimal(value.toString());
                            cell.setCellValue(bigDecimal.toPlainString());
                        } else {
                            cell.setCellValue(String.valueOf(value));
                        }
                    }
                } catch (Exception e) {
                    // e.printStackTrace();
                    logger.warn("Excel导出出错", e);
                }
            }
        }
    }

    /**
     * 创建Sheet(xls)
     *
     * @param workBook  HSSFWorkbook
     * @param sheetName
     * @param list
     * @param clazz
     */
    private static void createSheet(HSSFWorkbook workBook, String sheetName, List<Object> list, Class<?> clazz) {
        try {
            //创建工作表
            HSSFSheet sheet = workBook.createSheet(sheetName);
            //单元格格式为字符（防止科学计数法）
            HSSFCellStyle strCellStyle = workBook.createCellStyle();
            strCellStyle.setDataFormat(workBook.createDataFormat().getFormat("@"));
            //单元格格式为整数
            HSSFCellStyle numCellStyle = workBook.createCellStyle();
            numCellStyle.setDataFormat(workBook.createDataFormat().getFormat("0"));
            //单元格格式为浮点数（保留2位小数）
            HSSFCellStyle floCellStyle = workBook.createCellStyle();
            floCellStyle.setDataFormat(workBook.createDataFormat().getFormat("0.00"));
            //注解@excel属性集合
            List<Field> fields = new ArrayList<Field>();
            //写入表头信息
            int col = 0;
            HSSFRow row = sheet.createRow(0);
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Excel.class) && field.getAnnotation(Excel.class).exp()) {
                    Excel excel = field.getAnnotation(Excel.class);
                    fields.add(field);
                    HSSFCell cell = row.createCell(col++, CellType.STRING);
                    cell.setCellValue(excel.name());
                }
            }
            //写入元数据
            for (int i = 0; i < list.size(); i++) {
                HSSFRow row_ = sheet.createRow(i + 1);
                for (int j = 0; j < fields.size(); j++) {
                    HSSFCell cell = row_.createCell(j, CellType.STRING);
                    Field field = fields.get(j);
                    Method method = clazz.getMethod("get" + captureName(field.getName()), new Class[]{});
                    Object value = method.invoke(list.get(i), new Object[]{});
                    if (null == value) {
                        cell.setCellStyle(strCellStyle);
                        continue;
                    }
                    //日期格式处理
                    if (!field.getAnnotation(Excel.class).dateFormat().isEmpty()) {
                        cell.setCellStyle(strCellStyle);
                        //日期型
                        if (field.getType() == Date.class) {
                            cell.setCellValue(DateUtil.toDate((Date) value, field.getAnnotation(Excel.class).dateFormat()));
                        }
                        //整形
                        else {
                            //补位
                            cell.setCellValue(DateUtil.toDate(new Date(Long.parseLong(value.toString())), field.getAnnotation(Excel.class).dateFormat()));
                        }
                    }
                    //单元格数据类型校验
                    else if (field.getType() == int.class || field.getType() == Integer.class) {
                        cell.setCellStyle(numCellStyle);
                        cell.setCellValue(Integer.parseInt(value.toString()));
                    } else if (field.getType() == long.class || field.getType() == Long.class) {
                        cell.setCellStyle(numCellStyle);
                        cell.setCellValue(Long.parseLong(value.toString()));
                    } else if (field.getType() == float.class || field.getType() == Float.class) {
                        cell.setCellStyle(floCellStyle);
                        cell.setCellValue(Float.parseFloat(value.toString()));
                    } else if (field.getType() == short.class || field.getType() == Short.class) {
                        cell.setCellStyle(numCellStyle);
                        cell.setCellValue(Short.parseShort(value.toString()));
                    } else if (field.getType() == byte.class || field.getType() == Byte.class) {
                        cell.setCellStyle(numCellStyle);
                        cell.setCellValue(Byte.parseByte(value.toString()));
                    } else if (field.getType() == double.class || field.getType() == Double.class) {
                        cell.setCellStyle(floCellStyle);
                        cell.setCellValue(Double.parseDouble(value.toString()));
                    } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                        cell.setCellStyle(strCellStyle);
                        cell.setCellValue(Boolean.parseBoolean(value.toString()));
                    } else {
                        cell.setCellStyle(strCellStyle);
                        if (isENum(value.toString())) {
                            BigDecimal bigDecimal = new BigDecimal(value.toString());
                            cell.setCellValue(bigDecimal.toPlainString());
                        } else {
                            cell.setCellValue(String.valueOf(value));
                        }
                    }
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("Excel导出出错", e);
        }
    }


    private static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    static Pattern pattern = Pattern.compile("(-?\\d+\\.?\\d*)[Ee]{1}[\\+-]?[0-9]*");

    /***
     * 字符串是否为科学计数法
     * @param input
     * @return
     */
    private static boolean isENum(String input) {
        return pattern.matcher(input).matches();
    }

    /**
     * Description :获取xlsx格式的单元格值
     *
     * @param cell
     * @return String
     */
    private static String getCellValue(Cell cell) {
        Object result = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    result = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    result = cell.getNumericCellValue();
                    break;
                case BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                default:
                    result = cell.getStringCellValue();
            }
            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * @param sheetName
     * @param @param    voList
     * @param @param    response 设定文件
     * @return void 返回类型
     * @throws
     * @Title: downLoadSafetyIndicatorExcel
     * @Description: (根据条件 ， 生成工作薄对象到内存 。)
     */
    private static XSSFWorkbook makeWorkBook(String sheetName, String[] fieldName,
                                             List<Object[]> data, List<int[]> ranges) {
        // 用来记录最大列宽,自动调整列宽。
        Integer collength[] = new Integer[fieldName.length];
        // 产生工作薄对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 产生工作表对象
        XSSFSheet sheet = workbook.createSheet(sheetName);
        // 为了工作表能支持中文,设置字符集为UTF_16
        // sheet.setSheetName(0, sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 产生一行
        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        // 设置这些样式
        // style.setFillForegroundColor(HSSFColor.CORAL.index);//back
        // style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        // style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        XSSFFont font = workbook.createFont();
        // font.setColor(HSSFColor.RED.index);
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        // 把字体应用到当前的样式
        style.setFont(font);

        XSSFCellStyle cellstyle = workbook.createCellStyle();
        cellstyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        // 设置这些样式
        // cellstyle.setFillForegroundColor(HSSFColor.CORAL.index);//back
        // cellstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        XSSFFont cellfont = workbook.createFont();
        // font.setColor(HSSFColor.RED.index);
        cellfont.setFontHeightInPoints((short) 10);
        // 把字体应用到当前的样式
        cellstyle.setFont(cellfont);

        XSSFCellStyle numberstyle = workbook.createCellStyle();
        numberstyle.setAlignment(HorizontalAlignment.LEFT); // 创建一个居中格式
        // 设置这些样式
        // cellstyle.setFillForegroundColor(HSSFColor.CORAL.index);//back
        // cellstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellstyle.setBorderBottom(BorderStyle.THIN);
        cellstyle.setBorderLeft(BorderStyle.THIN);
        cellstyle.setBorderRight(BorderStyle.THIN);
        cellstyle.setBorderTop(BorderStyle.THIN);
        // cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        // font.setColor(HSSFColor.RED.index);

        // 产生单元格
        XSSFCell cell;
        // 写入各个字段的名称
        for (int i = 0; i < fieldName.length; i++) {
            // 创建第一行各个字段名称的单元格
            cell = row.createCell(i);
            // 设置单元格内容为字符串型
            cell.setCellType(CellType.STRING);
            // 给单元格内容赋值
            cell.setCellValue(new XSSFRichTextString(fieldName[i]));
            // 初始化列宽
            collength[i] = fieldName[i].getBytes().length;
            cell.setCellStyle(style);
        }

        // 临时单元格内容
        String cellContent = "";
        // 写入各条记录,每条记录对应excel表中的一行
        for (int i = 0; i < data.size(); i++) {
            Object[] item = data.get(i);
            // 生成一行
            row = sheet.createRow(i + 1);
            for (int j = 0; j < item.length; j++) {
                cell = row.createCell(j);
                // 设置单元格字符类型为String
                cell.setCellType(CellType.STRING);
                cellContent = (item[j] == null) ? "" : item[j].toString();
                cell.setCellValue(new XSSFRichTextString(cellContent));
                cell.setCellStyle(cellstyle);

                if (cellContent != null) {
                    int len = cellContent.getBytes().length;
                    collength[j] = Math.max(collength[j], len);
                }
            }
        }

        // 设置列宽
        for (int c = 0; c < collength.length; c++) {
            sheet.setColumnWidth(c, collength[c] * 400);
        }

        // 合并单元格
        if (ranges != null && !ranges.isEmpty()) {
            XSSFCellStyle mergeStyle = workbook.createCellStyle();
            mergeStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
            mergeStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
            mergeStyle.setBorderBottom(BorderStyle.THIN);
            mergeStyle.setBorderLeft(BorderStyle.THIN);
            mergeStyle.setBorderRight(BorderStyle.THIN);
            mergeStyle.setBorderTop(BorderStyle.THIN);

            for (int[] range : ranges) {
                // * firstRow 0-based
                // * lastRow 0-based
                // * firstCol 0-based
                // * lastCol 0-based
                CellRangeAddress rangeAddress = new CellRangeAddress(range[0], range[1], range[2], range[2]);
                // 在sheet里增加合并单元格
                sheet.addMergedRegion(rangeAddress);
                XSSFCell nowCell = sheet.getRow(range[0]).getCell(range[2]);
                nowCell.setCellStyle(mergeStyle);
            }
        }

        return workbook;
    }

//    public static void main(String[] args) {
//        List<Student> students = new ArrayList<Student>();
//        for (int i = 0; i < 10; i++) {
//            Student student = new Student(i, "测试" + i, i * 10);
//            students.add(student);
//        }
//        List<Class<?>> classList = new ArrayList<>();
//        classList.add(Student.class);
//        classList.add(Student.class);
//        classList.add(Student.class);
//        List<Object> objectList = new ArrayList<>();
//        objectList.add(students);
//        objectList.add(students);
//        objectList.add(students);
//        String[] sheets = new String[]{"学生信息", "11","22"};
//        excelExport_xlsx(classList, new File("D:\\学生信息.xlsx"), objectList, sheets);
//        excelExport_xlsx(Student.class, new File("D:\\学生信息1.xlsx"), students, "11");
////        List<Student> list = (List<Student>)excelImport_xlsx(new File("D:\\学生信息.xlsx"),Student.class);
////        //JDK1.8 Lambda表达式
////        list.stream().forEach(student -> System.out.println(student.toString()));
//
//    }

    private static final String[] cloName = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
            , "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ"
    };
}
