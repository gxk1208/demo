package com.auto.demo.test;

import com.auto.demo.utils.Md5Util;

import java.beans.IntrospectionException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/11/19 8:27
 */
public class DateTest {
    public static void main(String[] args) {

       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd");

        System.out.println(sdf.format(new Date()));*/


        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        System.out.println(sdf.format(new Date()));

        //long转时分秒();

        /*long l = System.currentTimeMillis();
        long time =   Long.parseLong("2147483647");

        long转时分秒1(time);

        String s = Md5Util.md5("123");*/


    }

    private static void long转时分秒1(long time) {
        time = time/1000;
        long hh = time/3600;
        long mm = (time-hh*3600)/60;
        long ss = time - hh*3600 - mm*60;

        StringBuilder str = new StringBuilder();
        str.append(String.format("%02d",hh)).append(":").append(String.format("%02d",mm)).append(":").append(String.format("%02d",ss));
        System.out.println(str.toString());
    }

    private static void long转时分秒() {
        String hh="00";
        String mm="00";
        String ss="00";

        long time=1600L;
        hh = String.format("%02d",time/3600);

        long mm1=(time- Integer.parseInt(hh) *3600)/60;
        mm = String.format("%02d",mm1);

        long ss1=time-Integer.parseInt(hh)*3600-Integer.parseInt(hh)*60;
        ss=String.format("%02d",ss1);

        System.out.println("共计:${hh}小时 ${mm}分钟 ${ss}秒");
    }
}
