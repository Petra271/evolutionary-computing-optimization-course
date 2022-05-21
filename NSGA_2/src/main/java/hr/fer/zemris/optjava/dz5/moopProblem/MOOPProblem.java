package hr.fer.zemris.optjava.dz5.moopProblem;

public interface MOOPProblem {

	int getNumberOfObjectives();

	void evaluateSolution(double[] solution, double[] objectives);

	double[][] getBounds();

	int getNumberOfVars();
}
