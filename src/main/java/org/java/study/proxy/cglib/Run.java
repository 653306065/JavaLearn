package org.java.study.proxy.cglib;

public class Run {
    public static void main(String[] args) {
        Work work= CglibProxyFactory.getProxy(Work.class);
        work.doWork();
    }
}
