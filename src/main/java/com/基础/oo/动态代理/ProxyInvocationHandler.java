package com.基础.oo.动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/2/2 8:52
 */
public class ProxyInvocationHandler implements InvocationHandler {

    private Object target;

    ProxyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(" 🧱 🧱 🧱 进入代理调用处理器 ");
        return method.invoke(target, args);
    }
}
