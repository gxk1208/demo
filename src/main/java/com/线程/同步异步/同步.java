package com.线程.同步异步;

import io.swagger.models.auth.In;

import java.util.Date;
import java.util.concurrent.FutureTask;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/28 15:53
 */
public class 同步 {

    public static void main(String[] args) throws Exception {
        //同步();

        //异步();
    }

    private static void 异步() throws InterruptedException, java.util.concurrent.ExecutionException {
        PlanB planB = new PlanB();
        FutureTask<PlanB> futureTask = new FutureTask<>(planB);
        System.out.println("调用阻塞函数"+new Date());
        new Thread(futureTask).start();
        System.out.println("阻塞结束"+new Date());
        //调用get方法依旧会进入阻塞状态
        PlanB planB1 = futureTask.get();
        System.out.println("planB" +planB1);

        System.out.println("执行结束"+new Date());
    }

    private static void 同步() throws InterruptedException {
        PlanA planA = new PlanA();
        System.out.println("调用阻塞函数"+new Date());
        Integer num = planA.getNum(1);
        System.out.println("阻塞结束"+new Date());
        planA.sysoNum(num);
        System.out.println("执行结束"+new Date());
    }
}
