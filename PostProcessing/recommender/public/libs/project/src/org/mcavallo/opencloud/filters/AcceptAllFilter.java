package org.mcavallo.opencloud.filters;

/**
 * Filter that accepts all objects.
 */
public class AcceptAllFilter<E> extends FilterBase<E> {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean accept(E e) {
		return true;
	}

}
