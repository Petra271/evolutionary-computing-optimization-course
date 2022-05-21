package hr.fer.zemris.optjava.dz5.moopProblem;

public class Problem1 implements MOOPProblem {

	@Override
	public int getNumberOfObjectives() {
		return 4;
	}

	@Override
	public void evaluateSolution(double[] solution, double[] objectives) {
		for (int i = 0; i < solution.length; i++) {
			objectives[i] = solution[i] * solution[i];
		}
	}

	@Override
	public int getNumberOfVars() {
		return 4;
	}

	@Override
	public double[][] getBounds() {
		return new double[][] { new double[] { -5.0, 5.0 }, new double[] { -5.0, 5.0 }, new double[] { -5.0, 5.0 },
				new double[] { -5.0, 5.0 } };
	}

}
