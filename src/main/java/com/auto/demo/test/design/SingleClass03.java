package com.auto.demo.test.design;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/10 14:51
 */
public class SingleClass03 {

    //双重检测机制(DCL)懒汉式 volatile有内存屏障的功能！

    private static volatile SingleClass03 singleClass03 = null;

    private String name;

    private SingleClass03(){

    }

    public static  SingleClass03 getNewInstance(){
        if(null == singleClass03){
            synchronized (SingleClass03.class){
                if(null == singleClass03){
                    singleClass03 = new SingleClass03();
                }
            }
        }
        return singleClass03;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
