package com.基础.oo;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/11 8:57
 */
public class CarRun {
    public static void main(String[] args) {

        ZxCar zxCar = new ZxCar();

        System.out.println(zxCar.getName());

        ZxCar mtCar = new MtCar(11);
        mtCar.setName("mtc");

        System.out.println(mtCar.getName());
        MtCar mtCar1 = (MtCar)mtCar;
        System.out.println(mtCar1.getAge());
    }
}
