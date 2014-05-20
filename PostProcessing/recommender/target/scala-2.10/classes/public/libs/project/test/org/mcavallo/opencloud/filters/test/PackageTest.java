package org.mcavallo.opencloud.filters.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PackageTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.mcavallo.opencloud.filters.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestDictionaryFilter.class);
		suite.addTestSuite(TestRegExFilter.class);
		suite.addTestSuite(TestAcceptAllFilter.class);
		suite.addTestSuite(TestAndFilter.class);
		suite.addTestSuite(TestAcceptNoneFilter.class);
		suite.addTestSuite(TestOrFilter.class);
		suite.addTestSuite(TestMinLengthFilter.class);
		suite.addTestSuite(TestNotFilter.class);
		suite.addTestSuite(TestMaxLengthFilter.class);
		suite.addTestSuite(TestNonNullFilter.class);
		suite.addTestSuite(TestLengthFilter.class);
		//$JUnit-END$
		return suite;
	}

}
