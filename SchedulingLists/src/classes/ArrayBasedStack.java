package classes;

import interfaces.Stack;
import system.Job;

public class ArrayBasedStack<E> implements Stack<E> {
	public static final int CAPACITY = 100;
	private E[] MyArray;
	private int topPointer = -1;
	public ArrayBasedStack() {this(CAPACITY);}
	@SuppressWarnings("unchecked")

	public ArrayBasedStack(int capacity){
		MyArray = (E[]) new Object[capacity];
	}
	public int size() {return topPointer; }

	public boolean isEmpty() { return topPointer == -1;}
	public void push(E e)throws IllegalStateException {
		if(size() == MyArray.length) throw new IllegalStateException("Stack is full");
		MyArray[++topPointer] = e;
		//topPointer ++;	
	}

	public E top() {
		if(isEmpty()) return null;

		return MyArray[topPointer];
	}

	public E pop() {
		if(isEmpty())return null;
		E temp = MyArray[topPointer];
		MyArray[topPointer] = null;
		topPointer--;
		return temp;
	}

	//Reverse an array of stack
	public static <E> void reverse(E[ ] a) 
	{  ArrayBasedStack<E> buffer = new ArrayBasedStack<>(a.length); 
	for (int i=0; i < a.length; i++) 
		buffer.push(a[i]); 
	for (int i=0; i < a.length; i++)
		a[i] = buffer.pop(); 
	}
	public void reverse(Job top) {
		// TODO Auto-generated method stub
		
	} 

}
