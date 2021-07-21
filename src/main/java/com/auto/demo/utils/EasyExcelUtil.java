package com.auto.demo.utils;

import com.alibaba.excel.EasyExcel;
import com.auto.demo.entity.SelfField;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/9 10:30
 */
public class EasyExcelUtil {
    /**
     *
     * @param response
     * @param fileds 显示的字段名,excel的列头
     * @param list 数据内容
     * @param fileName 导出的文件名
     * @throws IOException
     */
    public static void downloadFailedUsingJson(HttpServletResponse response,
                                               List<SelfField> fileds, List<Map<String, Object>> list, String fileName) throws IOException {

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        // 这里需要设置不关闭流
        EasyExcel.write(response.getOutputStream()).head(head(fileds))
                .autoCloseStream(Boolean.FALSE).sheet(fileName)
                .doWrite(dataList(list));
    }

    /**
     * 通过解析显示字段集合得到列头
     * @param fileds
     * @return
     */
    private static List<List<String>> head(List<SelfField> fileds) {
        List<List<String>> list = new ArrayList<>();

//        List<String> id = new ArrayList<>();
//        list.add(id);
        //上面两行是为了导出数据ID，不需要的可以不写
        //下面是解析字段集合获取列头
        for (SelfField layoutField : fileds) {
            List<String> head = new ArrayList<>();
            head.add(layoutField.getName());
            list.add(head);
        }

        return list;
    }

    /**
     * 通过解析所有数据将数据写入excel中，有特殊业务处理也可在这里进行
     * @param list
     * @return
     */
    private static List<List<Object>> dataList(List<Map<String, Object>> list) {
        List<List<Object>> excellist = new ArrayList<>();

        for (Map<String, Object> map : list) {
            List<Object> data = new ArrayList<Object>();
            for (String e:map.keySet()) {
                if(!("isdelete").equals(e)) {
                    data.add(map.get(e));
                }

            }
            excellist.add(data);
        }

        return excellist;
    }
}
