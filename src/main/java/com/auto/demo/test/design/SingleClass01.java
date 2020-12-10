package com.auto.demo.test.design;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SingleClass01 that = (SingleClass01) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
