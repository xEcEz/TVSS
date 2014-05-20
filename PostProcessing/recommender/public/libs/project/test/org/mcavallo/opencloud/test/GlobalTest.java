package org.mcavallo.opencloud.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class GlobalTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Complete project test");
		//$JUnit-BEGIN$
		suite.addTest(org.mcavallo.opencloud.test.PackageTest.suite());
		suite.addTest(org.mcavallo.opencloud.filters.test.PackageTest.suite());
		//$JUnit-END$
		return suite;

		// Indicative Execution Time:
		// Pentium 4, 3 GHz -> 0,375 s
	}

}
