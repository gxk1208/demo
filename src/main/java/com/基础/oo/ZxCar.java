package com.基础.oo;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/11 8:58
 */
public class ZxCar implements Car {

    private String name = "zxc";

    public ZxCar() {

    }

    @Override
    public void run() {
        System.out.println("zxc跑起来了");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
