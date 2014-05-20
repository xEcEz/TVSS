
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * this class deals with the reads and writes from the hdfs
 * @author khalilhajji
 *
 */
public class FileSystemHandler {

	/**
	 * 
	 * @param fileName
	 * @param matrix in the form v1 v2 ... vn
	 * 							 v1 v2 ... vn
	 * 
	 * write it in the form: v1,v2,...,vn
	 * 						 v1,v2,...,vn
	 */
	public static void writeMatrix(String fileName, double[][] matrix){

		try {
			Path path = new Path(fileName);
			FileSystem fs = getFileSystem();
			
			OutputStreamWriter os = new OutputStreamWriter(fs.create(path,true));

			BufferedWriter br=new BufferedWriter(os);


			String line ="";
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					line = line + matrix[i][j] +" "; 

				}
				//remove the last space
				line = line.substring(0, line.length()-1);
				//add the line jump
				line=line+"\n";
				
				br.write(line);
				line = "";

			}
			
			br.close();


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public static void writeVector(String fileName, double[]vector){
		try {
			Path path = new Path(fileName);
			FileSystem fs = getFileSystem();
			
			OutputStreamWriter os = new OutputStreamWriter(fs.create(path,true));
			BufferedWriter br=new BufferedWriter(os);

			String line ="";
			for (int i = 0; i < vector.length; i++) {

				line = line + vector[i] +" "; 

			}
			//remove the last space
			line = line.substring(0, line.length()-1);
			br.write(line);
			
			br.close();



		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}


	}

	public static void deletePath(String fileName){
		try {
			FileSystem fs = getFileSystem();
			fs.delete(new Path(fileName),true);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 * takes the output of the reducers and generate the gamma file, the lambda file and the gradient file
	 * in the good format
	 * @param pathJobOutput
	 * @param pathToLambda
	 * @param pathToGamma
	 * @param pathToGradient
	 */
	public static void convertJobOutputToLambdaGammaGradient(String pathJobOutput, String pathToLambda, String pathToGamma, String pathToGradient) {
		//the lambda matrix that will be constituted
		double[][]lambda = new double[Parameters.sizeOfVocabulary][Parameters.numberOfTopics];


		//the gamma matrix that will be constituted
		double[][] gamma = new double [Parameters.numberOfDocuments][Parameters.numberOfTopics];

		//may be needed if we find some keys with value -1;
		double[] delta = new double [Parameters.numberOfTopics];



		Path path = new Path(pathJobOutput);
		try {
			FileSystem fs = FileSystem.get(new Configuration());
			FileStatus[] listFileStatus = fs.listStatus(path);


			for (int i = 0; i < listFileStatus.length; i++) {
				//for each file read all the values and put them in the matrix
				Path fileName = listFileStatus[i].getPath();
				if(!fileName.toString().contains("_SUCESS") && !fileName.toString().contains("crc") && !fileName.toString().contains("_logs")){


					
					InputStreamReader is = new InputStreamReader(fs.open(fileName));

					BufferedReader br=new BufferedReader(is);
					String line = br.readLine();

					while (line != null) {
						String[] stringArray = line.split(",|\\s");

						int keyType = new Integer(stringArray[0]);
						if(keyType == 1) {
							//then it is the lambda

							lambda[new Integer(stringArray[2])][new Integer(stringArray[1])]= new Double(stringArray[3]);
						} else if(keyType == 2) {
							//then it is the delta

							delta[new Integer(stringArray[2])] = new Double(stringArray[3]);
						} else if(keyType == 3) {
							//then it is the gamma
							gamma[new Integer(stringArray[2])][new Integer(stringArray[1])]= new Double(stringArray[3]);
						}else {
							//should never be in that branch
							System.err.println("unexpected branch in FileSystemHandler.generateLambdaGammaGradient()");
						}
						line=br.readLine();

					}
					//normalize lambda



					//do the write
					writeMatrix(pathToLambda, lambda);

					writeVector(pathToGradient, delta);

					writeMatrix(pathToGamma, gamma);



				}

			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public static double[][] loadGammas(String fileName, int numberOfDocuments,int numberOfTopics){
		
		double[][] gammas = loadMatrix(fileName,numberOfDocuments, numberOfTopics);
		return gammas;
	}

	public static double[][] loadLambdas(String fileName, int sizeVocab, int numberOfTopics){
		double[][] lambdas = loadMatrix(fileName, sizeVocab, numberOfTopics);
		return lambdas;
	}

	public static double[] loadAlpha(String fileName, int numberOfTopics){
		double[]alpha = loadVector(fileName, numberOfTopics);
		return alpha;
	}

	public static double[] loadGradient(String fileName){
		double[]gradient = loadVector(fileName, Parameters.numberOfTopics);
		return gradient;
	}

	private static double[] loadVector(String fileName, int size){
		BufferedReader br;
		try {
			FileSystem fs = getFileSystem();
			Path path = new Path(fileName);
			InputStreamReader is = new InputStreamReader(fs.open(path));
			br=new BufferedReader(is);

			String line=br.readLine();

			double[]vector = new double[size];

			String[] stringArray = line.split(" ");


			for (int i = 0; i < stringArray.length; i++) {
				vector[i] = new Double(stringArray[i]);
			}

			br.close();
			return vector;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		} catch(IndexOutOfBoundsException e) {

			e.printStackTrace();
			return null;

		}

	}

	private static double[][] loadMatrix(String fileName, int sizeRows, int sizeColumns) {
		BufferedReader br;
		try {

			FileSystem fs = getFileSystem();
			Path path = new Path(fileName);
			InputStreamReader is = new InputStreamReader(fs.open(path));

			br=new BufferedReader(is);

			String line=br.readLine();

			double[][] matrix = new double[sizeRows][sizeColumns];
			int ind = 0;
			while (line != null) {

				String[] stringArray = line.split(" ");
				double[] row = new double[sizeColumns];

				for (int i = 0; i < stringArray.length; i++) {
					Double val = Double.valueOf(stringArray[i]);
					row[i] = (double)val;
				}

				matrix[ind] = row;
				ind++;

				line = br.readLine();

			}

			if (ind != matrix.length) {

				throw new IndexOutOfBoundsException();
			}

			br.close();
			return matrix;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;

		} catch(IndexOutOfBoundsException e) {

			e.printStackTrace();

			return null;

		}

	}



	private static FileSystem getFileSystem() throws IOException {
		return FileSystem.get(new Configuration());
	}


}
