import org.apache.commons.math.special.Gamma;



public class Driver {
	public int D;
	public int K;
	public int V;
	public double[] oldAlpha;
	public double[] newAlpha;
	public double[][] hessian;
	public double[] gradient;
	public double[] rDelta;

	//Use Jama package to invert matrix.

	public Driver(){
		this.D = Parameters.numberOfDocuments;
		this.K = Parameters.numberOfTopics;
		this.V = Parameters.sizeOfVocabulary;

		this.oldAlpha = new double[this.K];
		this.newAlpha = new double[this.K];
		this.hessian = new double[this.K][this.K];
		this.gradient = new double[this.K];
	}

	private void retrieveAlpha(){
		/**
		 * TODO
		 */
		oldAlpha = FileSystemHandler.loadAlpha(Parameters.pathToAlphas, K);
	}

	private void retrieveReducerOutput(){
		this.rDelta = FileSystemHandler.loadGradient(Parameters.pathToGradient);
	}
	

	/**
	 * The convergence test takes the biggest difference between the old and the new vector and return true
	 * if that difference is below some threshold or if the number of iterations exeeded the maximum number
	 * allowed
	 * @param newVector
	 * @param oldVector
	 * @return
	 */
	
	public void setNewAlpha(){

		retrieveAlpha();
		retrieveReducerOutput();
		
		double[] alphaVectorUpdate = new double[K];
		double[] alphaGradientVector = new double[K];
		double[] alphaHessianVector = new double[K];

		double[] alphaVector = oldAlpha.clone();
		double[] alphaSufficientStatistics = rDelta;

		int alphaUpdateIterationCount = 0;

		// update the alpha vector until converge
		boolean keepGoing = true;
		try {
			int decay = 0;

			double alphaSum = 0;
			for (int j = 0; j < K; j++) {
				alphaSum += alphaVector[j];
			}

			while (keepGoing) {
				double sumG_H = 0;
				double sum1_H = 0;

				for (int i = 0; i < K; i++) {
					// compute alphaGradient
					alphaGradientVector[i] = D
							* (Gamma.digamma(alphaSum) - Gamma.digamma(alphaVector[i]))
							+ alphaSufficientStatistics[i];

					// compute alphaHessian
					alphaHessianVector[i] = -D * Gamma.trigamma(alphaVector[i]);

					if (alphaGradientVector[i] == Double.POSITIVE_INFINITY
							|| alphaGradientVector[i] == Double.NEGATIVE_INFINITY) {
						throw new ArithmeticException("Invalid ALPHA gradient matrix...");
					}

					sumG_H += alphaGradientVector[i] / alphaHessianVector[i];
					sum1_H += 1 / alphaHessianVector[i];
				}

				double z = D * Gamma.trigamma(alphaSum);
				double c = sumG_H / (1 / z + sum1_H);

				while (true) {
					boolean singularHessian = false;

					for (int i = 0; i < K; i++) {
						double stepSize = Math.pow(Parameters.DEFAULT_ALPHA_UPDATE_DECAY_FACTOR, decay)
								* (alphaGradientVector[i] - c) / alphaHessianVector[i];
						if (alphaVector[i] <= stepSize) {
							// the current hessian matrix is singular
							singularHessian = true;
							break;
						}
						alphaVectorUpdate[i] = alphaVector[i] - stepSize;
					}

					if (singularHessian) {
						// we need to further reduce the step size
						decay++;

						// recover the old alpha vector
						alphaVectorUpdate = alphaVector;
						if (decay > Parameters.DEFAULT_ALPHA_UPDATE_MAXIMUM_DECAY) {
							break;
						}
					} else {
						// we have successfully update the alpha vector
						break;
					}
				}

				// compute the alpha sum and check for alpha converge
				alphaSum = 0;
				keepGoing = false;
				for (int j = 0; j < K; j++) {
					alphaSum += alphaVectorUpdate[j];
					if (Math.abs((alphaVectorUpdate[j] - alphaVector[j]) / alphaVector[j]) >= Parameters.DEFAULT_ALPHA_UPDATE_CONVERGE_THRESHOLD) {
						keepGoing = true;
					}
				}

				if (alphaUpdateIterationCount >= Parameters.DEFAULT_ALPHA_UPDATE_MAXIMUM_ITERATION) {
					keepGoing = false;
				}

				if (decay > Parameters.DEFAULT_ALPHA_UPDATE_MAXIMUM_DECAY) {
					break;
				}

				alphaUpdateIterationCount++;
				alphaVector = alphaVectorUpdate;
			}
		} catch (IllegalArgumentException iae) {
			System.err.println(iae.getMessage());
			iae.printStackTrace();
		} catch (ArithmeticException ae) {
			System.err.println(ae.getMessage());
			ae.printStackTrace();
		}

		newAlpha = alphaVector;


	}

	public void writeNewAlpha(){
		/**
		 * TODO
		 * Here we need to write the result of the previous computation to the alpha file.
		 */
		FileSystemHandler.writeVector(Parameters.pathToAlphas, this.newAlpha);
	}

}
