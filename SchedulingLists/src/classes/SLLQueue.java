package classes;

import exceptions.EmptyQueueException;
import interfaces.Queue;

/** 
A partial implementation of the Queue using a singly linked list with references 
to the first and to the last node.
 **/
public class SLLQueue<E> implements Queue<E> {

	private static class Node<E> {   // Inner class for nodes. 
		private E element; 
		private Node<E> next; 
		 public Node(E e, Node<E>n){ 
			 element = e; 
			 next = n; 
			} 
		public E getElement() {
			// TODO Auto-generated method stub
			return element;

		
		}
		public Node<E> getNext() {
			return next;
		}
		public void setNext(Node<E> nuevo) {
			next = nuevo;
			
		}
	}	
	private Node<E> first, last;   // references to first and last node
	private int size; 

	public SLLQueue() {           // initializes instance as empty queue
		first = last = null; 
		size = 0; 
	}
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public E first() {
		if (isEmpty()) return null;
		return first.getElement(); 
	}
	public E dequeue() {
		if (isEmpty()) throw new EmptyQueueException("dequeue: Queue is empty."); ;		
		E etr = first.getElement(); 
		first = first.getNext();
		size--;
		if(size==0){
			last = null;
		}
		return etr;
	}
	public void enqueue(E e) {
		Node<E> nuevo = new Node<E>(e,null); 
		if (size == 0) 
			first = nuevo; 
		else { 
			last.setNext(nuevo);
			
		}
		last = nuevo;
		size++; 
	}
}











