package hr.fer.zemris.optjava.dz5.moopProblem;

public class Problem2 implements MOOPProblem {

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}

	@Override
	public void evaluateSolution(double[] solution, double[] objectives) {
		objectives[0] = solution[0];
		objectives[1] = (1.0 + solution[1]) / solution[0];
	}

	@Override
	public int getNumberOfVars() {
		return 2;
	}

	@Override
	public double[][] getBounds() {
		return new double[][] { new double[] { 0.1, 1.0 }, new double[] { 0.0, 5.0 } };
	}

}
