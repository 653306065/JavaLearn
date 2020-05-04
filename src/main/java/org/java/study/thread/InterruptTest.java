package org.java.study.thread;

public class InterruptTest {

	public static void main(String[] args) {
		Thread thread = new Thread() {
			public void run() {
				while (true) {
                    if(isInterrupted()) {
    					System.out.println(isInterrupted());
                    	System.out.println("线程中断");
                    	this.interrupt();
                    	break;
                    }
                    System.out.println("线程正在运行");
				}
			}
		};
		thread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread.interrupt();
		
	}
}
