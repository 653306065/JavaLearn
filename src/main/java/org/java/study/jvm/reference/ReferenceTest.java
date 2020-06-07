package org.java.study.jvm.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.activemq.console.Main;

import jodd.typeconverter.impl.UUIDConverter;

/**
 * 
 * -XX:+PrintGCDetails -Xmx64m
 * 
 * @author fuqiang
 *
 */
public class ReferenceTest {

	public static void main(String[] args) {
		ReferenceTest object = new ReferenceTest();
		List<String> softList = new ArrayList<String>();
		List<String> weakList = new ArrayList<String>();
		List<String> phantomList = new ArrayList<String>();

		SoftReference<Object> SoftReference = new SoftReference<Object>(softList);
		WeakReference<Object> WeakReference = new WeakReference<Object>(weakList);
		PhantomReference<Object> PhantomReference = new PhantomReference<Object>(phantomList,
				new ReferenceQueue<Object>());
		while (true) {
			softList.add(UUID.randomUUID().toString());
			weakList.add(UUID.randomUUID().toString());
			phantomList.add(UUID.randomUUID().toString());
			if (Runtime.getRuntime().freeMemory()==0) {
                   break;
			}
		}
	}

}
