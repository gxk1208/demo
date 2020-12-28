package com.auto.demo.test;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/10/15 14:03
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
        //FILE类 IO类
        File files = new File("E:\\创建目录");

        //创建目录与文件(files);

        //获取文件名(files);

        //复制文件(files);

        //文本写入(files);

        //文本读取();

        //文本删除();

        File file = new File("E:\\WindowsApps");
        file.delete();



    }

    private static void 文本删除() {
        File file = new File("E:\\迅雷下载");

        File[] files1 = file.listFiles();
        for (File file1 : files1) {
            if(!file1.isDirectory()){
                String name = file1.getName();
                String[] split = name.split("\\.");
                if(split[1].equals("xlsx")){
                    file1.delete();
                }
            }
        }
    }

    private static void 文本读取() throws IOException {
        File file = new File("E:\\创建目录\\20201030.docx");
        FileInputStream in = new FileInputStream(file);
        byte[] b = new byte[1024];
        int len = 0;
        String s = "";
        while ((len = in.read(b)) != -1){
            s = new String(b,0,len);
            System.out.println(s);
        }
        in.close();

        File file1 = new File("E:\\创建目录\\20201030.doc");
        FileOutputStream out = new FileOutputStream(file1,true);
        out.write(s.getBytes());
    }

    private static void 文本写入(File files) throws IOException {
        File file = new File("E:\\创建目录\\20201030");
        files.mkdirs();
        file.createNewFile();

        FileOutputStream out = new FileOutputStream(file);
        String str = "AABBCCDD你好！！!";
        out.write(str.getBytes());
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
