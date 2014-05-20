package controllers;
public class TVShow {
	
	private String name;
	private double[] topicsVector;
	private String plot;
	private double rating;
	private String imageURL;
	private String genre;
	private int id;
	
	
	
	public TVShow(String name, double[] topics){
		this.name = name;
		this.topicsVector = topics;
		this.id = -1;
		
	}
	
	public TVShow(String name, double[] topics, int id){
		this.name = name;
		this.topicsVector = topics;
		this.id = id;
		
	}
	
	public TVShow(){
		this.name = "N/A";
		this.topicsVector = null;
				
	}


	public String getName() {
		return name;
	}
	
	public double norme(){
		double norme = 0;
		for(int i = 0 ; i < topicsVector.length ; i++)
		{
			norme += Math.pow(topicsVector[i], 2); 
		}
		return Math.sqrt(norme);
	}
	


	public double[] getTopicsVector() {
		return topicsVector;
	}
	
	public void setPlot(String plot) {
		this.plot = plot;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public double getRating(){
		return rating;
	}


	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	
	public double compare(TVShow tv){
		double result = 0;
		
		for(int i = 0; i<topicsVector.length; i++){
				result +=topicsVector[i]*tv.getTopicsVector()[i];
			}
		
		result = (float) (result/(norme()*tv.norme()));
		return result;
	}
	
	@Override
	public String toString(){
		
		return name + plot;
	}
	
	public String getImageURL() {
		return imageURL;
	}

	public String getGenre() {
		return genre;
	}
	
	public int getId() {
		return id;
	}

}
