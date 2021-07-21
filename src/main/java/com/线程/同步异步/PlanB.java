package com.线程.同步异步;

import lombok.SneakyThrows;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/28 16:21
 */
public class PlanB implements Callable<PlanB> {

    @Override
    public PlanB call() throws Exception {
        Thread.sleep(10000);
        System.out.println("异步调用阻塞10s"+new Date());
        return new PlanB();
    }


 /*    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(10000);
        System.out.println("22");
    }*/

    public  Integer getNum(Integer num)  throws InterruptedException {
        Thread.sleep(10000);
        return num * 2;
    }


    public  void sysoNum(Integer num){
        System.out.println(num);
    }
}
