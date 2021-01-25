package com.auto.demo.test;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/1/22 15:18
 */

public class LeetCodeTest {
    public static void main(String[] args) {
        int reverse = reverse1(-2147483412);
        System.out.println(reverse);

    }

    public static int reverse(int x) {
        if(-1<x && x<10){
            return x;
        }
        if(-232 > x || 230 < x){
            return 0;
        }
        String[] split = String.valueOf(x).split("");

        String str = "";
        if("-".equals(split[0])){
            // 负数
            str = "-";
            for (int i = split.length-1; i > 0 ; i--) {
                str = str+split[i];
            }
        }else{
            // 正数
            for (int i = split.length-1; i >= 0 ; i--) {
                str = str+split[i];
            }
        }
        Long aLong = new Long(str);
        // 大于等于Integer的正整数的最大值返回0
        if (aLong > Integer.MAX_VALUE/10 || (aLong == Integer.MAX_VALUE / 10 && x%10 > 7)){
            return 0;
        }
        // 小于等于Integer的负整数的最大值返回0
        if (aLong < Integer.MIN_VALUE/10 || (aLong == Integer.MIN_VALUE / 10 && x%10 < -8)){
            return 0;
        }
        return  aLong.intValue();
    }

    public static int reverse1(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            // 以下等于 x = x/10;
            x /= 10;
            // 大于等于Integer的正整数的最大值返回0
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)){
                return 0;
            }
            // 小于等于Integer的负整数的最大值返回0
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)){
                return 0;
            }

            rev = rev * 10 + pop;
        }
        return rev;

    }

}
