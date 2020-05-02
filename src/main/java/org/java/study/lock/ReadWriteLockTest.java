package org.java.study.lock;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * @author 付强
 *
 */
public class ReadWriteLockTest {

	private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);

	public static String value = "test";

	public static void main(String[] args) {

		for (int i = 0; i < 20; i++) {
			new Thread() {
				public void run() {
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					reentrantReadWriteLock.readLock().lock();
					System.out.println("read");
					reentrantReadWriteLock.readLock().unlock();
					System.out.println(value);
				}
			}.start();
		}

		for (int i = 0; i < 20; i++) {
			new Thread() {
				public void run() {
					reentrantReadWriteLock.writeLock().lock();
					System.out.println("write");
					value = UUID.randomUUID().toString();
					reentrantReadWriteLock.writeLock().unlock();
					System.out.println(value);
				}
			}.start();
		}
	}
}
