package org.java.study.proxy.jdk;

public class Run {
    public static void main(String[] args) {
        //旧版本jdk保存动态代理生成的class idea的工作空间下的 com\sun\proxy
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //新版本jdk保存动态代理生成的class idea的工作空间下的 com\sun\proxy
        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        DoThingImpl doThingImpl = new DoThingImpl();
        DoThing doThing = (DoThing) JdkProxyFactory.getProxy(doThingImpl);
        doThing.work();
        doThing.play("英雄联盟");
    }
}
