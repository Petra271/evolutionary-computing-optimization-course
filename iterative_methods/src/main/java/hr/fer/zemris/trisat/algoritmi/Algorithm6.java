package hr.fer.zemris.trisat.algoritmi;

import java.util.Optional;
import java.util.Random;
import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

public class Algorithm6 implements IOptAlgorithm {

	private SATFormula formula;
	private SATFormulaStats stats;
	private Random rand = new Random();
	private final int maxIters = 500;
	private IOptAlgorithm localSearch;

	public Algorithm6(SATFormula formula) {
		this.formula = formula;
		this.stats = new SATFormulaStats(this.formula);
		this.localSearch = new Algorithm2(this.formula);
	}

	@Override
	public Optional<BitVector> solve(Optional<BitVector> initial) {
		BitVector init = new BitVector(rand, this.formula.getNumberOfVariables());

		if (this.getFitness(init) == this.formula.getNumberOfClauses()) {
			System.out.println(init);
			return Optional.of(init);
		}

		int countIter = 0;
		while (countIter++ < this.maxIters) {
			int numOfVars = rand.nextInt(this.formula.getNumberOfVariables());

			int[] randomVars;
			if (numOfVars > 0) {
				randomVars = rand.ints(rand.nextInt(numOfVars), 0, numOfVars).toArray();

				MutableBitVector mutable = init.copy();
				for (int i = 0; i < randomVars.length; i++)
					flip(mutable, i);
				init = mutable;
			}

			System.out.println("Starting local search...");
			System.out.print("Try " + countIter + ": ");
			Optional<BitVector> solution = this.localSearch.solve(Optional.of(init));

			if (solution.isPresent()) {
				return solution;
			}
		}

		System.out.println("No solution found (no more iterations).");
		return Optional.empty();
	}

	private int getFitness(BitVector vector) {
		this.stats.setAssignment(vector, false);
		return this.stats.getNumberOfSatisfied();
	}

	protected void flip(MutableBitVector vector, int index) {
		vector.set(index, !vector.get(index));
	}

}
