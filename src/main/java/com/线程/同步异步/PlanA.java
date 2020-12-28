package com.线程.同步异步;

import ch.qos.logback.core.net.SyslogOutputStream;

import javax.swing.plaf.synth.SynthSpinnerUI;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/28 15:53
 */
public class PlanA {

    public  Integer getNum(Integer num) throws InterruptedException {
        Thread.sleep(10000);
        return num * 2;
    }

    public  void sysoNum(Integer num){
        System.out.println(num);
    }
}
