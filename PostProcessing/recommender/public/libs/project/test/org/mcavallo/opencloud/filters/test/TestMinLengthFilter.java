/**
 * 
 */
package org.mcavallo.opencloud.filters.test;

import java.util.ArrayList;
import java.util.List;

import org.mcavallo.opencloud.Tag;
import org.mcavallo.opencloud.filters.Filter;
import org.mcavallo.opencloud.filters.MinLengthFilter;

import junit.framework.TestCase;

public class TestMinLengthFilter extends TestCase {

	public void testAccept() {
		// Initialization
		Filter<Tag> filter = new MinLengthFilter(2);
		
		// Discard null objects
		assertFalse(filter.accept(null));

		// Discard too short tags
		assertFalse(filter.accept(new Tag("")));
		assertFalse(filter.accept(new Tag("1")));

		// Accept tags within limits
		assertTrue(filter.accept(new Tag("12")));
	}

	public void testFilter() {
		// Initialization
		Filter<Tag> filter = new MinLengthFilter(2);
		
		List<Tag> array = new ArrayList<Tag>();
		array.add(null);
		array.add(new Tag(""));
		array.add(new Tag("1"));
		array.add(new Tag("12"));
		array.add(new Tag("123"));
		
		// Check filtered array
		assertEquals(5, array.size());
		filter.filter(array);
		assertEquals(2, array.size());
		assertFalse(array.contains(null));
		assertFalse(array.contains(new Tag("")));
		assertFalse(array.contains(new Tag("1")));
		assertTrue(array.contains(new Tag("12")));
		assertTrue(array.contains(new Tag("123")));
	}

}
