package diff.evolution;
import java.util.Random;


/*
 * The class PopulationManager is responsible for generating and maintaining a population of 
 * instances of the Individual class. 
 * 
 * The populationmanager generates a starting population of Individuals and creates new 
 * Individuals using mutation and crossover. 
 * 
 * When the ending criteria is met, we can ask the population manager 
 * for the most fit Individual and how many generations it took to reach 
 * the end result. Hopefully the end result is the global optimum but 
 * this is not always the case. The search might converge to a local optimum 
 * due to the randomness of the search. Especially the Ackley-function is difficult 
 * as it contains many local optima and is a higher dimension problem.
 * 
 * 
 */
public class PopulationManager {
	
	private Individual[] population = new Individual[30];
	private Individual mostFitIndividual;
	private int rounds;
	private int roundsWithSameMostFitIndividual;
	
	private static Random rnd = new Random();
	private static final double STEPSIZEUPDATERATE = 0.1;
	private static final double CROSSOVERRATEUPDATERATE = 0.1;
	
	
	public void mainloop() {
		generateStartingPopulation();
		mostFitIndividual = population[0];
		roundsWithSameMostFitIndividual = 0;
		rounds = 0;
		while(!endCondition()) { 
			for(int i = 0; i < population.length; i++) {
				Individual child = createNewIndividual(population[i]);
				if(child.getFitness() < population[i].getFitness()) {
					population[i] = child;
				}
				if(child.getFitness() < mostFitIndividual.getFitness()) {
					mostFitIndividual = child;
					roundsWithSameMostFitIndividual = 0;
				} 
			}
			roundsWithSameMostFitIndividual++;
			rounds++;
		}
		
		printPopulation();
		System.out.println("The search took " + rounds + " generations.\n");
		System.out.println("The most fit individual is:\n");
		System.out.println(mostFitIndividual);
	}
	
	
	private void generateStartingPopulation() {
		
		for(int i = 0; i < population.length; i++) {
			population[i] = new Individual();
		}
	}
	
	private boolean endCondition() {
		return roundsWithSameMostFitIndividual > 30;
	}
	
	/*
	 * Creating a new Individual is a process consisting of two phases: 
	 * mutation and crossover. Mutation creates a random child using 
	 * the parents stepsize. After mutation we crossover the random child 
	 * with the parent.
	 * 
	 * Mutation:
	 * We select randomly 3 Individuals from the current population 
	 * and combine their datavectors as follows
	 * c = x + F(y - z ), where x,y,z are datavectors of chosen Individuals 
	 * and F is the parents stepsize. 
	 * If the parent is currently the most fit Individual in the population
	 * we sometimes use the Golden ratio search to locally optimize the 
	 * stepsize for mutation. 
	 * 
	 * Crossover:
	 * Using the parents crossoverrate we plant genes from the parents 
	 * datavector to the child's datavector.
	 * 
	 */

	
	private Individual createNewIndividual(Individual parent) {
		Individual ind1 = population[rnd.nextInt(population.length)];
		Individual ind2 = population[rnd.nextInt(population.length)];
		Individual ind3 = population[rnd.nextInt(population.length)];
		
		double[] childDataPart1 = ind1.getDatavector();
		double[] childDataPart2 = sub(ind2.getDatavector(), ind3.getDatavector());
		
		if(parent == mostFitIndividual && rnd.nextDouble() < 0.25) {
			double optimalStepSize = GoldenRatioSearch.goldenRatioSearch(childDataPart1, childDataPart2);
			double[] childData = add(childDataPart1,multi(optimalStepSize, childDataPart2));
			Individual child = new Individual(childData);
			child.setStepSize(optimalStepSize);
			crossover(parent, child);
			return child;
		} else {
			if(rnd.nextDouble() < STEPSIZEUPDATERATE) {
				parent.updateStepsize();
			} 
			double[] childData = add(childDataPart1,multi(parent.getStepSize(), childDataPart2));
			Individual child = new Individual(childData);
			if(rnd.nextDouble() < CROSSOVERRATEUPDATERATE) {
				parent.updateCrossOverRate();
			}
			crossover(parent, child);
			return child;
		}
	}
	
	
	private void crossover(Individual parent, Individual child) {
		double crossoverRate = parent.getCrossoverRate();
		double[] childData = child.getDatavector();
		double[] parentData = parent.getDatavector();
		for(int i = 0; i < childData.length; i++) {
			if(rnd.nextDouble() < crossoverRate) {
				childData[i] = parentData[i];
			}
		}
		child.setDatavector(childData);
		child.calculateFitness();
	}
	
	
	
	
	/*
	 * add, sub ja multi are helper-functions for creating data-arrays for 
	 * a child individual. Used both in this class and the 
	 * class GoldenRatioSearch.
	 * 
	 */
	
	
	public static double[] add(double[] a, double[] b) {
		double[] result = new double[a.length];
		for(int i = 0; i < a.length;i++) {
			result[i] = a[i] + b[i];
		}
		return result;
	}
	
	public static double[] sub(double[] a, double[] b) {
		double[] result = new double[a.length];
		for(int i = 0; i < a.length;i++) {
			result[i] = a[i] - b[i];
		}
		return result;
	}
	
	public static double[] multi(double coeff, double[] a) {
		double[] result = new double[a.length];
		for(int i = 0; i < a.length;i++) {
			result[i] = a[i] * coeff;
		}
		return result;
	}
	
	public int getGenerationCount() {
		return rounds;
	}
	
	public Individual getMostFitIndividual() {
		return mostFitIndividual;
	}
	
	
	
	public void printPopulation() {
		for(Individual indiv : population) {
			System.out.println(indiv);
		}
	}
}
