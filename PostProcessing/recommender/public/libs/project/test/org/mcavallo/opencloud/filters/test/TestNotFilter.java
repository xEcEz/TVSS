/**
 * 
 */
package org.mcavallo.opencloud.filters.test;

import java.util.ArrayList;
import java.util.List;

import org.mcavallo.opencloud.Tag;
import org.mcavallo.opencloud.filters.Filter;
import org.mcavallo.opencloud.filters.LengthFilter;
import org.mcavallo.opencloud.filters.NotFilter;

import junit.framework.TestCase;

public class TestNotFilter extends TestCase {

	public void testAccept() {
		// Initialization
		Filter<Tag> filter1 = new LengthFilter(2,3);
		Filter<Tag> filterNot1 = new NotFilter<Tag>(filter1);
		
		// Checks that it gives complementary results  
		// note: null object are accepted! 
		assertEquals(! filter1.accept(null), filterNot1.accept(null));
		assertEquals(! filter1.accept(new Tag("")), filterNot1.accept(new Tag("")));
		assertEquals(! filter1.accept(new Tag("1")), filterNot1.accept(new Tag("1")));
		assertEquals(! filter1.accept(new Tag("12")), filterNot1.accept(new Tag("12")));
		assertEquals(! filter1.accept(new Tag("123")), filterNot1.accept(new Tag("123")));
		assertEquals(! filter1.accept(new Tag("1234")), filterNot1.accept(new Tag("1234")));
	}

	public void testFilter() {
		// Initialization
		Filter<Tag> filter1 = new LengthFilter(2,3);
		Filter<Tag> filterNot1 = new NotFilter<Tag>(filter1);
		
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
		filterNot1.filter(array);
		assertEquals(6, array.size());
		assertTrue(array.contains(null));
		assertTrue(array.contains(new Tag("")));
		assertTrue(array.contains(new Tag("1")));
		assertFalse(array.contains(new Tag("12")));
		assertFalse(array.contains(new Tag("123")));
		assertTrue(array.contains(new Tag("1234")));
		assertTrue(array.contains(new Tag("12345")));
		assertTrue(array.contains(new Tag("123456")));
	}

}
