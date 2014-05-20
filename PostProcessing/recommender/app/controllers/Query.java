package controllers;
import java.util.ArrayList;
import java.util.List;


public class Query {
	
	private List<TVShow> liked;
	private List<String> likedTitles;
	
	public Query()
	{
		liked = new ArrayList<TVShow>();
		likedTitles = new ArrayList<String>();
	}
	
	public void addLike(TVShow tvs)
	{
		liked.add(tvs);
		likedTitles.add(tvs.getName());
	}

	public List<TVShow> getLiked() {
		return liked;
	}
	
	public List<String> getLikedTitle(){
		return likedTitles;
	}
}
