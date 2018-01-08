package interfaces;

import java.util.Iterator;

import classes.Node;
import system.Member;


/** An interface for a tree where nodes can have an arbitrary number of children. */
public interface Tree<E> extends Iterable<E> {	
    Node<E> root(); 
    Node<E> parent(Node<E> p) throws IllegalArgumentException; 
    Iterable<Node<E>> children(Node<E> r) throws 
                                         IllegalArgumentException; 
    int numChildren(Node<E> p) throws IllegalArgumentException; 
    boolean isInternal(Node<E> p) throws IllegalArgumentException; 
    boolean isExternal(Node<E> p) throws IllegalArgumentException; 
    boolean isRoot(Node<E> p) throws IllegalArgumentException; 
    int size(); 
    boolean isEmpty(); 
    Iterator<E> iterator(); 
    Iterable<Node<E>> positions(); 
    
    void display();  // added for testing purposes
}