package org.java.study.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    private static ReentrantLock lock = new ReentrantLock(true);

    private static Condition condition = lock.newCondition();
	
    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            System.out.println("获取成功");
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("重新获得锁");
            lock.unlock();
        }).start();
        
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            lock.lock();
            System.out.println("获取成功");
            condition.signal();
            lock.unlock();
        }).start();
    }

}
