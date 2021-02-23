package org.java.study.proxy.jdk;

import java.lang.reflect.Proxy;

public class JdkProxyFactory {

    public static Object getProxy(Object object) {

        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new DoThingInvocationHandler(object));
    }
}
