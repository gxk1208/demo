package com.auto.demo.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/5/14 14:18
 */

public class RegTest {
    public static void main(String[] args) {

        //金额正则校验();
    }

    private static void 金额正则校验() {
        // 金额正则校验
        String reg_money = "\\d+(\\.\\d{1,2})?";// 金额正则,可以没有小数，小数最多不超过两位
        Pattern pattern = Pattern.compile(reg_money);
        Matcher matcher = pattern.matcher("-5566");
        boolean ismatch = matcher.matches();
        System.out.println(ismatch);
    }
}
