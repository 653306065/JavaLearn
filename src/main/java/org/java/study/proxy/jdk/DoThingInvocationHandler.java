package org.java.study.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DoThingInvocationHandler implements InvocationHandler {

    private  Object target;

    public DoThingInvocationHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("满血复活");
        Object value = method.invoke(target,args);
        System.out.println("好好休息");
        return value;
    }
}
