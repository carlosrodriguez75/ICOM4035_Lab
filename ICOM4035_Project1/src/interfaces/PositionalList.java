package interfaces;

public interface PositionalList<E> {
	/*
	 * Returns the number of elements in the list.
	 */
	 
	 int size(); 
	 boolean isEmpty();
	 Position<E> last();
	 Position<E> first();
	 Position<E> before(Position<E> p) throws IllegalArgumentException;
	 Position<E> after(Position<E> p) throws IllegalArgumentException;
	 Position<E> addFirst(E e);
	 Position<E> addLast(E e);
	 Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException;
	 Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException;
	 E set(Position<E> p, E e)throws IllegalArgumentException;
	 E remove(Position<E> p)throws IllegalArgumentException;

}
