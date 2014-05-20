/**
 * 
 */
package org.mcavallo.opencloud.filters.test;

import java.util.ArrayList;
import java.util.List;

import org.mcavallo.opencloud.filters.Filter;
import org.mcavallo.opencloud.filters.NonNullFilter;

import junit.framework.TestCase;

public class TestNonNullFilter extends TestCase {

	public void testAccept() {
		// Initialization
		Filter<String> filter = new NonNullFilter<String>();
		
		// Discard null objects
		assertFalse(filter.accept(null));

		// Accept any non-null object
		assertTrue(filter.accept(new String("")));
		assertTrue(filter.accept(new String("123")));
	}

	public void testFilter() {
		// Initialization
		Filter<String> filter = new NonNullFilter<String>();
		
		List<String> array = new ArrayList<String>();
		array.add(null);
		array.add("");
		array.add("123");
		
		// Check filtered array
		assertEquals(3, array.size());
		filter.filter(array);
		assertEquals(2, array.size());
		assertFalse(array.contains(null));
		assertTrue(array.contains(new String("")));
		assertTrue(array.contains(new String("123")));
	}

}
