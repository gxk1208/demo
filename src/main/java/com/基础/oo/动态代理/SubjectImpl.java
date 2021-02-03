package com.基础.oo.动态代理;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/2/2 8:52
 */
public class SubjectImpl implements Subject {
    @Override
    public String say() {
        System.out.println("say");
        return "success";
    }
}
