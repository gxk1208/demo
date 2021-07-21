package com.auto.demo.helper;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

public class HttpHelper {

    private HttpHelper() {
    }

    /**
     * 导出文件
     *
     * @param response 请求头信息
     * @param file     文件
     * @param fileName 文件名称
     */
    public static void responseFile(HttpServletResponse response, File file, String fileName) {
        FileInputStream fis = null;
        response.reset();
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            OutputStream out = response.getOutputStream();
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            response.setContentLength(fis.available());
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            file.delete();
        }
    }


}
