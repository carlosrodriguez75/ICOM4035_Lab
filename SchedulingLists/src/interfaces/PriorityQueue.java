package interfaces;
/*
 * Interface for the priority queue ADT.
 */

import exceptions.IllegalStateEXception;
import exceptions.IllegallArgumentException;

public interface PriorityQueue<K,V> { 
	int size();
	boolean isEmpty(); 
	Entry<K,V> insert(K key, V value) throws IllegalArgumentException, IllegallArgumentException, IllegalStateEXception;
	Entry<K,V> min() throws IllegalStateEXception;
	Entry<K,V> removeMin(); 
} 