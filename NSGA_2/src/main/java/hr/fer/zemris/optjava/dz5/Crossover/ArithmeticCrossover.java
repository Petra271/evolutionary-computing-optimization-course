package hr.fer.zemris.optjava.dz5.Crossover;

import hr.fer.zemris.optjava.dz5.moopProblem.MOOPProblem;
import hr.fer.zemris.optjava.dz5.solution.Solution;

public class ArithmeticCrossover implements ICrossover {

	private double lam;

	public ArithmeticCrossover(double lam) {
		this.lam = lam;
	}

	@Override
	public Solution crossover(Solution p1, Solution p2, MOOPProblem problem) {
		int size = p1.getNumOfVars();
		double[] values = new double[size];
		double obj[] = new double[problem.getNumberOfObjectives()];

		for (int i = 0; i < size; i++) {
			values[i] = p1.getValueAtIndex(i) * lam + (1 - lam) * p2.getValueAtIndex(i);
		}

		problem.evaluateSolution(values, obj);
		return new Solution(values, obj);
	}

}
