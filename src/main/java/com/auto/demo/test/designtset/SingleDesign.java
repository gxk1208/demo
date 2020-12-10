package com.auto.demo.test.designtset;

import com.auto.demo.test.design.SingleClass01;

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
        System.out.println(singleClass01.getName());

        SingleClass01 singleClass02 = SingleClass01.getNewInstance();
        System.out.println(singleClass02.getName());
    }
}
