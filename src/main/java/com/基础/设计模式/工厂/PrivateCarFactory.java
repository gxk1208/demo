package com.基础.设计模式.工厂;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/16 15:29
 */
public class PrivateCarFactory extends CarFactory  {

    @Override
    public Car getCar() {
        return new PrivateCar();
    }
}
