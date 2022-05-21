package hr.fer.zemris.optjava.dz4.functions;

import Jama.Matrix;

public interface IFunction {

	int getNumOfVariables();

	double getValue(Matrix point);

}
