package com.auto.demo.test.design;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/10 14:51
 */
public class SingleClass02 {

    //饿汉式

    private static final SingleClass02 SINGLE_CLASS02 = new SingleClass02();

    private String name;

    private SingleClass02(){

    }

    public static SingleClass02 getNewInstance(){
        return SINGLE_CLASS02;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
