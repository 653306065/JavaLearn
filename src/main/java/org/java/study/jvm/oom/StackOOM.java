package org.java.study.jvm.oom;

import java.rmi.server.UID;
import java.util.UUID;

/**
 * -Xss 设置栈内存的大小
 * 
 * @author fuqiang
 *
 */
public class StackOOM {

	public static void main(String[] args) {
		StackOOM StackOOM = new StackOOM();
		StackOOM.depth();
	}

	public void depth() {
		depth();
	}
}
