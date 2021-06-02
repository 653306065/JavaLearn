package org.java.study.blockingQueue;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class BlockingQueueTest {

    public static void main(String[] args) {
        //数组阻塞队列
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(10);
        IntStream.range(0, 10).forEach(value -> {
            new Thread(() -> {
                try {
                    arrayBlockingQueue.put(UUID.randomUUID().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        new Thread(() -> {
            while (true) {
                try {
                    String value = arrayBlockingQueue.take();
                    System.out.println(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        DelayQueue<DelayObject> delayQueue = new DelayQueue<DelayObject>();

        new Thread(() -> {
            IntStream.range(0, 10).forEach(value -> {
                delayQueue.put(new DelayObject(1000L));
            });
        }).start();

        new Thread(() -> {
            IntStream.range(0, 10).forEach(value -> {
                try {
                    System.out.println(delayQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    public static class DelayObject implements Delayed {

        private Long time;

        public DelayObject(Long time) {
            this.time = time;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return 0;
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }
}
