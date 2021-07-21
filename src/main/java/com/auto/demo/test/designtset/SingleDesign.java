package com.auto.demo.test.designtset;

import com.auto.demo.test.design.*;

import java.util.Objects;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/10 8:46
 */
public class SingleDesign {
    public static void main(String[] args) throws Exception {
         //单线程懒汉式();

        //饿汉式();

       // 多线程单锁懒汉式();

       // 多线程问题();

        SingleClass05.INSTANCE.whateverMethod();

    }

    private static void 多线程问题() {

        new Thread(()->{
            SingleClass04 singleClass01 = SingleClass04.getNewInstance();
            System.out.println(singleClass01.toString());
        }).start();

        new Thread(()->{
            SingleClass04 singleClass01 = SingleClass04.getNewInstance();
            System.out.println(singleClass01.toString());
        }).start();

        new Thread(()->{
            SingleClass04 singleClass01 = SingleClass04.getNewInstance();
            System.out.println(singleClass01.toString());
        }).start();
    }

    private static void 多线程单锁懒汉式() {
        SingleClass03 singleClass03 = SingleClass03.getNewInstance();
        singleClass03.setName("懒汉式，加载类时创建对象");
        System.out.println(singleClass03);
        System.out.println(singleClass03.getName());

        SingleClass03 singleClass04 = SingleClass03.getNewInstance();
        System.out.println(singleClass04);
        System.out.println(singleClass04.getName());

        SingleClass03 singleClass05 = SingleClass03.getNewInstance();
        singleClass04.setName("懒汉式，加载类时创建对象++");
        System.out.println(singleClass05);
        System.out.println(singleClass05.getName());

        //拿到的指向堆内存的地址一样，是同一个对象
        System.out.println(Objects.equals(singleClass03,singleClass05));
    }

    private static void 饿汉式() {
        SingleClass02 singleClass02 = SingleClass02.getNewInstance();
        singleClass02.setName("饿汉式，加载类时创建对象");
        System.out.println(singleClass02);
        System.out.println(singleClass02.getName());

        SingleClass02 singleClass03 = SingleClass02.getNewInstance();
        System.out.println(singleClass03);
        System.out.println(singleClass03.getName());

        SingleClass02 singleClass04 = SingleClass02.getNewInstance();
        singleClass04.setName("饿汉式，加载类时创建对象++");
        System.out.println(singleClass04);
        System.out.println(singleClass04.getName());

        //拿到的指向堆内存的地址一样，是同一个对象
        System.out.println(Objects.equals(singleClass02,singleClass04));
    }

    private static void 单线程懒汉式() throws InterruptedException {
        //        SingleClass01 singleClass01 = new SingleClass01();
        SingleClass01 singleClass01 = SingleClass01.getNewInstance();
        singleClass01.setName("001单例模式测试");
        System.out.println(singleClass01);
        System.out.println(singleClass01.getName());
        System.out.println(Thread.currentThread().getName());

        SingleClass01 singleClass02 = SingleClass01.getNewInstance();
        System.out.println(singleClass02);
        System.out.println(singleClass02.getName());

        SingleClass01 singleClass03 = SingleClass01.getNewInstance();
        singleClass03.setName("003单例模式测试");
        System.out.println(singleClass03);
        System.out.println(singleClass03.getName());

        //拿到的指向堆内存的地址一样，是同一个对象
        System.out.println(Objects.equals(singleClass01,singleClass03));
        System.out.println(singleClass01.equals(singleClass03));

    }
}
