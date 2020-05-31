package org.java.study.jvm.oom;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 测试内存溢出
 * @author fuqiang
 *   -XMX512M
 *   -XX:+HeapDumpOnOutOfMemoryError  生成溢出的快照
 */
public class OOMTest {

	public static void main(String[] args) {
		List<String> list=new ArrayList<String>();
		while(true) {
			list.add(UUID.randomUUID().toString());
		}
	}
}
