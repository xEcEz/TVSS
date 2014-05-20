import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import controllers.Application;


public class MatrixManager 
{
	
	static HashMap<String, TVShow> mainMemory;
	
	
	public MatrixManager()
	{
		mainMemory = new HashMap<String,TVShow>();
		loadMatrix();
	}
	
	public TVShow getTVShow(String title)
	{
		return mainMemory.get(title);
	}
	
	public void printMatrix()
	{
		for (String title : mainMemory.keySet()) 
		{
			String scores = "Scores = ";
			
			for (int i = 0; i < mainMemory.get(title).getScores().length; i++) 
			{
				scores += mainMemory.get(title).getScores()[i]+" | ";
			}
			
			System.out.println("key ="+title+" ___ "+scores);
			
		}
	}
	
	public void loadMatrix()
	{
		
		BufferedReader br = null;
		 
		try 
		{
 
			String sCurrentLine;
			String[] currentLineSplitted;

 
			br = new BufferedReader(new FileReader("data/data.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) 
			{
				currentLineSplitted = sCurrentLine.split("\t");
				
				double[] values = new double[currentLineSplitted.length-1];
				
				for(int i = 1 ; i<currentLineSplitted.length ; i++)
				{
					values[i-1] = Double.parseDouble(currentLineSplitted[i]);
				}
					
				String title = currentLineSplitted[0].substring(1,currentLineSplitted[0].length()-1);
				//add each title to the choices available for the users
				Application.choices.add(title);
				mainMemory.put(title ,new TVShow(title, values));
				
			}
 
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
	}
	

}
