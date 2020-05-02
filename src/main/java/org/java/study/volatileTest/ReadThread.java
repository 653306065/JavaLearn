package org.java.study.volatileTest;

public class ReadThread extends Thread{
	
	public volatile Value value;
	
	public ReadThread(Value value){
		this.value=value;
	}
	
	public void run() {
		System.out.println("线程开始任务");
		while(true) {
			if(value.isStop) {
				System.out.println("线程接受到停止命令");
				break;
			}
			//boolean b= value.isStop;
		}
		System.out.println("线程结束任务");
	}
}
