package org.java.study.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class WorkMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("洗漱起床");
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("打开下班");
        return object;
    }
}
