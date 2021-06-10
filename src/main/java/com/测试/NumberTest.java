package com.测试;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/6/4 10:43
 */
public class NumberTest {
    public static void main(String[] args) {
        BigDecimal multiply = new BigDecimal(20000).multiply(new BigDecimal("0.8"));
        BigDecimal bigDecimal = multiply.setScale(0, RoundingMode.UP);
        int i = multiply.intValue();
        System.out.println(multiply);
        System.out.println(bigDecimal);
        System.out.println(i);
    }
}
