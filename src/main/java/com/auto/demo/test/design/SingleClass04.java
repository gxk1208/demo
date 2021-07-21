package com.auto.demo.test.design;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.ehcache.impl.internal.store.offheap.LazyOffHeapValueHolder;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/10 16:37
 */

public class SingleClass04 {

    //静态内部类懒汉式

    private SingleClass04() {

    }

    private static class LazyHolder{
        private  static final  SingleClass04 SINGLE_CLASS04 = new SingleClass04();
    }

    public static  SingleClass04 getNewInstance(){
        return LazyHolder.SINGLE_CLASS04;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
