package interfaces;

import exceptions.EmptyStackException;

public interface Stack<E> {
	int size(); 
	boolean isEmpty(); 
	E top() throws EmptyStackException; 
	E pop() throws EmptyStackException; 
	void push(E e); 
}
