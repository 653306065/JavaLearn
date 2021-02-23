package org.java.study.proxy.jdk;

public class Run {
    public static void main(String[] args) {
        DoThingImpl doThingImpl=new DoThingImpl();
        DoThing doThing= (DoThing) JdkProxyFactory.getProxy(doThingImpl);
        doThing.work();
        doThing.play("英雄联盟");
    }
}
