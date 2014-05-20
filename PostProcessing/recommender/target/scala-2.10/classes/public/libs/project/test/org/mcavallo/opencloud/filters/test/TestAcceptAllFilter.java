/**
 * 
 */
package org.mcavallo.opencloud.filters.test;

import java.util.ArrayList;
import java.util.List;

import org.mcavallo.opencloud.filters.AcceptAllFilter;
import org.mcavallo.opencloud.filters.Filter;

import junit.framework.TestCase;

public class TestAcceptAllFilter extends TestCase {

	public void testAccept() {
		// Initialization
		Filter<String> filter = new AcceptAllFilter<String>();
		
		// Accept null objects
		assertTrue(filter.accept(null));

		// Accept any object
		assertTrue(filter.accept(new String("")));
		assertTrue(filter.accept(new String("abc123\r\n")));
	}

	public void testFilter() {
		// Initialization
		Filter<String> filter = new AcceptAllFilter<String>();
		
		List<String> array = new ArrayList<String>();
		array.add(null);
		array.add("");
		array.add("abc123\r\n");
		
		// Array remains unchanged
		List<String> oldarray =  new ArrayList<String>(array);
		filter.filter(array);
		assertEquals(oldarray, array);
	}

}
