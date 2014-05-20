/**
 * 
 */
package org.mcavallo.opencloud.filters.test;

import java.util.ArrayList;
import java.util.List;

import org.mcavallo.opencloud.Tag;
import org.mcavallo.opencloud.filters.Filter;
import org.mcavallo.opencloud.filters.LengthFilter;
import org.mcavallo.opencloud.filters.OrFilter;

import junit.framework.TestCase;

public class TestOrFilter extends TestCase {

	public void testAccept() {
		// Initialization
		Filter<Tag> filter1 = new LengthFilter(1,2);
		Filter<Tag> filter2 = new LengthFilter(3,3);
		Filter<Tag> filter3 = new LengthFilter(4,5);
		Filter<Tag> filter1Or2= new OrFilter<Tag>(filter1, filter2);
		Filter<Tag> filter1Or2Or3 = new OrFilter<Tag>(filter1, filter2, filter3);
		
		// Testing filter 1 OR 2
		// Discard null objects
		assertFalse(filter1Or2.accept(null));
		
		// Discard objects that don't verify any filters
		assertFalse(filter1Or2.accept(new Tag("")));
		assertFalse(filter1Or2.accept(new Tag("1234")));

		// Accept objects that verify at least one filters
		assertTrue(filter1Or2.accept(new Tag("1")));
		assertTrue(filter1Or2.accept(new Tag("12")));
		assertTrue(filter1Or2.accept(new Tag("123")));

		
		// Testing filter 1 OR 2 OR 3
		// Discard null objects
		assertFalse(filter1Or2Or3.accept(null));
		
		// Discard objects that don't verify any filters
		assertFalse(filter1Or2Or3.accept(new Tag("")));
		assertFalse(filter1Or2Or3.accept(new Tag("123456")));

		// Accept objects that verify at least one filters
		assertTrue(filter1Or2Or3.accept(new Tag("1")));
		assertTrue(filter1Or2Or3.accept(new Tag("12")));
		assertTrue(filter1Or2Or3.accept(new Tag("123")));
		assertTrue(filter1Or2Or3.accept(new Tag("1234")));
		assertTrue(filter1Or2Or3.accept(new Tag("12345")));
	}

	public void testFilter() {
		// Initialization
		Filter<Tag> filter1 = new LengthFilter(1,2);
		Filter<Tag> filter2 = new LengthFilter(3,3);
		Filter<Tag> filter3 = new LengthFilter(4,5);
		Filter<Tag> filter1Or2 = new OrFilter<Tag>(filter1, filter2);
		Filter<Tag> filter1Or2Or3 = new OrFilter<Tag>(filter1Or2, filter3);
		
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
		filter1Or2Or3.filter(array);
		assertEquals(5, array.size());
		assertFalse(array.contains(null));
		assertFalse(array.contains(new Tag("")));
		assertTrue(array.contains(new Tag("1")));
		assertTrue(array.contains(new Tag("12")));
		assertTrue(array.contains(new Tag("123")));
		assertTrue(array.contains(new Tag("1234")));
		assertTrue(array.contains(new Tag("12345")));
		assertFalse(array.contains(new Tag("123456")));
	}

}
