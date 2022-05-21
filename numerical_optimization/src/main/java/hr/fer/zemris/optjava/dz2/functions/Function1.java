package hr.fer.zemris.optjava.dz2.functions;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IFunction;

public class Function1 implements IFunction {

	public int getNumOfVariables() {
		return 2;
	}

	public double getValue(Matrix point) {
		if (point.getColumnDimension() != 1 || point.getRowDimension() != this.getNumOfVariables())
			throw new IllegalArgumentException("Invalid arguments.");

		double x1 = point.get(0, 0);
		double x2 = point.get(1, 0);
		return x1 * x1 + (x2 - 1) * (x2 - 1);
	}

	public Matrix getGradient(Matrix point) {
		if (point.getColumnDimension() != 1 || point.getRowDimension() != this.getNumOfVariables())
			throw new IllegalArgumentException("Invalid arguments.");

		double x1 = point.get(0, 0);
		double x2 = point.get(1, 0);
		double[][] res = { { 2 * x1 }, { 2 * (x2 - 1) } };
		return new Matrix(res);
	}
	
}
