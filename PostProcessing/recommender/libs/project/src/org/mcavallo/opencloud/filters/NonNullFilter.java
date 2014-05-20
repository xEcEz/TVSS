package org.mcavallo.opencloud.filters;

/**
 * Filter that accepts all non-null objects.
 */
public class NonNullFilter<E> extends FilterBase<E> {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean accept(E e) {
		return (e != null);
	}

}
