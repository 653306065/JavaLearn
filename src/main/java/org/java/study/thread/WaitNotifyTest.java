package org.java.study.thread;

public class WaitNotifyTest {

    public Integer value = 0;

    public synchronized void addValue() {
        notifyAll();
        value++;
    }

    public synchronized Integer getValue() throws InterruptedException {
        wait();
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
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
    }

}
