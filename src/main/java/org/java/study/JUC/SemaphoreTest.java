package org.java.study.JUC;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore=new Semaphore(10);
        IntStream.range(0,10000).forEach(value->{
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(value);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });

    }
}
