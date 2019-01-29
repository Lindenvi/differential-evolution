package diff.evolution;

/*
 * Easom-function:
 * f(x) = -cos x1 * cos x2 * e^(-(x1-PI)^2-(x2-PI)^2)
 * x1,x2 in [-100,100]
 * Global minimum f(x*) = -1, x* = (PI,PI)
 */

public class Easom_function extends Function {
	
	private static final int DIMENSION = 2;
	
	
	public double functionValue(double[] variableValues) {
		double[] variables = variableValues;
		double power = -Math.pow((variables[0] - Math.PI), 2) - Math.pow((variables[1] - Math.PI), 2);
		double fValue = -Math.cos(variables[0]);
		fValue *= Math.cos(variables[1]);
		fValue *= Math.exp(power);
		return fValue;
	}
	
	public int getDimension() {
		return DIMENSION;
	}

}
