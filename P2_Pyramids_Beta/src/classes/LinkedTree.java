package classes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import interfaces.Position;
import system.Member;


import java.util.ArrayList;
import java.util.Iterator;


/**
 * 
 * @author pedroirivera-vega
 *
 * @param <E>
 */
public class LinkedTree<E> extends AbstractTree<E> {

	private Node<E> root; 
	private int size; 
	boolean flag = false;
	ArrayList<E> arrestsList = new ArrayList<E>();
	int arrests = 0,optionA,optionB;

	public LinkedTree() { 
		root = null; 
		size = 0; 
	}

	private Node<E> validate(Node<E> p) throws IllegalArgumentException { 
		if (!(p instanceof Node<?>)) 
			throw new IllegalArgumentException("Invalid position type for this implementation."); 
		Node<E> np = (Node<E>) p; 
		if (np.getParent() == np)
			throw new IllegalArgumentException("Target position is not part of a tree.");

		//the following validates that p is a position in this tree
		if (np.getOwnerTree() != this)
			throw new IllegalArgumentException("Target position is not part of the tree.");	
		return np; 
	}

	@Override
	public Node<E> root() {
		return root;
	}

	@Override
	public Node<E> parent(Node<E> p) throws IllegalArgumentException {
		Node<E> np = this.validate(p); 
		return np.getParent(); 
	}

	@Override
	public Iterable<Node<E>> children(Node<E> node)
			throws IllegalArgumentException {
		Node<E> np = node;
		ArrayList<Node<E>> result = 
				new ArrayList<Node<E>>(); 
		if (np.getChildren() != null) 
			for(Node<E> cp : np.getChildren())
				result.add(cp); 
		return result; 
	}

	@Override
	public int numChildren(Node<E> p) throws IllegalArgumentException {
		Node<E> np = validate(p);  
		if (np.getChildren() != null) 
			return np.getChildren().size();
		else 
			return 0; 
	}

	@Override
	public int size() {
		return size;
	}

	// some tree update operations.....
	public Node<E> addRoot(E element) throws IllegalStateException { 
		if (this.root != null) 
			throw new IllegalStateException("Tree must be empty to add a root."); 
		root = new Node<>(element, null, null, this); 
		size++; 
		return root; 
	}

	/**
	 * Add a new element as a child to a given position in the tree
	 * @param p the position to be the parent of the new element position
	 * @param element the new element to add to the tree
	 * @param testingCase 
	 * @return the Position<E> of where the new element is stored
	 * @throws IllegalArgumentException if the position is not valid.....
	 */
	public Node<E> addChild(String parent, E element, LinkedTree<E> testingCase) 
			throws IllegalArgumentException { 
		int childs = 0;
		if(parent.equals(((Member) testingCase.root.getElement()).getName())){
			if(testingCase.numChildren(root)>0){
				Node<E> nuevo = new Node<>(element, root, null, this); 
				ArrayList<Node<E>> list = (ArrayList<Node<E>>) testingCase.children(root) ;
				Node<E> mentor = list.get(list.size()-1);		
				//System.out.println(((Member) mentor.getElement()).getName());
				nuevo.setMentor(mentor);
				if (root.getChildren() == null)
					root.setChildren(new ArrayList<Node<E>>());
				root.getChildren().add(nuevo); 
				size++; 
				return nuevo;
			}
			Node<E> nuevo = new Node<>(element, root, null, this); 
			if (root.getChildren() == null)
				root.setChildren(new ArrayList<Node<E>>());
			root.getChildren().add(nuevo); 
			size++; 
			return nuevo; 
		}
		else{

			Node<E> newParent = searchChild(testingCase,parent,root);
			if(testingCase.numChildren(newParent)>0){
				Node<E> nuevo = new Node<>(element,newParent , null, this); 
				ArrayList<Node<E>> list = (ArrayList<Node<E>>) testingCase.children(newParent) ;
				Node<E> mentor = list.get(list.size()-1);		
				//System.out.println(((Member) mentor.getElement()).getName());
				nuevo.setMentor(mentor);
				if (newParent.getChildren() == null)
					newParent.setChildren(new ArrayList<Node<E>>());
				newParent.getChildren().add(nuevo); 
				size++; 
				return nuevo;
			}
			Node<E> nuevo = new Node<>(element,newParent , null, this); 
			if (newParent.getChildren() == null)
				newParent.setChildren(new ArrayList<Node<E>>());
			newParent.getChildren().add(nuevo); 
			size++; 
			return nuevo;
		}


	}

	private Node<E> searchChild(LinkedTree<E> testingCase, String parent,Node<E> index) {
		if(testingCase.root() == null){return null;}
		if(isInternal(index)){
			ArrayList<Node<E>> nuevo = (ArrayList<Node<E>>) testingCase.children(index) ;
			for (int counter = 0; counter < nuevo.size(); counter++) { 
				if(parent.equals(((Member) nuevo.get(counter).getElement()).getName())){
					return nuevo.get(counter);
				}    
			}

			for(int i = 0; i< nuevo.size();i++){
				Node<E> tmp = searchChild(testingCase,parent,nuevo.get(i));	
				if(tmp!= null){
					return tmp;
				}
			}
		}

		return null;

	}




	public E remove(Node<E> p) throws IllegalArgumentException { 
		Node<E> ntd = validate(p); 
		E etr = ntd.getElement(); 
		Node<E> parent = ntd.getParent(); 
		if (parent == null)    // then ntd is the root
			if (numChildren(ntd) > 1) 
				throw new IllegalArgumentException
				("Cannot remove a root having more than one child."); 
			else if (numChildren(ntd) == 0)
				root = null; 
			else { 
				root = ntd.getChildren().get(0);    // the only child
				root.setParent(null);
			}
		else { 
			for (Node<E> childNTD : ntd.children) { 
				parent.getChildren().add(childNTD);   
				// Exercise: why can't we use addChild method here????
				childNTD.setParent(parent); 
			}	
		}

		/*******************************************************/
		// SOME MISSING CODE HERE -- DISCOVER IT AN ADD... 
		// AS SPECIFIED IN EXERCISE 4. 

		parent.getChildren().remove(ntd);
		// discard the removed node
		ntd.discard(); 

		size--;    // adjust size
		return etr;   // return removed value
	}

	public ArrayList<E> arrestOperation(int theArrests,Node<E> pointer) {
		// arrestsList = new ArrayList<E>();
		arrestsList.clear();
		arrests=theArrests;
		arrestsList.add(pointer.getElement());
		arrests--;
		while(arrests != 0){
			Node<E> arrested = arrestOne(this,pointer);
			arrestsList.add(arrested.getElement());		
			arrests--;
			pointer = searchInTree(arrested,this,this.root);
		}
		return arrestsList;

	}

	private Node<E> arrestOne(LinkedTree<E> linkedTree,  Node<E> pointer) {
		LinkedTree<E> tmp = new LinkedTree<E>();	//Se utilizara para crear un tree nuevo
		Node<E> parentNode = pointer.getParent();
		Node<E> mentor = pointer.getMentor(); 
		tmp.addRoot(pointer.getElement());
		pointer = (searchInTree(pointer,linkedTree,linkedTree.root));
		int childrens = linkedTree.numChildren(pointer);
		if(childrens!= 0){
			//ArrayList<Node<E>> nuevo = (ArrayList<Node<E>>) linkedTree.children(pointer);
			ArrayList<Node<E>> nuevo  = (ArrayList<Node<E>>) pointer.getChildren();
			for(int i = 0; i< nuevo.size(); i++){	
				tmp.addChild(((Member) pointer.getElement()).getName(), nuevo.get(i).getElement(), tmp);
				//tmp.addChild(pointer, nuevo.get(i).getElement());
			}

		}
		if(parentNode != null){
			//tmp.addChild(pointer,parentNode.getElement());
			tmp.addChild(((Member) pointer.getElement()).getName(), parentNode.getElement(), tmp);
		}

		if(mentor != null){
			//tmp.addChild(pointer,mentor.getElement());
			tmp.addChild(((Member) pointer.getElement()).getName(), mentor.getElement(), tmp);
		}

		ArrayList<Node<E>> tmpList = tmp.root.getChildren(); //(ArrayList<Node<E>>) tmp.children(tmp.root);

		return arrestNode(tmpList,linkedTree);

	}

	




	private Node<E> arrestNode(ArrayList<Node<E>> tmpList, LinkedTree<E> linkedTree) {
		int maxAsset = 0;	
		Node<E> arrestNode = new Node<E>(null,null,null,null);
		Node<E> currentNode = new Node<E>(null,null,null,null);
		for(int i = 0; i< tmpList.size(); i++){	
			if(maxAsset<((Member) tmpList.get(i).getElement()).getAsset()){
				maxAsset = ((Member) tmpList.get(i).getElement()).getAsset();
				arrestNode = tmpList.get(i);
				currentNode = arrestNode;
			}

			else if(maxAsset == ((Member) tmpList.get(i).getElement()).getAsset()){
				if(arrests>1){
					//if(isInternal(tmpList.get(i)) && !isInternal(currentNode)){
					if(verifyInternal(searchInTree(tmpList.get(i),linkedTree,linkedTree.root),linkedTree) && !(verifyInternal(searchInTree(currentNode,linkedTree,linkedTree.root),linkedTree))){
						arrestNode = tmpList.get(i);
						currentNode = arrestNode;
					}
					else if(!verifyInternal(searchInTree(tmpList.get(i),linkedTree,linkedTree.root),linkedTree) && (verifyInternal(searchInTree(currentNode,linkedTree,linkedTree.root),linkedTree))){
						arrestNode = currentNode;
					}
					else 
						optionA = numChildren(searchInTree(tmpList.get(i),linkedTree,linkedTree.root));
					optionB = numChildren(searchInTree(currentNode,linkedTree,linkedTree.root));
					if(optionA > optionB){
						arrestNode = searchInTree(tmpList.get(i),linkedTree,linkedTree.root);
						currentNode = arrestNode;
					}
					else if(optionA<optionB){
						arrestNode = searchInTree(currentNode,linkedTree,linkedTree.root);
					}
					else
						arrestNode = searchInTree(tmpList.get(i),linkedTree,linkedTree.root);
				}

				arrestNode = tmpList.get(i);
				flag = true;

			}
		}
		//Verificar que el Nodo que se va a arrestar no este en la Lista
		//Si ya esta en la lista lo elimino de las opciones
		for(int i = 0; i<arrestsList.size();i++){
			if(arrestsList.get(i).equals(arrestNode.getElement())){
				tmpList.remove(arrestNode);
				return arrestNode(tmpList,linkedTree);
			}
		}

		//Si los arrestos que faltan son mas de uno el Nodo que debe devolver debe ser Internal
		if(arrests>1){
			if(verifyInternal(searchInTree(arrestNode,linkedTree,linkedTree.root),linkedTree)){
				return arrestNode;
			}
			tmpList.remove(arrestNode);
			return arrestNode(tmpList,linkedTree);
		}
		return arrestNode;
	}

	private Node<E> searchInTree(Node<E> target, LinkedTree<E> linkedTree,Node<E> index) {

		if(((Member) target.getElement()).getName().equals(((Member) root.getElement()).getName())){
			return linkedTree.root;
		}
		if(isInternal(index)){
			ArrayList<Node<E>> nuevo = (ArrayList<Node<E>>) linkedTree.children(index) ;
			for (int counter = 0; counter < nuevo.size(); counter++) { 
				if(((Member) target.getElement()).getName().equals(((Member) nuevo.get(counter).getElement()).getName())){
					return nuevo.get(counter);
				}
			}
			for(int i = 0; i<nuevo.size();i++){
				Node<E> tmp = searchInTree(target,linkedTree,nuevo.get(i));

				//			if(((Member) target.getElement()).getName().equals(((Member) tmp.getElement()).getName())){
				//				return tmp;
				//			}
				if(tmp!=null){
					return tmp;
				}
			}
		}
		//		private Node<E> searchChild(LinkedTree<E> testingCase, String parent,Node<E> index) {
		//			if(testingCase.root() == null){return null;}
		//			if(isInternal(index)){
		//				ArrayList<Node<E>> nuevo = (ArrayList<Node<E>>) testingCase.children(index) ;
		//				for (int counter = 0; counter < nuevo.size(); counter++) { 
		//					if(parent.equals(((Member) nuevo.get(counter).getElement()).getName())){
		//						return nuevo.get(counter);
		//					}    
		//				}
		//
		//				for(int i = 0; i< nuevo.size();i++){
		//					Node<E> tmp = searchChild(testingCase,parent,nuevo.get(i));	
		//					if(tmp!= null){
		//						return tmp;
		//					}
		//				}
		//			}
		//
		//			return null;
		//
		//		}

		return null;
	}













	//	private Node search(String name, Node node){
	//	    if(node != null){
	//	        if(node.name().equals(name)){
	//	           return node;
	//	        } else {
	//	            Node foundNode = search(name, node.left);
	//	            if(foundNode == null) {
	//	                foundNode = search(name, node.right);
	//	            }
	//	            return foundNode;
	//	         }
	//	    } else {
	//	        return null;
	//	    }
	//	}







	private boolean verifyInternal(Node<E> arrestNode, LinkedTree<E> linkedTree) {
		if(linkedTree.isInternal(arrestNode)){
			return true;
		}
		return false;

	}

	//	public ArrayList<E> traverse(Node<E> child){ // post order traversal
	//		ArrayList<E> results = new ArrayList<E>();
	//		SLLQueue<E> cases = new SLLQueue<E>();
	//	    for(Node<E> each : child.getChildren()){
	//	    	//results.add(each);
	//	    	//cases.enqueue(each);
	//	        traverse(each);
	//	    }
	//	    return results;
	//	}

	//Preorder traversal
	//	   private void walk(Node<E> element, ArrayList<Member> targets)
	//	   {
	//	       targets.add(element);
	//	      for(Node<E> data : element.getChildren())
	//	      {
	//	    	  targets.add(data)
	//	         walk(data, targets);
	//	      }
	//	   }




	// Creating a CLONE
	public LinkedTree<E> clone() throws CloneNotSupportedException { 
		LinkedTree<E> other = new LinkedTree<>(); 
		if (!isEmpty()){
			other.addRoot(root().getElement()); 
			cloneSubtree(root(), other, other.root()); 
		}
		return other; 
	}
	//
	private void cloneSubtree(Node<E> rThis, LinkedTree<E> other,
			Node<E> rOther) {
		for (Node<E> pThis : children(rThis)) { 

			Node<E> pOther = other.addChild(rOther, pThis.getElement());
			cloneSubtree(pThis, other, pOther); 
		}
	}

	/**
	 * Add a new element as a child to a given position in the tree
	 * @param p the position to be the parent of the new element position
	 * @param element the new element to add to the tree
	 * @return the Position<E> of where the new element is stored
	 * @throws IllegalArgumentException if the position is not valid.....
	 */
	public Node<E> addChild(Node<E> p, E element) {
		//throws IllegalArgumentException { 
		//Node<E> np = validate(p);  
		Node<E> np = (Node<E>) p;
		Node<E> nuevo = new Node<>(element, np, null, this); 
		if (np.getChildren() == null)
			np.setChildren(new ArrayList<Node<E>>());
		np.getChildren().add(nuevo); 
		size++; 
		return nuevo; 
	}	



}


