package org.java.study.feature.JDK8;

public interface DefaultMethods {

    void work();

    default void play(){
        System.out.println("play");
    }
}
