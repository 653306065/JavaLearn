package org.java.study.thread;

import java.util.concurrent.*;

public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            return "value";
        });
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(50));
        threadPoolExecutor.execute(futureTask);
        System.out.println(futureTask.get());

        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
