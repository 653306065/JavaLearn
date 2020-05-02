
package org.java.study.volatileTest;

public class Test {

	public Value isStop=new Value(false);
	
	public static void main(String[] args) {
		Test test=new Test();
		for(int i=0;i<10;i++) {
			ReadThread readThread=new ReadThread(test.isStop);
			readThread.start();
		}
		WriteThread writeThread=new WriteThread(test.isStop);
		writeThread.start();
	}
}
