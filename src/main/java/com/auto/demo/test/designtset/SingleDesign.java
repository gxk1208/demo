package com.auto.demo.test.designtset;

import com.auto.demo.test.design.SingleClass01;

import java.util.Objects;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/10 8:46
 */
public class SingleDesign {
    public static void main(String[] args) {

//        SingleClass01 singleClass01 = new SingleClass01();
        SingleClass01 singleClass01 = SingleClass01.getNewInstance();
        singleClass01.setName("001单例模式测试");
        System.out.println(singleClass01);
        System.out.println(singleClass01.getName());

        SingleClass01 singleClass02 = SingleClass01.getNewInstance();
        System.out.println(singleClass02);
        System.out.println(singleClass02.getName());

        SingleClass01 singleClass03 = SingleClass01.getNewInstance();
        singleClass03.setName("003单例模式测试");
        System.out.println(singleClass03);
        System.out.println(singleClass03.getName());

        SingleClass01 singleClass04 = SingleClass01.getNewInstance();
        System.out.println(singleClass04);
        System.out.println(singleClass01.getName());

        //拿到的指向堆内存的地址一样，是同一个对象
        System.out.println(Objects.equals(singleClass01,singleClass04));
        System.out.println(singleClass01.equals(singleClass04));
    }
}
