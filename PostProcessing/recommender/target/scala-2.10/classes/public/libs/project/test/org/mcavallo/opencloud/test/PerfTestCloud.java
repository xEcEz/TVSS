package org.mcavallo.opencloud.test;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import junit.framework.TestCase;

public class PerfTestCloud extends TestCase {

	public void testPeformance1() {
		int numTags = 100000;
		int numCycles = 10;
		double scoreStep = 1.0 / numTags;
		
		Cloud cloud = new Cloud();
		
		for (int c=0; c<numCycles; c++) {
			double score = 1.0;
			for (int t=0; t<numTags; t++) {
				String name = Integer.toString(t);
				cloud.addTag(new Tag(name, name, score));
				score += scoreStep;
			}
			cloud.tags();
		}
		
		// Indicative Execution Time:
		// (Processor, Processor frequency, Tags, Cycles -> Execution Time)
		// Pentium 4, 3 GHz, 100000, 10 -> 4,3 s
	}
	
}
