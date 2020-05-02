package org.java.study.volatileTest;

public class WriteThread extends Thread{

	public volatile Value value;
	
	public WriteThread(Value value) {
		this.value=value;
	}
	
	public void run() {
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    System.out.println("发出停止命令");
	    value.isStop=true;
	}
}
