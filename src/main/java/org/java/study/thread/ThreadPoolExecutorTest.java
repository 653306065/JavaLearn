package org.java.study.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

	public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 50, 1000, TimeUnit.MICROSECONDS,
			new ArrayBlockingQueue<Runnable>(50), new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r);
				}
			}, new RejectedExecutionHandler() {

				@Override
				public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
					System.out.println(r);
				}

			});

	public static void main(String[] args) {
		for(int i=0;i<1000;i++) {
			threadPoolExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("run");
				}
			});
		}
	}

}
