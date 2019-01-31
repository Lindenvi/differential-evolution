package diff.evolution;


/*
 * Using the golden ratio search optimization method
 * we determine the optimal stepsize in range[-1,1] 
 * to be used in mutation. A negative stepsize essentially means 
 * reversing the direction vector.
 * 
 */

public class GoldenRatioSearch {
	
	private static Function fitnessFunction;
	private static final double GR = (1 + Math.sqrt(5) / 2);
	
	
	public static double goldenRatioSearch(double[] childDataPart1, double[] childDataPart2) {
		double intervalStart = -1;
		double intervalEnd = 1;
		
		while(Math.abs(intervalEnd - intervalStart) > 0.01) {
			double firstSamplePoint = intervalEnd - (intervalEnd - intervalStart) / GR;
			double secondSamplePoint = intervalStart + (intervalEnd - intervalStart) / GR;
			
			double[] dataWithFirstSample = PopulationManager.add(childDataPart1, PopulationManager.multi(firstSamplePoint, childDataPart2));
			double[] dataWithSecondSample = PopulationManager.add(childDataPart1, PopulationManager.multi(secondSamplePoint, childDataPart2));
			
			fitnessFunction = Individual.getFitnessFunction();
			double fitnessWithFirstSample = fitnessFunction.functionValue(dataWithFirstSample);
			double fitnessWithSecondSample = fitnessFunction.functionValue(dataWithSecondSample);
			
			if(fitnessWithFirstSample < fitnessWithSecondSample) {
				intervalEnd = secondSamplePoint;
			} else {
				intervalStart = firstSamplePoint;
			}
		}
		
		return intervalEnd;
	}
}
