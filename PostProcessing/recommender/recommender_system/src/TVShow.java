
public class TVShow {
	
	String title;
	double[] scores;
	double sim;
	
	public TVShow(String title, double[] scores)
	{
		this.title = title;
		this.scores = scores;
	}

	public String getTitle() {
		return title;
	}


	public double[] getScores() {
		return scores;
	}
	
	public void setSim(double sim)
	{
		this.sim = sim;
	}
	

}
