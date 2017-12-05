package interfaces;

import exceptions.IllegalStateEXception;

public interface Position<E> {
	E getElement() throws IllegalStateEXception;

}
