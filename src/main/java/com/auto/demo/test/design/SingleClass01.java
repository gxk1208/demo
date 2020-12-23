package com.auto.demo.test.design;

import lombok.SneakyThrows;

import java.util.Objects;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/10 9:09
 */
public class SingleClass01 {

    //单线程  懒汉式，用到对象的时候才去创建

    private static  SingleClass01 singleClass = null;
    private static int i = 0;


    private SingleClass01() {
    }

    @SneakyThrows
    public static SingleClass01 getNewInstance()  {
        if(null == singleClass){
            synchronized (SingleClass01.class){
                if(null == singleClass){
                    long l = System.currentTimeMillis();
                    i++;
                    System.out.println(l+"---"+i);
                    singleClass = new SingleClass01();
                    long e = System.currentTimeMillis();
                    System.out.println(e);
                    long time = e-l;
                    System.out.println(time);
                }
            }

        }
        return singleClass;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
