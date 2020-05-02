package org.java.study.dataStructure;

public class MyLinkedList<T> {

	private Node<T> first;

	private Node<T> last;

	private int size = 0;

	public boolean add(T t) {
		final Node<T> l=this.last;
		final Node<T> node=new Node<T>(t,last,null);
	    this.last=node;
		if(l==null) {
			first=node;
		}else {
			l.next=node;
		}
		size++;
		return true;
	}

	public T get(int index) {
		Node<T> node = first;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node.value;
	}

	private static class Node<T> {

		private Node<T> prev;

		private Node<T> next;

		private T value;

		public Node(T t, Node<T> prev, Node<T> next) {
			this.prev = prev;
			this.next = next;
			this.value = t;
		}
	}

	public static void main(String[] args) {
		MyLinkedList<Integer> myLinkedList = new MyLinkedList<Integer>();
		myLinkedList.add(1);
		myLinkedList.add(2);
		myLinkedList.add(3);
		System.out.println(myLinkedList.get(1));
	}
}
