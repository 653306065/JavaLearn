package org.java.study.hash;

import java.util.HashMap;

/**
 * 模拟hash冲突
 * @author 付强
 *
 */
public class HashmapClash   {

	private int hashCode;

	public int getHashCode() {
		return hashCode;
	}
	
	public HashmapClash(int hashCode) {
		this.hashCode=hashCode;
	}


	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	@Override
	public int hashCode() {
		System.out.println("hashCode:"+hashCode);
		return hashCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		System.out.println(obj);
		return super.equals(obj);
	}
	
	public static void main(String[] args) {
		HashmapClash hashmapTest1=new HashmapClash(10);
		HashmapClash hashmapTest2=new HashmapClash(10);
		HashmapClash hashmapTest3=new HashmapClash(50);
		HashMap<HashmapClash,String> map=new HashMap<HashmapClash,String>();
		map.put(hashmapTest1, "test1");
		map.put(hashmapTest2, "test2");
		map.put(hashmapTest3, "test3");
		System.out.println(map.get(hashmapTest1));
		System.out.println(map.get(hashmapTest2));
		System.out.println(map.get(hashmapTest3));
	}
}
