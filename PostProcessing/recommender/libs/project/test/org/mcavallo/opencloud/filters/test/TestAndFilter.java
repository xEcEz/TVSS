/**
 * 
 */
package org.mcavallo.opencloud.filters.test;

import java.util.ArrayList;
import java.util.List;

import org.mcavallo.opencloud.Tag;
import org.mcavallo.opencloud.filters.AndFilter;
import org.mcavallo.opencloud.filters.Filter;
import org.mcavallo.opencloud.filters.LengthFilter;

import junit.framework.TestCase;

public class TestAndFilter extends TestCase {

	public void testAccept() {
		// Initialization
		Filter<Tag> filter1 = new LengthFilter(1,4);
		Filter<Tag> filter2 = new LengthFilter(2,5);
		Filter<Tag> filter3 = new LengthFilter(3,3);
		Filter<Tag> filter1And2 = new AndFilter<Tag>(filter1, filter2);
		Filter<Tag> filter1And2And3 = new AndFilter<Tag>(filter1, filter2, filter3);
		
		// Testing filter 1 AND 2
		// Discard null objects
		assertFalse(filter1And2.accept(null));
		
		// Discard objects that don't verify all filters
		assertFalse(filter1And2.accept(new Tag("")));
		assertFalse(filter1And2.accept(new Tag("1")));
		assertFalse(filter1And2.accept(new Tag("12345")));

		// Accept objects that verify all filters
		assertTrue(filter1And2.accept(new Tag("12")));
		assertTrue(filter1And2.accept(new Tag("123")));
		assertTrue(filter1And2.accept(new Tag("1234")));
		
		// Testing filter 1 AND 2 AND 3
		// Discard null objects
		assertFalse(filter1And2And3.accept(null));
		
		// Discard objects that don't verify all filters
		assertFalse(filter1And2And3.accept(new Tag("")));
		assertFalse(filter1And2And3.accept(new Tag("1")));
		assertFalse(filter1And2And3.accept(new Tag("12")));
		assertFalse(filter1And2And3.accept(new Tag("1234")));
		assertFalse(filter1And2And3.accept(new Tag("12345")));

		// Accept objects that verify all filters
		assertTrue(filter1And2And3.accept(new Tag("123")));
	}

	public void testFilter() {
		// Initialization
		Filter<Tag> filter1 = new LengthFilter(1,4);
		Filter<Tag> filter2 = new LengthFilter(2,5);
		Filter<Tag> filter3 = new LengthFilter(3,3);
		Filter<Tag> filter1And2 = new AndFilter<Tag>(filter1, filter2);
		Filter<Tag> filter1And2And3 = new AndFilter<Tag>(filter1And2, filter3);
		
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
		filter1And2And3.filter(array);
		assertEquals(1, array.size());
		assertFalse(array.contains(null));
		assertFalse(array.contains(new Tag("")));
		assertFalse(array.contains(new Tag("1")));
		assertFalse(array.contains(new Tag("12")));
		assertTrue(array.contains(new Tag("123")));
		assertFalse(array.contains(new Tag("1234")));
		assertFalse(array.contains(new Tag("12345")));
		assertFalse(array.contains(new Tag("123456")));
	}

}
