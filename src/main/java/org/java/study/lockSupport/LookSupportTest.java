package org.java.study.lockSupport;

import java.util.concurrent.locks.LockSupport;

public class LookSupportTest {

	public static void main(String[] args) {
		Thread thread= new Thread(() -> {
			System.out.println(Thread.currentThread().getName()+",调用park之前");
			LockSupport.park();
			System.out.println(Thread.currentThread().getName()+",调用park之后");
		});
		thread.start();
		try {
			System.out.println(Thread.currentThread().getName()+",主线程休眠");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LockSupport.unpark(thread);
	}
}
