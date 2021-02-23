package org.java.study.proxy.jdk;

public class DoThingImpl implements DoThing {

    public void work() {
        System.out.println("好好工作");
    }

    @Override
    public void play(String game) {
        System.out.println("好好玩"+game);
    }
}
