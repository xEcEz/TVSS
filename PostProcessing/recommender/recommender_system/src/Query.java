import java.util.ArrayList;
import java.util.List;


public class Query {
	
	private List<TVShow> liked;
	
	public Query()
	{
		liked = new ArrayList<TVShow>();
	}
	
	public void addLike(TVShow tvs)
	{
		liked.add(tvs);
	}

	public List<TVShow> getLiked() {
		return liked;
	}
}
