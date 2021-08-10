package com.auto.demo.test;

import com.auto.demo.utils.Md5Util;

import java.awt.*;
import java.beans.IntrospectionException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/11/19 8:27
 */
public class DateTest {
    public static void main(String[] args) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        for (int i = 0; i < 12; i++) {
            instance.set(Calendar.MONTH,i);
            System.out.println(instance.getActualMaximum(Calendar.DAY_OF_MONTH));
        }




        for (int i = 0; i < 7; i++) {
            instance.add(Calendar.DAY_OF_MONTH,1);
            System.out.println(sdf.format(instance.getTime()));
            int x = instance.get(Calendar.DAY_OF_WEEK);
            System.out.println(x);
        }


       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd");

        System.out.println(sdf.format(new Date()));*/


        //指定现在的前后任意时间段();

        //时间戳转时分();

        //  long转时分秒();

        /*long l = System.currentTimeMillis();
        long time =   Long.parseLong("2147483647");

        long转时分秒1(time);

        String s = Md5Util.md5("123");*/
       // 时间戳转时分1();

       /* String startOrEndDayOfMonth = getStartOrEndDayOfMonth(null, false);
        System.out.println(startOrEndDayOfMonth);*/
    }

    public static String getStartOrEndDayOfMonth(LocalDate today, Boolean isFirst){
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        Month month = today.getMonth();
        int length = month.length(today.isLeapYear());
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), month, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), month, length);
        }
        return resDate.toString();
    }

    private static void 时间戳转时分1() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long entryTime = sdf.parse("2021-06-15 06:17:20").getTime();
        long nowTime = sdf.parse("2021-06-15 08:32:03").getTime();
        long hours = (nowTime-entryTime)/(3600*1000);
        long minuter = (nowTime-entryTime-(hours*3600*1000))/(60*1000)+1;
        System.out.println(String.format("%s时%s分",hours,minuter));


        long hours1 = (8083)/(3600);
        long minuter1 = (8083-(hours1*3600))/(60)+1;

        System.out.println(String.format("%s时%s分",hours1,minuter1));
    }

    private static void 指定现在的前后任意时间段() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String endTime = sdf.format(date);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MINUTE, -5);
        String startTime = sdf.format(instance.getTime());
        System.out.println(startTime);
        System.out.println(endTime);
    }

    private static void 时间戳转时分() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        System.out.println(sdf.format(new Date()));

        long l = 1860000L;
        long a = l/(3600*1000);
        long b = (l-a*(3600*1000))/(60*1000);
        System.out.println(a +"---"+ b);
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

        long time=1860L;
        hh = String.format("%02d",time/3600);

        long mm1=(time- Integer.parseInt(hh) *3600)/60;
        mm = String.format("%02d",mm1);

        long ss1=(time-Integer.parseInt(hh)*3600-Integer.parseInt(mm)*60);
        ss=String.format("%02d",ss1);

        System.out.println(String.format("共计: %s小时 %s分钟 %s秒",hh,mm,ss));
    }
}
