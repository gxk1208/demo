package com.基础.设计模式.建造者;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/11 17:43
 */
public class LronTire implements Tire {
    @Override
    public String getTire() {
        String str = "铁轮胎";
        System.out.println(str);
        return str;
    }
}
