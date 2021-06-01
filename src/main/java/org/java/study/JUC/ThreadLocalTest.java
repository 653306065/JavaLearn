package org.java.study.JUC;

import java.util.stream.IntStream;

public class ThreadLocalTest{
    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        IntStream.range(0,10).forEach(value -> {
            new Thread(()->{
                threadLocal.set(value);
                System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
            }).start();
        });
    }
}
