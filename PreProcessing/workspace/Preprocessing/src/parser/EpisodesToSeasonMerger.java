package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class EpisodesToSeasonMerger {
	private PrintWriter pw;
	private BufferedReader br;
	int status;
	
	public int mergeEipsodesToSeason(File in) {
		String prevParent = "";
		boolean isFirst = true;
		status = 0;
		String mergedSeasonName = "";
		
		Collection<File> files = FileUtils.listFiles(in, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for(File f : files) {
			if(f.getName().contains("parsed")) {
				if(!isFirst) {	
					if(f.getParent().equalsIgnoreCase(prevParent)) { // Same season
						copyContent(f.toPath().toString());
					} else {
						System.out.println("merged : " + mergedSeasonName);
						pw.flush();
						pw.close();
						try {
							mergedSeasonName = createSeasonName(f.getPath().toString());
							pw = new PrintWriter(new File(mergedSeasonName));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
							status = 1;
						}
						copyContent(f.toPath().toString());
					}
					
				} else {											// First season file, create it
					try {
						mergedSeasonName = createSeasonName(f.getPath().toString());
						pw = new PrintWriter(new File(mergedSeasonName));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						status = 1;
					}
					copyContent(f.toPath().toString());
					isFirst = false;
				}
				prevParent = f.getParent();
			}
		}
		pw.flush();
		pw.close();
		
		return status;
	}
	
	private void copyContent(String path) {
		String currentLine = "";
		try {
			br = new BufferedReader(new FileReader(path));
			while((currentLine = br.readLine()) != null) {
				pw.println(currentLine);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			status = 1;
		}
	}

	private String createSeasonName(String path) {
		String returnString = "";
		String tmp = "";
		boolean done = false;
		
		StringTokenizer st = new StringTokenizer(path, "/");
		while(st.hasMoreTokens() && !done) {
			tmp = st.nextToken();
			returnString += tmp + "/";
			if(tmp.equalsIgnoreCase("parsed2")) {
				returnString += st.nextToken() + "/" + st.nextToken() + ".season";
				done = true;
			} 
		}
		return returnString;
	}
}
