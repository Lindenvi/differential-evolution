package diff.evolution;
import java.util.Random;


/*
 * The class Individual corresponds to a candidate solution to minimizing a given fitness-function.
 * The fitness function implements the abstract Function.java class.
 *
 * Using mutation and crossover on existing individuals in the population we generate new individuals.
 * The new individual will only replace its parent individual only if it's fitter than the parent individual.
 * 
 */



public class Individual {
	
	
	private double[] datavector;
	private double fitness;
	private double stepSize;
	private double crossoverRate;
	
	private static Random rnd = new Random();
	private static Function fitnessFunction;
	private static final double MINSTEPSIZE = 0.1;
	private static final double MAXSTEPSIZECONTRIBUTION = 0.9;
	
	
	public Individual() {
		datavector = new double[fitnessFunction.getDimension()];

		for(int i = 0; i < datavector.length; i++) {
			datavector[i] = 200*rnd.nextDouble() - 100;
			
			/*
			 * Use smaller ranges when testing the Ackley-function with more than 2 dimensions.
			 * The bigger the dimension, the smaller range my implementation needs.
			 * 
			 * Eq.
			 * datavector[i] = 64*rnd.nextDouble() - 32; tested with 10 dimensions 
			 * datavector[i] = 2*rnd.nextDouble() - 1;  tested with 100 dimensions
			 * 
			 */
			

			stepSize = rnd.nextDouble();
			crossoverRate = rnd.nextDouble();
			calculateFitness();
		}
	}
	
	public Individual(double[] data) {
		this.datavector = data;
		stepSize = rnd.nextDouble();
		crossoverRate = rnd.nextDouble();
		calculateFitness();
	}
	
	
	public void calculateFitness() {
		fitness = fitnessFunction.functionValue(this.datavector);
		
	}
	
	public void updateStepsize() {
		this.stepSize = MINSTEPSIZE + MAXSTEPSIZECONTRIBUTION * rnd.nextDouble();
	}
	
	public void updateCrossOverRate() {
		this.crossoverRate = rnd.nextDouble();
	}
	
	
	public double[] getDatavector() {
		return this.datavector;
	}
	
	public void setDatavector(double[] datavector) {
		this.datavector = datavector;
	}
	
	public double getFitness() {
		return this.fitness;
	}
	
	public double getStepSize() {
		return this.stepSize;
	}
	
	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
	}
	
	public double getCrossoverRate() {
		return this.crossoverRate;
	}
	
	public void setCrossoverRate(double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}
	
	
	/*
	 * getFitnessFunction() is used to tell 
	 * goldenRatioSearch which fitnessFunction is 
	 * being used.
	 * 
	 * setFitnessFunction sets the used fitnessFunction.
	 * This function is called from the GUI.
	 */
	public static Function getFitnessFunction() {
		return fitnessFunction;
	}
	
	public static void setFitnessFunction(Function newFitnessFunction) {
		fitnessFunction = newFitnessFunction;
	}
	
	public String toString() {
		String result = "Genetic data: ";
		for(int i = 0; i < datavector.length; i++) {
			result = result + this.datavector[i] + " "; 
		}
		result += "\n" + "Fitness: " + fitness +"\n"; 
		return result;
	}

}
