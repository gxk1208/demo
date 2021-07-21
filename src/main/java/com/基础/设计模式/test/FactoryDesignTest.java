package com.基础.设计模式.test;

import com.基础.设计模式.工厂.BusFactory;
import com.基础.设计模式.工厂.Car;
import com.基础.设计模式.工厂.CarFactory;
import com.基础.设计模式.工厂.PrivateCarFactory;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/16 15:30
 */
public class FactoryDesignTest {
    public static void main(String[] args) {
        CarFactory factory = new BusFactory();
        Car car = factory.getCar();
        car.say();
        factory = new PrivateCarFactory();
        car = factory.getCar();
        car.say();

    }
}
