package com.auto.demo.service.impl;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.auto.demo.entity.SelfEntity;
import com.auto.demo.mapper.SelfEntityMapper;
import com.auto.demo.mq.config.RepeatSendMqConfig;
import com.auto.demo.mq.config.TopicTestMqConfig;
import com.auto.demo.service.EasyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/9/17 15:55
 */
@Slf4j
@Service
public class EasyServiceImpl implements EasyService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SelfEntityMapper selfEntityMapper;


    @Override
    public String test() {
        return "1";
    }

    @Override
    public String imgCode(HttpServerRequest request, HttpServerResponse response) {

        return null;
    }


    @Override
    public void easyExport(Object test, HttpServletResponse response) throws IOException {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            arrayList.add(i+1);
        }


        // Excel表开始
        String sheetname = "申报单";
        //创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetname);

        //设置宽度
        int[] width = {10, 20, 30, 30, 30, 20};
        for (int columnIndex = 0; columnIndex < 6; columnIndex++) {
            sheet.setColumnWidth(columnIndex, width[columnIndex] * 256);
        }
        //设置样式集合
        Map<String, HSSFCellStyle> styles = addStyle(wb);

        int rowIndex;
        int colIndex;
        //添加标题行
        HSSFRow row0 = sheet.createRow(0);
        HSSFCell cell0 = row0.createCell(0);
        String cellValues0 = "物品盘点计划";
        cell0.setCellValue(cellValues0);
        row0.getCell(0).setCellStyle(styles.get("border_bottom"));
        CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(cra);

        /*HSSFRow row1 = sheet.createRow(1);
        row0.setHeight((short) (30 * 20));
        HSSFCell cell1 = row1.createCell(0);
       // cell1.setCellStyle(styles.get("title"));
        String cellValues1 = "DECALRATION OF OFFLAND/SUPPLY PROVISIONS/STORES TO CUSTOMS\n";
        cell1.setCellValue(cellValues1);
        CellRangeAddress cra1 = new CellRangeAddress(1, 1, 0, 6);
        sheet.addMergedRegion(cra1);*/

        HSSFRow row1 = sheet.createRow(1);
        for (colIndex = 0; colIndex < 6; colIndex++) {
            row1.createCell(colIndex);
            row1.getCell(colIndex).setCellStyle(styles.get("border_bottom"));
        }
        row1.getCell(0).setCellValue("项目：");
        row1.getCell(1).setCellValue("荣柏大厦");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
        row1.getCell(3).setCellValue("责任人：");
        row1.getCell(4).setCellValue("张三");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));

        HSSFRow row2 = sheet.createRow(2);
        for (colIndex = 0; colIndex < 6; colIndex++) {
            row2.createCell(colIndex);
               row2.getCell(colIndex).setCellStyle(styles.get("border_bottom"));
        }
        row2.getCell(0).setCellValue("借用点：");
        row2.getCell(1).setCellValue("T!借用点");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
        row2.getCell(3).setCellValue("盘点日期：");
        row2.getCell(4).setCellValue("2021-05-28~2021-05-30");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));

        HSSFRow row3 = sheet.createRow(3);
        for (colIndex = 0; colIndex < 6; colIndex++) {
            row3.createCell(colIndex);
             row3.getCell(colIndex).setCellStyle(styles.get("border_bottom"));
        }
        row3.getCell(0).setCellValue("序号");
        row3.getCell(1).setCellValue("物品编码");
        row3.getCell(2).setCellValue("物品名称");
        row3.getCell(3).setCellValue("RFID卡号");
        row3.getCell(4).setCellValue("状态");
        row3.getCell(5).setCellValue("丢失原因");

        for (Integer i : arrayList) {
            HSSFRow rowGoods = sheet.createRow(3+i);
            for (colIndex = 0; colIndex < 6; colIndex++) {
                rowGoods.createCell(colIndex);
                rowGoods.getCell(colIndex).setCellStyle(styles.get("border_bottom"));
            }
            rowGoods.getCell(0).setCellValue(i);
        }

        //生成文件
        String fileName = "申报单.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    private Map<String, HSSFCellStyle> addStyle(HSSFWorkbook wb) {
        @SuppressWarnings({"unchecked", "rawtypes"})
        Map<String, HSSFCellStyle> styles = new HashMap();

        //设置字体
        HSSFFont headFont = wb.createFont();
        headFont.setFontName("微软雅黑");
        headFont.setFontHeightInPoints((short) 16);
        headFont.setBold(true);
        HSSFFont bodyFont = wb.createFont();
        bodyFont.setFontName("微软雅黑");
        bodyFont.setFontHeightInPoints((short) 10);

        //标题行样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(headFont);
        style.setWrapText(true);
        style.setFillForegroundColor((short) 27);
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styles.put("title", style);

        //数据头居中样式
        style = wb.createCellStyle();
        style.setFont(bodyFont);
        style.setWrapText(true);
        style.setFillForegroundColor((short) 27);
        styles.put("header_center", style);

        //数据行居中样式
        style = wb.createCellStyle();
        style.setFont(bodyFont);
        styles.put("data_center", style);

        //数据行居中底色样式
        style = wb.createCellStyle();
        style.setFont(bodyFont);
        style.setWrapText(true);
        style.setFillForegroundColor((short) 27);
        styles.put("data_center_color", style);

        //数据行居中底色样式2
        style = wb.createCellStyle();
        style.setFont(bodyFont);
        style.setWrapText(true);
        style.setFillForegroundColor((short) 27);
        styles.put("data_center_color1", style);

        //数据行居左样式
        style = wb.createCellStyle();
        style.setFont(bodyFont);
        style.setWrapText(true);
        style.setFillForegroundColor((short) 27);
//        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styles.put("data_left", style);

        //数据行居右样式
        style = wb.createCellStyle();
        style.setFont(bodyFont);
//        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styles.put("data_right", style);
        //无边框样式
        style = wb.createCellStyle();
        style.setFont(bodyFont);
        style.setWrapText(true);
        styles.put("data_noborder", style);
        //无底边框样式
        style = wb.createCellStyle();
        style.setFont(bodyFont);
        style.setWrapText(true);
        styles.put("data_bottom", style);

        // 添加边框
        style = wb.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setWrapText(true);
        styles.put("border_bottom", style);

        return styles;
    }


    @Override
    public Integer repeat(String msg, Integer tenantId) {
        CorrelationData data = new CorrelationData(msg);
        rabbitTemplate.convertAndSend(RepeatSendMqConfig.REPEAT_EXCHANGE,RepeatSendMqConfig.REPEAT_ROUTING,msg, message -> {
            log.info("send time : {}", System.currentTimeMillis());
            return message;
        },data);
        return 1;
    }

    @Override
    public void topicTest(String message) {
        rabbitTemplate.convertAndSend(TopicTestMqConfig.TOPIC_TEST_EXCHANGE,TopicTestMqConfig.TOPIC_TEST_EXCHANGE_ROUTE,message);
    }

    @Override
    public Map<String, String> easyMap(Object test) {
        List<Map<String, String>> map =  selfEntityMapper.getEasyMap(test);
        return null;
    }
}
