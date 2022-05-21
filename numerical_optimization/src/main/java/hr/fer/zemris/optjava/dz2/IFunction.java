package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public interface IFunction {

	int getNumOfVariables();

	double getValue(Matrix point);

	Matrix getGradient(Matrix point);

}
