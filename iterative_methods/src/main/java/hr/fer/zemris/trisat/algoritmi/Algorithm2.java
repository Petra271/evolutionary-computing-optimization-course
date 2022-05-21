package hr.fer.zemris.trisat.algoritmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

public class Algorithm2 implements IOptAlgorithm {
	private SATFormula formula;
	private final int maxIters = 100000;
	private SATFormulaStats stats;

	public Algorithm2(SATFormula formula) {
		this.formula = formula;
		this.stats = new SATFormulaStats(this.formula);
	}

	@Override
	public Optional<BitVector> solve(Optional<BitVector> initial) {
		Random rand = new Random();
		BitVector init = new BitVector(rand, this.formula.getNumberOfVariables());
		int countIter = 0;

		while (countIter++ < this.maxIters) {
			int numberOfSatisfied = this.getFitness(init);

			if (numberOfSatisfied == this.formula.getNumberOfClauses()) {
				System.out.println(init);
				return Optional.of(init);
			}

			List<BitVector> neighborsWithHigherFitness = new ArrayList<>();
			BitVectorNGenerator generator = new BitVectorNGenerator(init);
			for (MutableBitVector v : generator) {
				if (numberOfSatisfied <= this.getFitness(v))
					neighborsWithHigherFitness.add(v);
			}

			int numOfBetterNeighbors = neighborsWithHigherFitness.size();
			if (numOfBetterNeighbors == 0) {
				System.out.println("Local optimum reached. Search failed.");
				return Optional.empty();
			}

			init = neighborsWithHigherFitness.get(rand.nextInt(numOfBetterNeighbors));
		}

		System.out.println("No solution found (no more iterations).");
		return Optional.empty();
	}

	private int getFitness(BitVector vector) {
		this.stats.setAssignment(vector, false);
		return this.stats.getNumberOfSatisfied();
	}
}
