package org.java.study.lock;

import java.util.UUID;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {

	public static StampedLock stampedLock = new StampedLock();

	public static String uuid;

	public static void main(String[] args) {

		//write 
		for (int i = 0; i < 1000; i++) {
			new Thread(() -> {
				long stamp= stampedLock.writeLock();
				uuid=UUID.randomUUID().toString();
				System.out.println("write:"+uuid);
				stampedLock.unlock(stamp);
			}).start();
		}
		
		//read
		for(int i=0;i<1000;i++) {
			new Thread(() -> {
				long stamp= stampedLock.readLock();
				System.out.println("read:"+uuid);
				stampedLock.unlock(stamp);
			}).start();
		}

	}
}
