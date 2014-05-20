/**
 * 
 */
package org.mcavallo.opencloud.filters.test;

import java.util.ArrayList;
import java.util.List;

import org.mcavallo.opencloud.Tag;
import org.mcavallo.opencloud.filters.Filter;
import org.mcavallo.opencloud.filters.MaxLengthFilter;

import junit.framework.TestCase;

public class TestMaxLengthFilter extends TestCase {

	public void testAccept() {
		// Initialization
		Filter<Tag> filter = new MaxLengthFilter(5);
		
		// Discard null objects
		assertFalse(filter.accept(null));

		// Discard too long tags
		assertFalse(filter.accept(new Tag("123456")));

		// Accept tags within limits
		assertTrue(filter.accept(new Tag("")));
		assertTrue(filter.accept(new Tag("12345")));
	}

	public void testFilter() {
		// Initialization
		Filter<Tag> filter = new MaxLengthFilter(5);
		
		List<Tag> array = new ArrayList<Tag>();
		array.add(null);
		array.add(new Tag(""));
		array.add(new Tag("1"));
		array.add(new Tag("12"));
		array.add(new Tag("123"));
		array.add(new Tag("1234"));
		array.add(new Tag("12345"));
		array.add(new Tag("123456"));
		
		// Check filtered array
		assertEquals(8, array.size());
		filter.filter(array);
		assertEquals(6, array.size());
		assertFalse(array.contains(null));
		assertTrue(array.contains(new Tag("")));
		assertTrue(array.contains(new Tag("1")));
		assertTrue(array.contains(new Tag("12")));
		assertTrue(array.contains(new Tag("123")));
		assertTrue(array.contains(new Tag("1234")));
		assertTrue(array.contains(new Tag("12345")));
		assertFalse(array.contains(new Tag("123456")));
	}

}
