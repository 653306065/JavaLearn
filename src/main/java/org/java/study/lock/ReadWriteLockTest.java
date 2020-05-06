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

		for (int i = 0; i < 100; i++) {
			new Thread() {
				public void run() {
					reentrantReadWriteLock.readLock().lock();
					System.out.println("read:"+value);
					reentrantReadWriteLock.readLock().unlock();
				}
			}.start();
		}

		for (int i = 0; i < 100; i++) {
			new Thread() {
				public void run() {
					reentrantReadWriteLock.writeLock().lock();
					value = UUID.randomUUID().toString();
					System.out.println("write:"+value);
					reentrantReadWriteLock.writeLock().unlock();
				}
			}.start();
		}
	}
}
