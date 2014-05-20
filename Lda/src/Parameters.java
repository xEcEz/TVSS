/**
 * 
 * This class represents the static final constants
 *
 */
public class Parameters {
	public static String pathToAlphas;
	public static String pathToGammas;
	public static String pathToLambdas;
	public static String pathJobOutput;
	public static String pathToGradient;


	public static int numberOfTopics;
	public static int numberOfDocuments;
	public static int sizeOfVocabulary;
	public static int numberOfIterations;
	public static final int NUMBER_OF_MAPPER_ITERATIONS=100;
	
	public static final int MAX_ALPHA_ITERATION_NUMBER = 1000;
	public static final float DEFAULT_ALPHA_UPDATE_DECAY_FACTOR = 0.8f;
	public static final int DEFAULT_ALPHA_UPDATE_MAXIMUM_DECAY = 10;
	public static final float DEFAULT_ALPHA_UPDATE_CONVERGE_THRESHOLD =0.000001f;
	public static final int DEFAULT_ALPHA_UPDATE_MAXIMUM_ITERATION =1000;
	
	
	public static void setPaths(String pathAlpha, String pathGammas, String pathLambdas, String jobPathOutput, String pathToGradient){
		Parameters.pathToAlphas = pathAlpha;
		Parameters.pathToGammas = pathGammas;
		Parameters.pathToLambdas = pathLambdas;
		Parameters.pathJobOutput = jobPathOutput;
		Parameters.pathToGradient = pathToGradient;
		
	}

}
