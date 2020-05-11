package org.java.study.thread;

public class ThreadLocalTest {

	public static ThreadLocal<Object> local=new ThreadLocal<Object>();
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++) {
			new Thread() {
				public void run() {
					local.set(getName());
					System.out.println(local.get());
				}
			}.start();
		}
	}
}
