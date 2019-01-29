package diff.evolution;

/*
 * The Ackley function is a difficult optimization test function with multiple local minima.
 * Global minimum:
 * f(x*) = 0, where x* = (0,0,...,0).
 * Typically the variables are defined in range [-32,32]. 
 * My solution can find the minimum of a 2 dimension Auckley function using range [-100,100], 
 * sometimes even with 5 dimensions.
 * With larger dimensions I need to restrict the range.  
 * 
 */



public class Ackley extends Function{
	
	private static final int DIMENSION = 5;
	
	private static final int a = 20;
	private static final double b = 0.2;
	private static final double c = 2 * Math.PI;
	
	public double functionValue(double[] variableValues) {
		double firstSum = 0;
		for(int i = 0; i < DIMENSION; i++) {
			firstSum += Math.pow(variableValues[i],2);
		}
		firstSum /=  DIMENSION;
		firstSum = Math.sqrt(firstSum);
		firstSum *= -b;
		firstSum = Math.exp(firstSum);
		firstSum *= -a; 
		double secondSum = 0;
		for(int i = 0; i < DIMENSION; i++) {
			secondSum += Math.cos(variableValues[i] * c);
		}
		secondSum /=  DIMENSION;
		secondSum = Math.exp(secondSum);
		return (firstSum - secondSum + a + Math.E);

	}
	
	
	
	public int getDimension() {
		return DIMENSION;
	}

}
