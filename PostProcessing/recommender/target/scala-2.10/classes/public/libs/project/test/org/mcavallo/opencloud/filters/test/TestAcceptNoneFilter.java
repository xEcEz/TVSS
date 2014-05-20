/**
 * 
 */
package org.mcavallo.opencloud.filters.test;

import java.util.ArrayList;
import java.util.List;

import org.mcavallo.opencloud.filters.AcceptNoneFilter;
import org.mcavallo.opencloud.filters.Filter;

import junit.framework.TestCase;

public class TestAcceptNoneFilter extends TestCase {

	public void testAccept() {
		// Initialization
		Filter<String> filter = new AcceptNoneFilter<String>();
		
		// Discard any object
		assertFalse(filter.accept(null));
		assertFalse(filter.accept(new String("")));
		assertFalse(filter.accept(new String("abc123\r\n")));
	}

	public void testFilter() {
		// Initialization
		Filter<String> filter = new AcceptNoneFilter<String>();
		
		List<String> array = new ArrayList<String>();
		array.add(null);
		array.add("");
		array.add("abc123\r\n");
		
		// Empties the array
		filter.filter(array);
		assertEquals(0, array.size());
	}

}
