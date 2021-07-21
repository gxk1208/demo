package com.基础.设计模式.建造者;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/11 17:49
 */
public class Drive {
   private List<Car> cars = new ArrayList<Car>();

   public void addCar(Car car){
       cars.add(car);
   }

   public Integer getWt(){
       Integer wt = 0;
       for (Car car : cars) {
           wt += car.wt();
       }

       return wt;
   }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
