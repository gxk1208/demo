package com.auto.demo.test;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/23 15:15
 */
public class NumberTest {

    public static void main(String[] args) {

        /*System.out.println(0.1+0.2);
        System.out.println(1.0-0.8);
        System.out.println(4.015*100);
        System.out.println(123.3/100);*/


        System.out.println(get(8));;

    }

    public static Integer get(Integer n){
        if (n <= 2) {
            return n;
        } else {
            return get(n - 1) + get(n - 2);
        }
    }
}
