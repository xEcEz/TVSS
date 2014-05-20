import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.math.special.Gamma;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;


public class LdaMapper {



	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, DoubleWritable> {

		//A few useful parameters retrieved from class Parameters.
		private static int K;
		private static int V;
		private static int D;
		private static int numberOfMaxGammaIterations;
		private static String pathToAlphas;
		private static String pathToGammas;
		private static String pathToLambdas;
		
		
		
		/**
		 * an array of V*K
		 */
		private double[][] lambda;

		/**
		 * an array of size D*K
		 */
		private double[][] gamma;
		
		/**
		 * an array of K
		 */
		private double[] alpha;
		
		/**
		 * an array of V*K
		 */
		private double[][] phi;
		
		/**
		 * an array of K
		 */
		private double[] sigma;




		private DoubleWritable outputValue = new DoubleWritable(1);
		private Text outputKey = new Text("");


		

		private void setPhiSigma(){

			phi = new double[V][K];
			sigma = new double[K];
			
		}

		private boolean convergenceTest(int num){
			return num < numberOfMaxGammaIterations ;

		}
		

		

		private void retrieveLambda(){
			/**
			 * TODO
			 * Read from file the value of lambda
			 */
			lambda = FileSystemHandler.loadLambdas(pathToLambdas, V, K);
			for(int k = 0; k < lambda[0].length; k++){
				double sum = 0.0;
				for (int v = 0; v < lambda.length; v++) {
					sum += lambda[v][k];
				}
				if (sum!=0) {
					for(int v = 0; v < lambda.length; v++){
						lambda[v][k] = lambda[v][k]/(double)sum;
					}
					
				} else {
					System.out.println("SUM OF LAMBDA IS EQUAL TO 0");
				}
				

			}


		}

		private void retrieveGamma(){
			/**
			 * TODO
			 * Read from file the value of Gamma
			 */
			gamma = FileSystemHandler.loadGammas(pathToGammas,D, K);

		}

		private void retrieveAlpha(){
			/**
			 * TODO
			 * Read from file the value of Alpha
			 */
			alpha = FileSystemHandler.loadAlpha(pathToAlphas, K);
			
		}
		
		/**
		 * 
		 * @param sigma
		 * @param phi
		 * @param frequency
		 * @param sumPhi
		 * @return sigma + wordFrequencies*phi
		 */
		private double[] updateSigma(double[]sigma, double[]phi, int frequency, double sumPhi ){
			double[]result = new double[K];
			for (int k = 0; k < phi.length; k++) {
				//nomalize phi
				phi[k] = phi[k]/sumPhi;
				
				//update sigma
				result[k] = sigma[k]+frequency*phi[k];
			}
			
			return result;
		}
		
		/**
		 * 
		 * @param sigma
		 * @param alpha
		 * @return sigma+alpha
		 */
		private double[] updateGamma(double[]sigma, double[] alpha) {
			double[]result = new double[K];
			
			//make the sum gamma = alpha+sigma
			for (int k = 0; k < result.length; k++) {
				result[k]=sigma[k]+alpha[k];
			}
			return result;
		}



		
		
		/**
		 * this is done once at the initialization of a mapper
		 */
		public void configure(JobConf job) {
			K = Integer.parseInt(job.get("numberOfTopics"));
			V= Integer.parseInt(job.get("sizeOfVocabulary"));
			D = Integer.parseInt(job.get("numberOfDocuments"));
			numberOfMaxGammaIterations = Integer.parseInt(job.get("numberOfIterations"));
			pathToAlphas = job.get("pathToAlphas");
			pathToGammas = job.get("pathToGammas");
			pathToLambdas = job.get("pathToLambdas");
			
			//initialize the variables
			retrieveLambda();
			retrieveGamma();
			retrieveAlpha();

		}

		//the line input is of the form :
		//showId\twordId,Occurences wordId,Occurences wordId,Occurences
		//note that it's better that only words with non 0 occurences are recored for performances reasons.
		public void map(LongWritable key, Text value, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
			//Three different keys value:
			//I- 1,k,v 
			//II- 2,-1,k
			//III- 3,k,d
			
			setPhiSigma();

			//We retrieve the values.
			//We need to parse the line to fill in an array of the words in the doc :
			String[] formatLine = value.toString().split("\t");
			int documentId = Integer.parseInt(formatLine[0]); 
			String[] wordsInDocTemp = formatLine[1].split(" ");
						
			//compute the the hashmap
			//The key is the id of the word, the value is it's frequency. This HashMap contains the non 0 
			//frequencies
			HashMap<Integer, Integer> wordsFreq = new HashMap<Integer, Integer>();
			//add the words with their frequencies and compute the total number of words
			double numberOfWords = 0.0;
			for (int i = 0; i < wordsInDocTemp.length; i++) {
				String[]wordFreqPair = wordsInDocTemp[i].split(",");
				int wordId = Integer.parseInt(wordFreqPair[0]);
				int frequency = Integer.parseInt(wordFreqPair[1]);
				
				
				if (frequency>0) {
					//add the word to the hashmap.
					wordsFreq.put(wordId, frequency);
					
					
					//compute the total number of words
					numberOfWords = numberOfWords+frequency;
				} else {
					//we should never be here because the input file
					//contains only the non 0 frequencies
				}
			}
			
			
			
			//initialize gamma the first time
			if (gamma[documentId][0] ==  -1) {
				double gammaInitConst = (numberOfWords/K);
				//initialize the gamma
				for (int k = 0; k < gamma[documentId].length; k++) {
					gamma[documentId][k] = alpha[k]+ gammaInitConst;
				}
			}

			//We need to keep track of convergence
			boolean notConverged = true;
			int numIterations = 0; 
			Set<Integer> wordIds = wordsFreq.keySet();
			//We now go through the heavy computations : 
			while(notConverged){
				//reinitialize sigma to 0 array
				sigma = new double[K];
				//We update the count of iterations :
				numIterations++;

				//iterate over the set of words
				Iterator<Integer> wordIdsIterator = wordIds.iterator();
				while (wordIdsIterator.hasNext()) {
					Integer wordId = (Integer) wordIdsIterator.next();
					int frequency = wordsFreq.get(wordId);
					
					double sumPhi = 0;
					for (int k = 0; k < K; k++) {						
						phi[wordId][k] = lambda[wordId][k]*Math.exp(Gamma.digamma(gamma[documentId][k]));

						sumPhi = sumPhi + phi[wordId][k];
					}
					sigma = updateSigma(sigma, phi[wordId], frequency, sumPhi);
				}//end iteration over words
				
				//update the gamma
				gamma[documentId] = updateGamma(sigma, alpha);
				
				//Check to see if converged.
				notConverged = convergenceTest(numIterations);
				
				
			}//end while


			//Now we do the emission of our key value types.

			//write the lambda to the reducer
			Iterator<Integer> wordIdsIterator = wordIds.iterator();
			while (wordIdsIterator.hasNext()) {
				Integer wordId = (Integer) wordIdsIterator.next();
				int frequency = wordsFreq.get(wordId);
				for (int k = 0; k < K; k++) {
					outputKey.set("1,"+k +"," + wordId);
					outputValue.set(frequency*phi[wordId][k]);
					output.collect(outputKey, outputValue);

				}
				
			}
			

			//We want to compute the sum of the gammas : 
			double sumGamma = 0.0;
			for(int l = 0; l < K; l++){
				sumGamma += gamma[documentId][l];
			}
			double digammaSumGamma = Gamma.digamma(sumGamma);
			//write the gradient and the lambda to the reducer
			for(int k = 0; k < K; k++){

				outputKey.set("2,"+ (-1) + "," + k);
				
				//write of the gradient to the reducer
				outputValue.set(Gamma.digamma(gamma[documentId][k]) - digammaSumGamma);
				output.collect(outputKey, outputValue);


				//write the gamma to the reducer
				outputKey.set("3,"+k+","+documentId);
				outputValue.set(gamma[documentId][k]);

				output.collect(outputKey, outputValue);

			}

		}
	}




}
