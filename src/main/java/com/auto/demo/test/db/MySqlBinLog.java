package com.auto.demo.test.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.github.shyiko.mysql.binlog.BinaryLogFileReader;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.deserialization.ChecksumType;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/7/20 14:50
 */
public class MySqlBinLog {
    public static void main(String[] args) throws IOException {
         test001();

        //test002();
    }

    private static void test002() throws IOException {
        String filePath = "E:\\";
        File binlogFile = new File(filePath);
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setChecksumType(ChecksumType.CRC32);
        BinaryLogFileReader reader = new BinaryLogFileReader(binlogFile,
                eventDeserializer);


        try {
            // 准备写入的文件名称
            /*
             * File f1 = new File("D:\\mysql-bin.000845.sql"); if
             * (f1.exists()==false){ f1.getParentFile().mkdirs(); }
             */
            FileOutputStream fos = new FileOutputStream(
                    "G:\\mysql-bin.000345.sql", true);
            for (Event event; (event = reader.readEvent()) != null; ) {
                System.out.println(event.toString());

                // 把数据写入到输出流
                fos.write(event.toString().getBytes());
            }
            // 关闭输出流
            fos.close();
            System.out.println("输入完成");
        } finally {
            reader.close();
        }
    }

    private static void test001() throws IOException {
        String filePath="D:\\DATA\\mysql-bin.000987";
        File binlogFile = new File(filePath);
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setChecksumType(ChecksumType.CRC32);
        BinaryLogFileReader reader = new BinaryLogFileReader(binlogFile, eventDeserializer);
        try {
            for (Event event; (event = reader.readEvent()) != null; ) {
                System.out.println(event.toString());
            }
        } finally {
            reader.close();
        }
    }
}
