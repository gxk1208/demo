package com.基础.oo;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/11 8:59
 */
public class MtCar extends ZxCar implements Car {

    private int age = 9;

    public MtCar(Integer age) {
        super();
        this.age = age;
    }

    public MtCar() {
    }

    @Override
    public void run() {
        System.out.println("mtc跑起来了");
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
