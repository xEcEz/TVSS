package parser;
import java.io.File;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;


public class Run {

	/** SubtitlesParser
	 * @param args
	 */
	public static void main(String[] args) {
		File input = new File("../../data/subtitles2/");
		String output = "../../data/parsed2/";
		Parser parser = new Parser(output);
		EpisodesToSeasonMerger episodesMerger = new EpisodesToSeasonMerger();
		SeasonsToShowMerger seasonsMerger = new SeasonsToShowMerger();

		Collection<File> files = FileUtils.listFiles(input, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for(File f : files) {
			System.out.println("handling file : " + f.toPath());
			parser.parse(f.toPath().toString());
		}
		
		episodesMerger.mergeEipsodesToSeason(new File(output));
		seasonsMerger.mergeSeasonsToShow(new File(output));

	}

}
