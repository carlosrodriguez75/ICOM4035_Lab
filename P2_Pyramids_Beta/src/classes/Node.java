package classes;

import java.util.ArrayList;

import interfaces.Position;

/**
 * 
 * @author pedroirivera-vega
 *
 * @param <E>
 */
public class Node<E> implements Position<E> {

	private E element; 
	private Node<E> parent; 
	public ArrayList<Node<E>> children; 
	private LinkedTree<E> ownerTree;  // the tree the node belongs to
	private Node<E> mentor;
	
	public Node(E element, Node<E> parent, ArrayList<Node<E>> c, 
			LinkedTree<E> ownerTree) { 
		this.element = element; 
		this.parent = parent; 
		this.children = c; 
		this.ownerTree = ownerTree;   
	}
	
	public Node(E element) { 
		this.element = element; 
		this.parent = this; 
		this.children = null; 
	}
	
	public void clear() { 
		this.element = null; 
		this.parent = this; 
		this.children = null; 
	}
	
	public Node<E> getParent() {
		return parent;
	}

	public void setParent(Node<E> parent) {
		this.parent = parent;
	}

	public ArrayList<Node<E>> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node<E>> children) {
		this.children = children;
	}

	@Override
	public E getElement() {
		return element;
	} 
	
	public void setElement(E e) { 
		element = e; 
	}		
	
	public LinkedTree<E> getOwnerTree() { 
		return ownerTree; 
	}
	
	public void setOwnerTree(LinkedTree<E> t) { 
		ownerTree = t; 
	}
	
	public void discard() { 
		parent = this; 
		element = null; 
		children = null; 
		ownerTree = null; 
	}

	public void setMentor(Node<E> mentor) {
		this.mentor = mentor;
		
	}
	
	public Node<E> getMentor(){
		return this.mentor;
	}

//	public void setMentor(String name) {
//		this.mentor = name;
//		
//	}
//	
//	public String getMentor(){
//		return mentor;
//	}
}