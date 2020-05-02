package org.java.study.singletonModel;

public class DoubleCheck {

	
	private volatile static DoubleCheck doubleCheck;

	
	public static  DoubleCheck getInstance() {
		if(doubleCheck==null) {
			synchronized (DoubleCheck.class) {
				if(doubleCheck==null) {
					doubleCheck=new DoubleCheck();
				}
			}
		}
		return doubleCheck;
	}
	
	
	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			new Thread() {
				public void run() {
					System.out.println(getInstance());
				}
			}.start();
		}
	}
}
