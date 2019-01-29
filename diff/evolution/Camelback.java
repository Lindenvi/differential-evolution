package diff.evolution;

/*
 * Six-hump camelback-function:
 * f(x) = (4 - 2.1 * x1^2 + x1^4 / 3) * x1^2 + x1 * x2 + (-4 + 4 * x2^2) * x2^2
 * Six local minima, two of which are global.
 * The function is usually evaluated on the rectangle x1 in [-3, 3], x2 in [-2, 2]. 
 * My implementation uses x1,x2 in [-100,100].
 * 
 * Global minimum:
 * f(x*) = -1.0316 where x* = (0.0898, -0.7126) or x*= (-0.0898, 0.7126)
 * 
 */



public class Camelback extends Function {
	
	private static final int DIMENSION = 2;
	
	public double functionValue(double[] variableValues) {
		double part1 = (4-2.1*Math.pow(variableValues[0], 2) + Math.pow(variableValues[0], 4)/ 3) * Math.pow(variableValues[0], 2);
		double part2 = variableValues[0] * variableValues[1];
		double part3 = (-4 + 4 * Math.pow(variableValues[1], 2)) * Math.pow(variableValues[1], 2);
		
		return part1 + part2 + part3;
		
	}
	
	public int getDimension() {
		return DIMENSION;
	}

}
