package controllers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import controllers.MapUtil;
import controllers.MatrixManager;

import models.UserShow;


public class Recommender {

	HashMap<String, Integer[]> mainMemory;
	/**
	 * Number of recommandation to output.
	 */
	private static final int MAX_RECOMMANDATION = 15;

	public static void main(String[] args) {

		MatrixManager mm = new MatrixManager();
		//mm.printMatrix();
		Query q1 = new Query();
		q1.addLike(mm.getTVShow("Dexter"));

		List<TvShowSimPair> result = recommand(q1);
		

	}

	public static List<TvShowSimPair> start(List<UserShow> tvlist) {

		MatrixManager mm = new MatrixManager();
		Query q = new Query();
		for (UserShow userShow : tvlist) {
			q.addLike(mm.getTVShow(userShow.label));
		}

		List<TvShowSimPair> result = recommand(q);
		return result;
	}

	/**
	 * 
	 * @param likedtvs
	 * @return a list of the TvShows in decreasing order regarding the
	 *         similarity with likedtvs
	 */
	public static List<TvShowSimPair> recommand(TVShow likedtvs) {
		HashMap<String, TVShow> memory = MatrixManager.mainMemory;
		Set<String> keys = memory.keySet();

		HashMap<TVShow, Double> result = new HashMap<TVShow, Double>();
		
		Map<TVShow, Double> sortedResult = new LinkedHashMap<TVShow, Double>();
		List<TvShowSimPair> listOfResult = new ArrayList<TvShowSimPair>();

		for (String title : keys) {
			TVShow currenttvs = memory.get(title);
			result.put(currenttvs,likedtvs.compare(currenttvs));

		}

		List<Double> sortedKey = new ArrayList<Double>(result.values());
		Collections.sort(sortedKey);
		
		sortedResult = MapUtil.sortByValue(result);
		

		for(TVShow tv:sortedResult.keySet()){
			if(!tv.getName().equals(likedtvs.getName())){
				listOfResult.add(new TvShowSimPair(tv, sortedResult.get(tv)));

			}
		}
		/*
		for (int i = 0; i < sortedResult.size(); i++) {
			if (!sortedResult.g
					.equals(likedtvs.getName())) {
				TVShow toAdd = sortedResult.get(i).getKey();
				listOfResult.add(new TvShowSimPair(toAdd, sortedKey.get(i)));
			}
		}
		*/

		Collections.reverse(listOfResult);
		return listOfResult;

	}

	/**
	 * Compute MAX_RECOMMANDATION recommendations for query q. Remove the
	 * TVShows given in the query from the recommended result. At each step,
	 * choose from the recommendation given for each TVShow, the one with the
	 * highest similarity value.
	 * 
	 * @param q
	 *            query containing a list of tvshows for wich we want a
	 *            recommandation
	 * @return
	 */
	public static List<TvShowSimPair> recommand(Query q) {
		List<List<TvShowSimPair>> recommandationForEachTvShow = new ArrayList<List<TvShowSimPair>>();
		List<TvShowSimPair> listOfResult = new ArrayList<TvShowSimPair>();
		List<String> listOfResultTitles = new ArrayList<String>();

		int i = 0;
		for (TVShow tv : q.getLiked()) {
			recommandationForEachTvShow.add(recommand(tv));
		}

		while (i < MAX_RECOMMANDATION) {
			int maxPosition = -1;
			double maxSimilarity = 0;
			for (int j = 0; j < recommandationForEachTvShow.size(); j++) {
				if (!recommandationForEachTvShow.get(j).isEmpty()) {
					TvShowSimPair topRecommandationTvShow = recommandationForEachTvShow
							.get(j).get(0);
					if (topRecommandationTvShow.getSimilarity() >= maxSimilarity) {
						maxSimilarity = recommandationForEachTvShow.get(j)
								.get(0).getSimilarity();
						maxPosition = j;
					}
				}
			}
			if (maxPosition != -1) {
				TvShowSimPair maxEntry = recommandationForEachTvShow.get(
						maxPosition).get(0);

				// ------ICI
				if (!q.getLiked().contains(maxEntry.getTvShow())) {
					if (!listOfResultTitles.contains(maxEntry.getTitle())) {
						listOfResult.add(maxEntry);
						listOfResultTitles.add(maxEntry.getTitle());
					}
				}
				recommandationForEachTvShow.get(maxPosition).remove(0);
				if (recommandationForEachTvShow.get(maxPosition).isEmpty()) {
					recommandationForEachTvShow.remove(maxPosition);
				}
			}
			i++;
		}
		return listOfResult;
	}
	
	public static List<TvShowSimPair> sortByRating(List<TvShowSimPair> tvList){
		HashMap<TvShowSimPair, Double> toBeSorted = new HashMap<TvShowSimPair, Double>();
		Map<TvShowSimPair, Double> intermediateSort = new LinkedHashMap<TvShowSimPair, Double>();
		List<TvShowSimPair> result = new ArrayList<TvShowSimPair>();

		for(TvShowSimPair tv: tvList){
			toBeSorted.put(tv, tv.getTvShow().getRating());
		}
		
		intermediateSort = MapUtil.sortByValue(toBeSorted);
		for(TvShowSimPair tvSim:intermediateSort.keySet()){
			result.add(tvSim);
		}
		Collections.reverse(result);
		return result;
	}

}
