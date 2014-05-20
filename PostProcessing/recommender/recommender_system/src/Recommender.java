import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Recommender 
{
	
	HashMap<String, Integer[]> mainMemory;
	
	
	
	public static void main(String[] args)
	{
		
		// For now on data :
		// -----------------
		// "Game of Throne"	0.233	0.140	0.045	0.0014	0.00123	0.0012345	0.000013455
		// "Breaking Bad"	0.233	0.140	0.045	0.0014	0.00123	0.0012345	0.000013455
		// "Homeland"	0.233	0.140	0.045	0.0014	0.00123	0.0012345	0.000013455
		// "House of Cards"	0.233	0.140	0.045	0.0014	0.00123	0.0012345	0.000013455
		// "NCIS"	0.233	0.140	0.045	0.0014	0.00123	0.0012345	0.000013455
		
		
		MatrixManager mm = new MatrixManager();
		mm.printMatrix();
		//Query q1 = new Query();
		//q1.addLike(mm.getTVShow("Game of Throne"));
		
		List<TVShow> result = recommand(mm.getTVShow("Breaking Bad"));
		
		
		
	}
	
	public static List<TVShow> recommand(TVShow likedtvs)
	{
		System.out.println(likedtvs);
		System.out.println("Recommendation on :"+likedtvs.getTitle());
		
		HashMap<String, TVShow> memory = MatrixManager.mainMemory;
		Set<String> keys = memory.keySet();
		
		HashMap<Double, TVShow> result = new HashMap<Double, TVShow>();
		List<TVShow> listOfResult = new LinkedList<TVShow>();

		for (String title : keys) 
		{
			System.out.println("Getting similarity result for : "+title);
			TVShow currenttvs = memory.get(title);
			result.put(similarity(likedtvs, currenttvs ), currenttvs);
		}
		
		System.out.println(result);
		
		
		List<Double> sortedKey = new ArrayList<Double>(result.keySet());
		Collections.sort(sortedKey);
		
		for (int i = 0; i < sortedKey.size(); i++) {
			TVShow toAdd = result.get(sortedKey.get(i));
			toAdd.setSim(sortedKey.get(i));
			listOfResult.add(toAdd);
			System.out.println("Sim = "+sortedKey.get(i)+" |    | Movie = "+result.get(sortedKey.get(i)));
		}

		Collections.reverse(listOfResult);
		System.out.println(listOfResult);
		return listOfResult;

	}
	
	public static double similarity(TVShow tv1, TVShow tv2)
	{
		double [] score1 = tv1.getScores();
		double [] score2 = tv2.getScores();
		
		double sim = 0;
		double norm1 = 0;
		double norm2 = 0;
		
		for(int i = 0 ; i < score1.length ; i++)
		{
			norm1 += Math.pow(score1[i], 2); 
			norm2 += Math.pow(score2[i], 2); 
			sim += score1[i]*score2[i];
		}
		
		return sim / (Math.sqrt(norm1)*Math.sqrt(norm2));
		
		
	}

}


