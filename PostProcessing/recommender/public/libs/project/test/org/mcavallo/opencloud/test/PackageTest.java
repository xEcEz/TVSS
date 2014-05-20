package org.mcavallo.opencloud.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PackageTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.mcavallo.opencloud.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestTag.class);
		suite.addTestSuite(TestCloud.class);
		//$JUnit-END$
		return suite;
	}

}
