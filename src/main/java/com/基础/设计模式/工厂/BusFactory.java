package com.基础.设计模式.工厂;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/16 15:25
 */
public class BusFactory extends CarFactory {

    @Override
    public Car getCar() {
        return new Bus();
    }
}
