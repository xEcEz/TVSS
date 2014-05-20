/**
 * 
 */
package org.mcavallo.opencloud.filters.test;

import java.util.ArrayList;
import java.util.List;

import org.mcavallo.opencloud.Tag;
import org.mcavallo.opencloud.filters.Filter;
import org.mcavallo.opencloud.filters.RegExFilter;

import junit.framework.TestCase;

public class TestRegExFilter extends TestCase {

	public void testAccept() {
		// Initialization
		Filter<Tag> filter = new RegExFilter("[abc][123]");
		
		// Discard null objects
		assertFalse(filter.accept(null));

		// Discard tags that don't match the regular expression
		assertFalse(filter.accept(new Tag("")));
		assertFalse(filter.accept(new Tag("d")));
		assertFalse(filter.accept(new Tag("1")));
		assertFalse(filter.accept(new Tag("1a")));
		assertFalse(filter.accept(new Tag("d1")));
		assertFalse(filter.accept(new Tag("a1a")));

		// Accept tags that match the regular expression
		assertTrue(filter.accept(new Tag("a1")));
		assertTrue(filter.accept(new Tag("b3")));
	}

	public void testFilter() {
		// Initialization
		Filter<Tag> filter = new RegExFilter("[abc][123]");
		
		List<Tag> array = new ArrayList<Tag>();
		array.add(null);
		array.add(new Tag(""));
		array.add(new Tag("1"));
		array.add(new Tag("a1"));
		array.add(new Tag("a1a"));
		
		// Check filtered array
		assertEquals(5, array.size());
		filter.filter(array);
		assertEquals(1, array.size());
		assertFalse(array.contains(null));
		assertFalse(array.contains(new Tag("")));
		assertFalse(array.contains(new Tag("1")));
		assertTrue(array.contains(new Tag("a1")));
		assertFalse(array.contains(new Tag("a1a")));
	}

}
