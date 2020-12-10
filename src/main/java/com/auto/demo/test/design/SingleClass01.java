package com.auto.demo.test.design;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/10 9:09
 */
public class SingleClass01 {

    private static  SingleClass01 singleClass = null;

    private SingleClass01() {
    }

    public static SingleClass01 getNewInstance(){
        if(null == singleClass){
            singleClass = new SingleClass01();
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
