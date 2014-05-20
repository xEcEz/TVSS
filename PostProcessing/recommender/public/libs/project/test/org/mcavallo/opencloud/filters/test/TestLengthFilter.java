/**
 * 
 */
package org.mcavallo.opencloud.filters.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.mcavallo.opencloud.Tag;
import org.mcavallo.opencloud.filters.Filter;
import org.mcavallo.opencloud.filters.LengthFilter;

import com.thoughtworks.xstream.XStream;

import junit.framework.TestCase;

public class TestLengthFilter extends TestCase {

	public void testAccept() {
		// Initialization
		Filter<Tag> filter = new LengthFilter(2,5);
		
		// Discard null objects
		assertFalse(filter.accept(null));

		// Discard too short tags
		assertFalse(filter.accept(new Tag("")));
		assertFalse(filter.accept(new Tag("1")));

		// Discard too long tags
		assertFalse(filter.accept(new Tag("123456")));

		// Accept tags within limits
		assertTrue(filter.accept(new Tag("12")));
		assertTrue(filter.accept(new Tag("12345")));
	}

	public void testFilter() {
		// Initialization
		Filter<Tag> filter = new LengthFilter(2,5);
		
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
		assertEquals(4, array.size());
		assertFalse(array.contains(null));
		assertFalse(array.contains(new Tag("")));
		assertFalse(array.contains(new Tag("1")));
		assertTrue(array.contains(new Tag("12")));
		assertTrue(array.contains(new Tag("123")));
		assertTrue(array.contains(new Tag("1234")));
		assertTrue(array.contains(new Tag("12345")));
		assertFalse(array.contains(new Tag("123456")));
	}

	public void testSerialization() {
		try {
			// Create a filter
			LengthFilter filter1 = new LengthFilter(2,5);
			
			// Save the filter in a file
			FileOutputStream fos = new FileOutputStream("test/test_data/filter1.tmp");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(filter1);
	        oos.close();
	        
			// Read the filter from the file
	        LengthFilter filter2;
	        FileInputStream fis = new FileInputStream("test/test_data/filter1.tmp");
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        filter2 = (LengthFilter) ois.readObject();
	        ois.close();
	        
	        // Check whether the two filters are equal
	        assertEquals(filter1, filter2);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	public void testXMLSerialization() {
		try {
			// Create a filter
			XStream xstream = new XStream();
			LengthFilter filter1 = new LengthFilter(2,5);
			
			// Convert the filter to XML
			String xml1 = xstream.toXML(filter1);
			
			// Read from XML
			LengthFilter filter2 = (LengthFilter) xstream.fromXML(xml1);

			// Check whether the two filters are equal
	        assertEquals(filter1, filter2);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
