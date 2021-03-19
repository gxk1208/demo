package com.测试;

import com.auto.demo.entity.SelfField;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/2/20 8:54
 */
public class ClassTest {
    public static void main(String[] args) throws Exception {




        long l1 = new Long("1615859740666");
        long l2 = System.currentTimeMillis();
        if(l2 -l1 > 1800000){
            System.out.println("long类型减");
        }
        System.out.println(l2 - l1);

        SelfField field = new SelfField();
        field.setEntityId(2);
        SelfField field1 = new SelfField();
        field1.setEntityId(2);
        boolean equals1 = Objects.equals(field.toString(), field1.toString());
        boolean equals = Objects.equals(field, field1);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Period period = Period.between(LocalDate.parse("2020-03-15"), LocalDate.parse("2021-03-17"));

        StringBuffer sb = new StringBuffer();
        sb.append(period.getYears()).append(",")
                .append(period.getMonths()).append(",")
                .append(period.getDays());

        System.out.println(sb.toString());

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time = calendar.getTime();



        Date date = sdf.parse("2021-03-01");
        date = new Date();
        String format = sdf.format(date);
        Date parse = sdf.parse(format);
        System.out.println(format.equals("2021-03-01"));

        //equalsNULL();
    }

    private static void equalsNULL() {
        String str = new String();
        Class clazz=str.getClass();
        System.out.println(clazz.getName());
        System.out.println(clazz.getSimpleName());

        String s = "zz";
        String a = null;

        System.out.println(a.equals(a));

        String name = "";

        File file;


    }
}
