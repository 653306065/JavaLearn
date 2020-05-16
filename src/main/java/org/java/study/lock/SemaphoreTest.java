package org.java.study.lock;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	public static Semaphore semaphore=new Semaphore(10);
	
	public static void main(String[] args) {
		for(int i=0;i<100;i++) {
			Thread thread=new Thread() {
				public void run() {
					try {
						semaphore.acquire();
						System.out.println(getName());
						sleep(10000);
						semaphore.release();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();
		}
	}
}
