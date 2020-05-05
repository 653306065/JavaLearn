package org.java.study.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueTest {

	public static   ArrayBlockingQueue<String> ArrayBlockingQueue =new ArrayBlockingQueue<String>(10,true);
	
	private String name="11";
	
	public static void main(String[] args) {
//		for(int i=0;i<1000;i++) {
//			try {
//				ArrayBlockingQueue.put("test");
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		ArrayBlockingQueueTest arrayBlockingQueueTest=  new ArrayBlockingQueueTest();
		interior interior=arrayBlockingQueueTest.new interior();
		System.out.println(interior.d);
	}
	
	
	public class interior {
		String d=name;
	}
	
}
