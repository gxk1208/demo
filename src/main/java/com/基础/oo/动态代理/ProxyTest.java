package com.基础.oo.动态代理;


import java.lang.reflect.Proxy;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/2/2 8:53
 */
public class ProxyTest {
    public static void main(String[] args) {
        Subject subject = new SubjectImpl();
        Subject proxy = (Subject) Proxy.newProxyInstance(
                Subject.class.getClassLoader(),
                Subject.class.getInterfaces(),
                new ProxyInvocationHandler(subject)
        );

        proxy.say();

    }
}
