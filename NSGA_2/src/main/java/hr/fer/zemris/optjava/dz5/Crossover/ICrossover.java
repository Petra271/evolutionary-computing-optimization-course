package hr.fer.zemris.optjava.dz5.Crossover;

import hr.fer.zemris.optjava.dz5.moopProblem.MOOPProblem;
import hr.fer.zemris.optjava.dz5.solution.Solution;

public interface ICrossover {

	public Solution crossover(Solution sol1, Solution sol2, MOOPProblem problem);

}
