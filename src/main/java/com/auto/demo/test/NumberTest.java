package com.auto.demo.test;

import com.auto.demo.entity.ServrMonitor.Sys;
import com.auto.demo.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/23 15:15
 */
public class NumberTest {

    public static void main(String[] args) {

        //System.out.println(get(8));

        //bigDecimalTest();

        //compareTo方法();

        uuid01(6);

        //uuid02();


    }

    private static void uuid02() {
        int i = 3;

        String uuid02 = UUIDUtil.getUUID()+System.currentTimeMillis();
        Long num = 1L;
        while (!uuid02.substring(0,i).equals(StringUtils.reverse(uuid02.substring(uuid02.length() - i)))){
            num++;
            uuid02 = UUIDUtil.getUUID()+System.currentTimeMillis();
        }
        System.out.println(num);
        System.out.println(uuid02);
    }

    private static void uuid01(int i) {

        long s = System.currentTimeMillis();

        System.out.println(s);

        String uuid01 = UUIDUtil.getUUID().substring(0,i);
        Long num = 0L;

        while (!UUIDUtil.getUUID().substring(0,i).equals(uuid01)){
            num++;
            if(num>10000000){
                System.out.println("1kw");
                long e = System.currentTimeMillis();

                System.out.println(e);
                System.out.println(e-s);
                return;
            }
        }

        System.out.println(num);
        System.out.println(uuid01);

        long e = System.currentTimeMillis();
        System.out.println(e);

        System.out.println(e-s);
    }

    private static void compareTo方法() {

        BigDecimal number1 = new BigDecimal("10.0");
        BigDecimal number2 = new BigDecimal("10.000");


        System.out.println(number1.equals(number2));

        System.out.println(number1.compareTo(number2));

        System.out.println(new BigDecimal("1").compareTo(new BigDecimal("1.0")));

        //java中的compareto方法，返回参与比较的前后两个字符串的asc码的差值

        //两个字符串首字母不同，则该方法返回首字母的asc码的差值
        String a = "a";
        String b = "b";
        //参与比较的两个字符串如果首字符相同，则比较下一个字符，直到有不同的为止，返回该不同的字符的asc码差值
        //两个字符串不一样长，可以参与比较的字符又完全一样，则返回两个字符串的长度差值
        String c = "abc";
        String d = "abcdefgh";

        System.out.println(a.compareTo(b));

        System.out.println(c.compareTo(d));
    }

    private static void bigDecimalTest() {
        System.out.println(0.1+0.2);
        System.out.println(1.0-0.8);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
        System.out.println("--------------");

        System.out.println(new BigDecimal(0.1).add(new BigDecimal(0.2)));
        System.out.println(new BigDecimal(1.0).subtract(new BigDecimal(0.8)));
        System.out.println(new BigDecimal(4.015).multiply(new BigDecimal(100)));
        System.out.println(new BigDecimal(123.3).divide(new BigDecimal(100)));
        System.out.println("--------------");

        System.out.println(new BigDecimal("0.1").add(new BigDecimal("0.2")));
        System.out.println(new BigDecimal("1.0").subtract(new BigDecimal("0.8")));
        System.out.println(new BigDecimal("4.015").multiply(new BigDecimal("100")));
        System.out.println(new BigDecimal("123.3").divide(new BigDecimal("100")));
    }

    public static Integer get(Integer n){
        if (n <= 2) {
            return n;
        } else {
            return get(n - 1) + get(n - 2);
        }
    }
}
