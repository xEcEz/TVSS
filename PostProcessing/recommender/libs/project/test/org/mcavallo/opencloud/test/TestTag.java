/**
 * 
 */
package org.mcavallo.opencloud.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.mcavallo.opencloud.Tag;

import com.thoughtworks.xstream.XStream;

import junit.framework.TestCase;

public class TestTag extends TestCase {

	public void testTagConstructors() {
		String name = "pippo";
		String link = "http://www.google.com";
		double score = 2.5;
		Date date = new Date(1000000);
		Tag tag;
		
		// Check default constructor existence
		tag = new Tag();
		
		// Check other constructors
		tag = new Tag(name);
		assertEquals(name, tag.getName());

		tag = new Tag(name, link);
		assertEquals(name, tag.getName());
		assertEquals(link, tag.getLink());

		tag = new Tag(name, link, date);
		assertEquals(name, tag.getName());
		assertEquals(link, tag.getLink());
		assertEquals(date, tag.getDate());

		tag = new Tag(name, link, score);
		assertEquals(name, tag.getName());
		assertEquals(link, tag.getLink());
		assertEquals(score, tag.getScore());

		tag = new Tag(name, link, score, date);
		assertEquals(name, tag.getName());
		assertEquals(link, tag.getLink());
		assertEquals(score, tag.getScore());
		assertEquals(date, tag.getDate());
		
		// Test copy constructor
		tag = new Tag(new Tag(name, link, score, date));
		assertEquals(name, tag.getName());
		assertEquals(link, tag.getLink());
		assertEquals(score, tag.getScore());
		assertEquals(date, tag.getDate());
	}
	
	public void testTagOperators() {
		String name = "pippo";
		String link = "http://www.google.com";
		Tag tag = new Tag(name, link);
		
		// Addition
		tag.setScore(2.0);
		tag.add(3.0);
		assertEquals(5.0, tag.getScore(), 0.001);
		
		// Division
		tag.setScore(6.0);
		tag.divide(3.0);
		assertEquals(2.0, tag.getScore(), 0.001);
		
		// Multiplication
		tag.setScore(2.0);
		tag.multiply(3.0);
		assertEquals(6.0, tag.getScore(), 0.001);

		// Normalization
		tag.setScore(2.0);
		tag.normalize(5.0);
		assertEquals(0.4, tag.getNormScore(), 0.001);
	}
	
	public void testEqualsObject() {
		Tag t1, t2, t3, t4, t5;
		
		t1 = new Tag("Test", "url");
		t2 = new Tag("Test", "url2");
		t3 = new Tag("Test2", "url");
		t4 = new Tag("Test2", "url2");

		// Tags are equal if (and only if) they have the same name
		assertTrue(t1.equals(t2));
		assertTrue(t2.equals(t1));
		assertTrue(! t1.equals(t3));
		assertTrue(! t3.equals(t1));
		assertTrue(! t1.equals(t4));
		assertTrue(! t4.equals(t1));

		t1 = new Tag(null,"url");
		t2 = new Tag(null,"url2");
		t3 = new Tag("Test",null);
		t4 = new Tag("Test","url");
		t5 = new Tag("Test2",null);
		
		// The name or the link can be null
		assertTrue(t1.equals(t2));
		assertTrue(t2.equals(t1));
		assertTrue(! t1.equals(t3));
		assertTrue(! t3.equals(t1));
		assertTrue(t3.equals(t4));
		assertTrue(t4.equals(t3));
		assertTrue(! t3.equals(t5));
		assertTrue(! t5.equals(t3));
	}

	public void testSerialization() {
		try {
			// Create a tag
			Tag tag1 = new Tag("pippo","http://www.google.com",15.0,new Date(1000000));
			
			// Save the tag in a file
			FileOutputStream fos = new FileOutputStream("test/test_data/tag1.tmp");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tag1);
	        oos.close();
	        
			// Read the tag from the file
	        Tag tag2;
	        FileInputStream fis = new FileInputStream("test/test_data/tag1.tmp");
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        tag2 = (Tag) ois.readObject();
	        ois.close();
	        
	        // Check whether the two tags are equal
	        assertEquals(tag1, tag2);
			assertEquals(tag1.getName(), tag2.getName());
			assertEquals(tag1.getLink(), tag2.getLink());
			assertEquals(tag1.getScore(), tag2.getScore());
			assertEquals(tag1.getDate(), tag2.getDate());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	public void testSerializationCompatibility() {
		try {
			// Create reference tag
			Tag tag_ref = new Tag("pippo","http://www.google.com",15.0,new Date(1000000));
			
			// Read the tag from the file
	        Tag tag;
	        FileInputStream fis = new FileInputStream("test/test_data/tag_ref.dat");
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        tag = (Tag) ois.readObject();
	        ois.close();
	        
	        // Check whether the two tags are equal
	        assertEquals(tag_ref, tag);
			assertEquals(tag_ref.getName(), tag.getName());
			assertEquals(tag_ref.getLink(), tag.getLink());
			assertEquals(tag_ref.getScore(), tag.getScore());
			assertEquals(tag_ref.getDate(), tag.getDate());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	public void testXMLSerialization() {
		try {
			// Create a tag
			XStream xstream = new XStream();
			Tag tag1 = new Tag("pippo","http://www.google.com",15.0,new Date(1000000));
			
			// Convert the tag to XML
			String xml1 = xstream.toXML(tag1);
			
			// Read from XML
			Tag tag2 = (Tag) xstream.fromXML(xml1);

			// Check whether the two tags are equal
	        assertEquals(tag1, tag2);
			assertEquals(tag1.getName(), tag2.getName());
			assertEquals(tag1.getLink(), tag2.getLink());
			assertEquals(tag1.getScore(), tag2.getScore());
			assertEquals(tag1.getDate(), tag2.getDate());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	public void testXMLSerializationCompatibility() {
		try {
			// Create reference tag
			Tag tag_ref = new Tag("pippo","http://www.google.com",15.0,new Date(1000000));
			
			// Read the tag from the XML file
	        FileInputStream fis = new FileInputStream("test/test_data/tag_ref.xml");
	        XStream xstream = new XStream();
	        Tag tag = (Tag) xstream.fromXML(fis);
	        
	        // Check whether the two tags are equal
	        assertEquals(tag_ref, tag);
			assertEquals(tag_ref.getName(), tag.getName());
			assertEquals(tag_ref.getLink(), tag.getLink());
			assertEquals(tag_ref.getScore(), tag.getScore());
			assertEquals(tag_ref.getDate(), tag.getDate());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
