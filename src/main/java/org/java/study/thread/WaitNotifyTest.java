package org.java.study.thread;

public class WaitNotifyTest {

    public static Integer value = 0;

    public Object lock = new Object();

    //对象锁
    public void addValueLock() {
        synchronized (lock) {
            value++;
            lock.notifyAll();
        }
    }

    //对象锁
    public Integer getValueLock() throws InterruptedException {
        synchronized (lock) {
            lock.wait();
            return value;
        }
    }

    //实例锁
    public synchronized void addValue() {
        notifyAll();
        value++;
    }

    //实例锁
    public synchronized Integer getValue() throws InterruptedException {
        wait();
        return value;
    }

    //类锁
    public synchronized static void addValueStatic() {
        WaitNotifyTest.class.notifyAll();
        value++;
    }

    //类锁
    public synchronized static Integer getValueStatic() throws InterruptedException {
        WaitNotifyTest.class.wait();
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        //实例锁测试
        WaitNotifyTest waitNotifyTest = new WaitNotifyTest();
        Thread thread1 = new Thread(() -> {
            try {
                System.out.println(waitNotifyTest.getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            waitNotifyTest.addValue();
        });
        thread1.start();
        Thread.sleep(1);
        thread2.start();

        //类锁测试
        Thread thread3 = new Thread(() -> {
            try {
                System.out.println(getValueStatic());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread4 = new Thread(() -> {
            addValueStatic();
        });
        thread3.start();
        Thread.sleep(1);
        thread4.start();

        //对象锁测试
        Thread thread5 = new Thread(() -> {
            try {
                System.out.println(waitNotifyTest.getValueLock());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread6 = new Thread(() -> {
            waitNotifyTest.addValueLock();
        });
        thread5.start();
        Thread.sleep(1);
        thread6.start();
    }


}
