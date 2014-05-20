import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Job;





public class Run {
	
	private static void initializeAlpha(String alphaPath){
		
		double[] alpha = new double[Parameters.numberOfTopics];
		
		//initialize with some random value
		for (int i = 0; i < alpha.length; i++) {
			alpha[i] = Math.random();
		}
		
		FileSystemHandler.writeVector(Parameters.pathToAlphas, alpha);
	}
	
	
	private static void initializeGamma(String gammaPath){
		//initialization will be done in the mapper
		double[][] gamma = new double [Parameters.numberOfDocuments][Parameters.numberOfTopics];
		for (int i = 0; i < gamma.length; i++) {
			for (int j = 0; j < gamma[0].length; j++) {
				gamma[i][j] = -1;
			}
		}
		
		FileSystemHandler.writeMatrix(Parameters.pathToGammas, gamma);
	}
	
	
	private static void initializeLambda(String lambdaPath){
		//initialization with equiprobable probabilities
		double[][] lambda = new double [Parameters.sizeOfVocabulary][Parameters.numberOfTopics];
		for (int i = 0; i < lambda.length; i++) {
			for (int j = 0; j < lambda[0].length; j++) {
				lambda[i][j] = 1.0/Parameters.sizeOfVocabulary;
			}
		}
		
		FileSystemHandler.writeMatrix(Parameters.pathToLambdas, lambda);
	}
	
	private static JobConf getJob(String inputPath, String outputPath) {
		JobConf conf = new JobConf(LdaReducer.class);
		
 	
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(DoubleWritable.class);
		conf.setMapperClass(LdaMapper.Map.class);
		conf.setCombinerClass(LdaReducer.Reduce.class);
		conf.setReducerClass(LdaReducer.Reduce.class);
 	
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		
		//set the arguments
		conf.set("pathToAlphas", Parameters.pathToAlphas);
		conf.set("pathToGammas", Parameters.pathToGammas);
		conf.set("pathToLambdas", Parameters.pathToLambdas);
		conf.set("sizeOfVocabulary", ""+Parameters.sizeOfVocabulary);
		conf.set("numberOfDocuments","" + Parameters.numberOfDocuments);
		conf.set("numberOfTopics", "" + Parameters.numberOfTopics);
		conf.set("numberOfIterations","" +Parameters.NUMBER_OF_MAPPER_ITERATIONS);
		
		//set the number of mappers and reducers
		conf.setNumReduceTasks(40);
		conf.setNumMapTasks(50);
		
 	
		FileInputFormat.setInputPaths(conf, new Path(inputPath));
		FileOutputFormat.setOutputPath(conf, new Path(outputPath));
		return conf;
		
	}
	
	private static void normalizeLambda(String pathToLambda) {
		int K = Parameters.numberOfTopics;
		int V = Parameters.sizeOfVocabulary;
				
		double[][] lambda = FileSystemHandler.loadGammas(pathToLambda, V, K);
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
		FileSystemHandler.writeMatrix(pathToLambda, lambda);
	}
	
	private static void normalizeGamma(String pathToGamma) {
		int D = Parameters.numberOfDocuments;
		int K = Parameters.numberOfTopics;
		
		double[][] gammas = FileSystemHandler.loadGammas(pathToGamma, D, K);
		
		//normalize gamma
		for (int d = 0; d < gammas.length; d++) {
			double sumGamma = 0;
			for (int k = 0; k < gammas[0].length; k++) {
				sumGamma = sumGamma + gammas[d][k];
			}
			
			for (int k = 0; k < gammas[0].length; k++) {
				gammas[d][k] = gammas[d][k]/sumGamma;
				if (gammas[d][k]<0) {
					System.out.println("Gamma is negative "+d+","+k);
					
				}
			}	
		}
		
		FileSystemHandler.writeMatrix(pathToGamma, gammas);
		
	}
	
	private static void displayLdaConfigurations(String inputFolder, String outputFolder, String pathAlpha, String pathGammas, String pathLambdas,
			String jobOutPath, String pathToGradient) {
		System.out.println("********************************* LDA CONFIGURATIONS ********************************************************");
		System.out.println("Input Folder : " + inputFolder);
		System.out.println("Output Folder :  " + outputFolder);
		System.out.println("Path to alpha : " + pathAlpha);
		System.out.println("Path to gamma : " + pathGammas);
		System.out.println("Path to lambda : " + pathLambdas);
		System.out.println("Job output path : " + jobOutPath);
		System.out.println("Path to gradient : " + pathToGradient);
		

		System.out.println("Number of topics : " + Parameters.numberOfTopics);
		System.out.println("Number of documents : " + Parameters.numberOfDocuments);
		System.out.println("Size of vocabulary : " + Parameters.sizeOfVocabulary);
		System.out.println("Number of iterations : " + Parameters.numberOfIterations);
		System.out.println("Number of mapper iterations : " + Parameters.NUMBER_OF_MAPPER_ITERATIONS);
		
		System.out.println("***********************************************************************************************************************");
	
		
		
	}
	
	public static void main(String[] args) throws Exception {
		
		
		if (args.length != 6) {
			System.err.println("input format: <input folder> <outputfolder> <number of topics> <number of documents>" +
					"<size of vocabulary> <number of iterations>");
			System.exit(1);
			
		}
		
		
		String inputFolder = args[0];
		String outputFolder = args[1];
				
		String pathAlpha = outputFolder+"/final/alpha";
		String pathGammas = outputFolder+"/final/gamma";
		String pathLambdas = outputFolder+"/final/lambda";
		String pathToGradient = outputFolder+"/final/gradient";
		
		String jobOutPath = outputFolder+"/temp";
		
		
		
		//The Parameters class is set once and for all.
		Parameters.numberOfTopics = Integer.parseInt(args[2]);
		Parameters.numberOfDocuments = Integer.parseInt(args[3]);
		Parameters.sizeOfVocabulary = Integer.parseInt(args[4]);
		Parameters.numberOfIterations = Integer.parseInt(args[5]);
		Parameters.setPaths(pathAlpha, pathGammas, pathLambdas, jobOutPath, pathToGradient);
		
		displayLdaConfigurations(inputFolder, outputFolder, pathAlpha, pathGammas, pathLambdas, jobOutPath, pathToGradient);
		
		//We now do the algorithm : 
		
		int i = 0;
		
		//At i = 0, we need to initialise the files for alpha, gamma, lambda.
		Run.initializeAlpha(Parameters.pathToAlphas);
		Run.initializeGamma(Parameters.pathToGammas);
		Run.initializeLambda(Parameters.pathToLambdas);
		
		Driver driver = new Driver();
		JobConf conf = getJob(inputFolder, Parameters.pathJobOutput);
		
		while(i < Parameters.numberOfIterations){
			i++;
			System.out.println("iteration: "+ i);

			conf.setJobName("LDA: K="+Parameters.numberOfTopics+", D="+Parameters.numberOfDocuments+", V="+Parameters.sizeOfVocabulary+", iteration="+i);			
			
			
			Job job = new Job(conf);
			job.waitForCompletion(true);
			
			//rewrite the files lambda and gamma to a good format
			//and write the delta
			
			FileSystemHandler.convertJobOutputToLambdaGammaGradient(Parameters.pathJobOutput, Parameters.pathToLambdas, Parameters.pathToGammas, Parameters.pathToGradient);
			

			//delete the output of the reducer
			
			FileSystemHandler.deletePath(Parameters.pathJobOutput);
			
		
			
			driver.setNewAlpha();
			driver.writeNewAlpha();
			
			//activate the garbage collector
			System.runFinalization();
			System.gc();
		}
		
		//at the very end write a normalized version of lambda and gamma
		normalizeLambda(Parameters.pathToLambdas);
		normalizeGamma(Parameters.pathToGammas);
		
			
	}
	
	

}