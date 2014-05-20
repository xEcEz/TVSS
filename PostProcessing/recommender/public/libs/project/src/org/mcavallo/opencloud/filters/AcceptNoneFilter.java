package org.mcavallo.opencloud.filters;

/**
 * Filter that doesn't accept any objects.
 */
public class AcceptNoneFilter<E> extends FilterBase<E> {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean accept(E e) {
		return false;
	}

}
