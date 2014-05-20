package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import controllers.TVShow;

public class MatrixManager {

	public static HashMap<String, TVShow> mainMemory;
	private static final double RATING_NOT_FOUND = -1;

	public MatrixManager() {
		mainMemory = new HashMap<String, TVShow>();
		//loadMatrix();
		loadMatrix_mallet();
		fillComplementaryInfo();
	}

	public TVShow getTVShow(String title) {
		if (mainMemory.containsKey(title)) {
			return mainMemory.get(title);
		} else {
			return new TVShow();
		}
	}

	public void printMatrix() {
		for (String title : mainMemory.keySet()) {
			String scores = "Scores = ";

			for (int i = 0; i < mainMemory.get(title).getTopicsVector().length; i++) {
				scores += mainMemory.get(title).getTopicsVector()[i] + " | ";
			}

			System.out.println("key =" + title + " ___ " + scores);

		}
	}

	/**
	 * Take the imdb_rating_short.txt file and fill the imdb rating for each TV
	 * show in mainmemory.
	 */
	public void fillComplementaryInfo() {
		/*
		 * try { FileReader reader = new FileReader("data/imdb_ratings.json");
		 * JSONParser jsonParser = new JSONParser(); JSONArray jsonArray =
		 * (JSONArray) jsonParser.parse(reader);
		 *
		 * Iterator<JSONObject> iterator = jsonArray.iterator(); while
		 * (iterator.hasNext()) { JSONObject json = (JSONObject)
		 * iterator.next(); if (mainMemory.containsKey(json.get("title"))) {
		 * TVShow tv = mainMemory.get(json.get("title")); if
		 * (!json.get("rating").equals("not found")) {
		 * tv.setRating(Double.parseDouble((String) json .get("rating")));
		 * tv.setPlot((String) json.get("plot")); tv.setImageURL((String)
		 * json.get("poster")); tv.setGenre((String) json.get("genre"));
		 *
		 * }else{ tv.setRating(RATING_NOT_FOUND); } tv.toString(); } }
		 *
		 * } catch (FileNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		BufferedReader br = null;
		int i = 0;

		try {

			String sCurrentLine;
			String[] currentLineSplitted;

			br = new BufferedReader(new FileReader(
					"data/imdb_ratings_short.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				currentLineSplitted = sCurrentLine.split(",", 2);
				String title = currentLineSplitted[1];

				if (!currentLineSplitted[0].equals("not found")
						&& !currentLineSplitted[0].equals("N/A")) {
					double rating = Double.parseDouble(currentLineSplitted[0]);

					if (mainMemory.containsKey(title)) {

						TVShow tv = mainMemory.get(title);
						tv.setRating(rating);
					} else {
						i++;
					}
				}else{
					if(mainMemory.containsKey(title)){
						TVShow tv = mainMemory.get(title);
						tv.setRating(RATING_NOT_FOUND);
					}
				}

			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Take the data.txt file and transform it into a matrix of TV shows where
	 * each line is a TV show along with its topic vector.
	 */
	public void loadMatrix() {


		BufferedReader br = null;

		try {

			String sCurrentLine;
			String[] currentLineSplitted;

			br = new BufferedReader(new FileReader("data/data.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				currentLineSplitted = sCurrentLine.split("\t");

				/*
				 * avec l'ajout de grégory, data.txt contient un id pour chaque
				 * TVShow, juste avant le titre. double[] values = new
				 * double[currentLineSplitted.length - 2];
				 *
				 * for (int i = 2; i < currentLineSplitted.length; i++) {
				 * values[i - 2] = Double.parseDouble(currentLineSplitted[i]); }
				 *
				 * String title = currentLineSplitted[1];
				 */

				double[] values = new double[currentLineSplitted.length - 1];

				for (int i = 1; i < currentLineSplitted.length; i++) {
					values[i - 1] = Double.parseDouble(currentLineSplitted[i]);
				}

				String title = currentLineSplitted[0];
				mainMemory.put(title, new TVShow(title, values));

			}

		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMatrix_mallet() {


		BufferedReader br1 = null;
		BufferedReader br2 = null;

		try {

			String sCurrentLine1;
			String sCurrentLine2;
			String[] currentLineSplitted1;
			String[] currentLineSplitted2;


			br1 = new BufferedReader(new FileReader("data/topicProportions.txt"));
			br2 = new BufferedReader(new FileReader("data/shows.txt"));

			while ((sCurrentLine1 = br1.readLine()) != null && (sCurrentLine2 = br2.readLine()) != null ) {
				//System.out.println(sCurrentLine1 +" ||||||||||"+sCurrentLine2);
				currentLineSplitted1 = sCurrentLine1.split(" ");
				currentLineSplitted2 = sCurrentLine2.split("\t");

				/*
				 * avec l'ajout de grégory, data.txt contient un id pour chaque
				 * TVShow, juste avant le titre. double[] values = new
				 * double[currentLineSplitted.length - 2];
				 *
				 * for (int i = 2; i < currentLineSplitted.length; i++) {
				 * values[i - 2] = Double.parseDouble(currentLineSplitted[i]); }
				 *
				 * String title = currentLineSplitted[1];
				 */

				double[] values = new double[currentLineSplitted1.length];

				for (int i = 0; i < currentLineSplitted1.length; i++) {
					values[i] = Double.parseDouble(currentLineSplitted1[i]);
				}

				int id = Integer.parseInt(currentLineSplitted2[0]);
				String title = currentLineSplitted2[1];

				mainMemory.put(title, new TVShow(title, values, id));

			}

		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getListOfTitle() {
		return new ArrayList<String>(mainMemory.keySet());
	}

	public List<TVShow> getListOfTVShow() {
		return new ArrayList<TVShow>(mainMemory.values());
	}

}
