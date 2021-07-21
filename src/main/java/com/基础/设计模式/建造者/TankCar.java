package com.基础.设计模式.建造者;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/11 17:46
 */
public class TankCar implements Car {
    @Override
    public void name() {
        System.out.println("坦克用"+tire().getTire());
    }

    @Override
    public Tire tire() {
        return new LronTire();
    }

    @Override
    public Integer wt() {
        return 2000;
    }
}
