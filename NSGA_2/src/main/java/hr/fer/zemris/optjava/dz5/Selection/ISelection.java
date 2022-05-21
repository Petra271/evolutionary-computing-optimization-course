package hr.fer.zemris.optjava.dz5.Selection;

import java.util.List;

import hr.fer.zemris.optjava.dz5.solution.Solution;

public interface ISelection {

	public Solution select(List<Solution> population);

}
