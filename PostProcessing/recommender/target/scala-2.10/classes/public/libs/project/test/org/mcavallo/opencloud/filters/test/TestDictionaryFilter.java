package org.mcavallo.opencloud.filters.test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.mcavallo.opencloud.Tag;
import org.mcavallo.opencloud.filters.DictionaryFilter;
import org.mcavallo.opencloud.filters.Filter;

import junit.framework.TestCase;

public class TestDictionaryFilter extends TestCase {

	public void testDictionaryFilter() {
		Locale.setDefault(Locale.ENGLISH);
		Filter<Tag> filter = new DictionaryFilter();

		checkEnglish(filter);
	}

	public void testDictionaryFilterLocale() {
		Filter<Tag> filter = new DictionaryFilter(Locale.ENGLISH);
		
		checkEnglish(filter);
		
		Filter<Tag> filter2 = new DictionaryFilter(Locale.ITALY);
		
		checkItalian(filter2);
	}

	public void testDictionaryFilterString() {
		Filter<Tag> filter = new DictionaryFilter(ResourceBundle.getBundle("test_data/test_blacklist"));

		checkEnglish(filter);
	}

	public void testDictionaryFilterStringLocale() {
		Filter<Tag> filter = new DictionaryFilter(ResourceBundle.getBundle("test_data/test_blacklist", Locale.ENGLISH));
		
		checkEnglish(filter);
		
		Filter<Tag> filter2 = new DictionaryFilter(ResourceBundle.getBundle("test_data/test_blacklist", Locale.ITALY));
		
		checkItalian(filter2);
	}

	public void testDictionaryFilterCollectionOfQextendsString() {
		List<String> terms = new ArrayList<String>();
		terms.add("a");
		terms.add("an");
		terms.add("the");
		DictionaryFilter filter = new DictionaryFilter(terms);
		
		checkEnglish(filter);
	}

	public void testDictionaryFilterStringArray() {
		String[] terms = new String[] { "a", "an", "the" };
		DictionaryFilter filter = new DictionaryFilter(terms);
		
		assertEquals(terms.length, filter.getDictionary().size());
		
		checkEnglish(filter);
	}

	public void testDictionaryFilterInputStream() {
		DictionaryFilter filter;
		try {
			filter = new DictionaryFilter(new FileInputStream("test/test_data/test_blacklist.properties"));
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
			return;
		}
		
		assertEquals(3, filter.getDictionary().size());
		
		checkEnglish(filter);
	}

	public void testDictionaryFilterScanner() {
		DictionaryFilter filter = new DictionaryFilter(new Scanner("a\r\nan\r\nthe\r\n"));
		
		assertEquals(3, filter.getDictionary().size());
		
		checkEnglish(filter);
	}

	public void testAcceptTag() {
		Filter<Tag> filter = new DictionaryFilter(Locale.ENGLISH);
		
		// Accept null
		assertTrue(filter.accept(null));

		// Accept empty tag
		assertTrue(filter.accept(new Tag("")));
	}

	private void checkEnglish(Filter<Tag> filter) {
		// Discard words contained in the dictionary
		assertFalse(filter.accept(new Tag("a")));
		assertFalse(filter.accept(new Tag("an")));
		assertFalse(filter.accept(new Tag("the")));

		// Accept words not contained in the dictionary
		assertTrue(filter.accept(new Tag("sun")));
		assertTrue(filter.accept(new Tag("aa")));
	}

	private void checkItalian(Filter<Tag> filter) {
		// Discard words contained in the dictionary
		assertFalse(filter.accept(new Tag("il")));
		assertFalse(filter.accept(new Tag("nostro")));
		assertFalse(filter.accept(new Tag("che")));

		// Accept words not contained in the dictionary
		assertTrue(filter.accept(new Tag("sole")));
		assertTrue(filter.accept(new Tag("aa")));
	}

}
