package controllers;
public class TvShowSimPair {

	private TVShow tvShow;
	private double similarity;

	public TvShowSimPair(TVShow tv, Double sim) {
		this.tvShow = tv;
		this.similarity = sim;

	}

	public TVShow getTvShow() {
		return tvShow;

	}

	public String getTitle() {
		return tvShow.getName();
	}

	public double getSimilarity() {
		return similarity;
	}

	public String toString() {
		return tvShow.getName() + " : " + similarity + "|IMDB: "+tvShow.getRating();
	}

}
