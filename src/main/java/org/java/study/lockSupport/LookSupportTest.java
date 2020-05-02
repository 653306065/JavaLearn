package org.java.study.lockSupport;

import java.util.concurrent.locks.LockSupport;

public class LookSupportTest {

	public static void main(String[] args) {
		Thread thread=new Thread() {
			public void run() {
				System.out.println("调用park之前");
				LockSupport.park();
				System.out.println("调用park之后");
			}
		};
		thread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LockSupport.unpark(thread);
	}
}
