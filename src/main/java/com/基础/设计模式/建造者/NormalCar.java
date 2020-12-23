package com.基础.设计模式.建造者;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/11 17:44
 */
public class NormalCar implements Car {
    @Override
    public void name() {
        System.out.println("正常车用"+tire().getTire());
    }

    @Override
    public Tire tire() {
        return new RubberTire();
    }

    @Override
    public Integer wt() {
        return 2000;
    }


}
