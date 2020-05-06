package org.java.study.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

	private static ReentrantLock fairReentrantLock = new ReentrantLock(true);

	private static ReentrantLock noFairReentrantLock = new ReentrantLock();

	public static void main(String[] args) {
		/**
		 * 公平锁
		 */
		for (int i = 0; i < 100; i++) {
			final long s=i;
			new Thread("thread-" + i) {
				public void run() {
					try {
						sleep(s);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fairReentrantLock.lock();
					System.out.println(getName()+",获取锁");
					fairReentrantLock.unlock();
					System.out.println(getName()+",释放取锁");
				}
			}.start();
		}
		
		/**
		 * 非公平锁
		 */
		for (int i = 0; i < 100; i++) {
			new Thread("thread-" + i) {
				public void run() {
					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					noFairReentrantLock.lock();
					System.out.println(getName()+",获取锁");
					noFairReentrantLock.unlock();
					System.out.println(getName()+",释放取锁");
				}
			}.start();
		}


	}
}
