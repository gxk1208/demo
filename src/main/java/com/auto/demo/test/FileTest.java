package com.auto.demo.test;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.auto.demo.entity.SelfEntity;
import com.auto.demo.entity.SelfField;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/10/15 14:03
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
        //FILE类 IO类
        File files = new File("E:\\创建目录");

//        创建目录与文件(files);

        //获取文件名(files);

        //复制文件(files);

      文本写入(files);

         文本读取();

     //   文本删除();

        File file = new File("E:\\WindowsApps");
        file.delete();
        // 根据url获取文件();


    }

    private static void 根据url获取文件() throws IOException {
        //根据url获取文件
        URL url =  new  URL("https://smart-park-saas-files.oss-cn-beijing.aliyuncs.com/2021/1/52dda97d-5fb3-4e58-ad1c-97eab3512f32.jpg");
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        //设置是否要从 URL 连接读取数据,默认为true
        uc.setDoInput( true );
        uc.connect();
        InputStream inputstream = uc.getInputStream();
        MultipartFile media = new MockMultipartFile("temp.jpg","temp.jpg","", inputstream);

        // 获取文件名
        String fileName = media.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        final File excelFile = File.createTempFile(UUID.randomUUID().toString(), prefix);
        // MultipartFile to File
        media.transferTo(excelFile);
        byte[] bytes = media.getBytes();
        System.out.println(bytes.length);
        long length = excelFile.length();
        System.out.println(length);
        BufferedImage sourceImg = ImageIO.read(new FileInputStream(excelFile));
        System.out.println(String.format("%.1f",excelFile.length()/1024.0));
    }

    private static void 文本删除() {
        File file = new File("E:\\迅雷下载");

        File[] sonFiles = file.listFiles();
        assert sonFiles != null;
        for (File sonFile: sonFiles) {
            if(!sonFile.isDirectory()){
                String name = sonFile.getName();
                String[] split = name.split("\\.");
                if(split.length>1){
                    if("xlsx".equals(split[1])){
                        sonFile.delete();
                    }
                }
            }
        }
    }

    private static void 文本读取() throws IOException {
        File file = new File("E:\\创建目录\\20201030.p12");
        FileInputStream in = new FileInputStream(file);
        byte[] b = new byte[1024];
        int len = 0;
        String s = "";
        while ((len = in.read(b)) != -1){
            s = new String(b,0,len);
            SelfEntity selfEntity = JSON.parseObject(s, SelfEntity.class);
            System.out.println(s);
        }
        in.close();

    }

    private static void 文本写入(File files) throws IOException {
        File file = new File("E:\\创建目录\\20201030.p12");
        files.mkdirs();
        file.createNewFile();

        FileOutputStream out = new FileOutputStream(file);
      //  String str = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJgHMGYsspghvP+yCbjLG43CkZuQ3YJyDcmEKxvmgblITfmiTPx2b9Y2iwDT9gnLGExTDm1BL2A8VzMobjaHfiCmTbDctu680MLmpDDkVXmJOqdlXh0tcLjhN4+iDA2KkRqiHxsDpiaKT6MMBuecXQbJtPlVc1XjVhoUlzUgPCrvAgMBAAECgYAV9saYTGbfsdLOF5kYo0dve1JxaO7dFMCcgkV+z2ujKtNmeHtU54DlhZXJiytQY5Dhc10cjb6xfFDrftuFcfKCaLiy6h5ETR8jyv5He6KH/+X6qkcGTkJBYG1XvyyFO3PxoszQAs0mrLCqq0UItlCDn0G72MR9/NuvdYabGHSzEQJBAMXB1/DUvBTHHH4LiKDiaREruBb3QtP72JQS1ATVXA2v6xJzGPMWMBGQDvRfPvuCPVmbHENX+lRxMLp39OvIn6kCQQDEzYpPcuHW/7h3TYHYc+T0O6z1VKQT2Mxv92Lj35g1XqV4Oi9xrTj2DtMeV1lMx6n/3icobkCQtuvTI+AcqfTXAkB6bCz9NwUUK8sUsJktV9xJN/JnrTxetOr3h8xfDaJGCuCQdFY+rj6lsLPBTnFUC+Vk4mQVwJIE0mmjFf22NWW5AkAmsVaRGkAmui41Xoq52MdZ8WWm8lY0BLrlBJlvveU6EPqtcZskWW9KiU2euIO5IcRdpvrB6zNMgHpLD9GfMRcPAkBUWOV/dH13v8V2Y/Fzuag/y5k3/oXi/WQnIxdYbltad2xjmofJ7DbB7MJqiZZD8jlr8PCZPwRNzc5ntDStc959";
        String str ="1";
        SelfField field = new SelfField();
        field.setEntityId(1);
        field.setType(1);
        field.setName("111");
        out.write(JSON.toJSONBytes(field));
        out.flush();
        out.close();
    }

    private static void 复制文件(File files) {
        //创建目标目录。目标目录路径不应在源文件目录路径下
        File text = new File("E:\\创建目录分类\\text");
        File docx = new File("E:\\创建目录分类\\docx");
        File xlsx = new File("E:\\创建目录分类\\xlsx");
        File xlsl = new File("E:\\创建目录分类\\xlsl");
        File other = new File("E:\\创建目录分类\\other");
        text.mkdirs();
        docx.mkdirs();
        xlsx.mkdirs();
        xlsl.mkdirs();
        other.mkdirs();

        File[] subFiles = files.listFiles();
        for (File subFile : subFiles) {
            if(subFile.isDirectory()){
                复制文件(subFile);
            }else{
                String fileName = subFile.getName();
                String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
                if("txt".equals(suffixName)){
                    FileUtil.copy(subFile,text,true);
                }else if("docx".equals(suffixName)){
                    FileUtil.copy(subFile,docx,true);
                }else if("xlsx".equals(suffixName)){
                    FileUtil.copy(subFile,xlsx,true);
                }else if("xlsl".equals(suffixName)){
                    FileUtil.copy(subFile,xlsl,true);
                }else{
                    FileUtil.copy(subFile,other,true);
                }

            }
        }
    }

    private static void 获取文件名(File files) {
        File[] subFiles = files.listFiles();
        for (File subFile : subFiles) {
            //获取文件名
            String name = subFile.getName();
            if(name.contains(".")){
                //文件名(不包含文件扩展类型)
                String prefixName = name.substring(0,name.lastIndexOf("."));
                System.out.println(prefixName);

                //文件后缀
                String suffixName = name.substring(name.lastIndexOf(".") + 1);
                System.out.println(suffixName);

            }
        }
    }

    private static void 创建目录与文件(File files) throws IOException {

        if(!files.exists()){
            files.mkdirs();
        }

        File file = new File("E:\\创建目录\\创建文件.xlsx");
        file.createNewFile();
    }
}
