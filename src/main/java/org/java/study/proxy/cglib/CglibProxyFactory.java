package org.java.study.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class CglibProxyFactory {

    public static <T> T getProxy(Class<T> clazz){
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new WorkMethodInterceptor());
        // 创建代理类
        return (T)enhancer.create();
    }
}
