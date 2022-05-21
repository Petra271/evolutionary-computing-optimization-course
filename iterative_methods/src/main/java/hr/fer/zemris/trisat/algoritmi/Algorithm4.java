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

public class Algorithm4 implements IOptAlgorithm {

	protected SATFormula formula;
	protected SATFormulaStats stats;
	private Random rand = new Random();
	private final int maxIters = 500;
	private final int maxFlips = 100;

	public Algorithm4(SATFormula formula) {
		this.formula = formula;
		this.stats = new SATFormulaStats(this.formula);
	}


	@Override
	public Optional<BitVector> solve(Optional<BitVector> initial) {
		BitVector init = null;
		int countIter = 0;
		int countFlips = 0;

		while (countIter++ < this.maxIters) {
			init = new BitVector(this.rand, this.formula.getNumberOfVariables());
			this.stats.setAssignment(init, false);

			while (countFlips++ < maxFlips) {
				if (this.stats.getNumberOfSatisfied() == this.formula.getNumberOfClauses()) {
					System.out.println(init);
					return Optional.of(init);
				}
				init = getBestFlipped(init);
				this.stats.setAssignment(init, false);
			}
		}

		System.out.println("No solution found (no more iterations).");
		return Optional.empty();
	}

	protected BitVector getBestFlipped(BitVector init) {
		List<BitVector> bestVectors = new ArrayList<>();
		BitVectorNGenerator generator = new BitVectorNGenerator(init);
		int fitness;
		int currentMaxSatisfied = 0;

		for (MutableBitVector v : generator) {
			fitness = this.getFitness(v);
			if (fitness == currentMaxSatisfied) {
				bestVectors.add(v);
			} else if (fitness > currentMaxSatisfied) {
				bestVectors.clear();
				currentMaxSatisfied = fitness;
				bestVectors.add(v);
			}
		}
		
		return bestVectors.get(this.rand.nextInt(bestVectors.size()));
	}

	protected int getFitness(BitVector vector) {
		this.stats.setAssignment(vector, false);
		return this.stats.getNumberOfSatisfied();
	}
	
	protected void flip(MutableBitVector vector, int index) {
		vector.set(index, !vector.get(index));
	}

}
