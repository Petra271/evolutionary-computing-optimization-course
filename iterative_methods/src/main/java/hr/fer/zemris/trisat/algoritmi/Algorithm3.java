package hr.fer.zemris.trisat.algoritmi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map;
import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

public class Algorithm3 implements IOptAlgorithm {
	private SATFormula formula;
	private SATFormulaStats stats;
	private final int maxIters = 100000;
	private final int numOfBest = 2;

	public Algorithm3(SATFormula formula) {
		this.formula = formula;
		this.stats = new SATFormulaStats(this.formula);
	}

	@Override
	public Optional<BitVector> solve(Optional<BitVector> initial) {
		Random rand = new Random();
		BitVector init = new BitVector(rand, this.formula.getNumberOfVariables());
		int countIter = 0;

		while (countIter++ < this.maxIters) {
			stats.setAssignment(init, true);

			if (this.stats.getNumberOfSatisfied() == this.formula.getNumberOfClauses()) {
				System.out.println(init);
				return Optional.of(init);
			}

			Map<Double, BitVector> solutions = new TreeMap<>(Comparator.reverseOrder());
			BitVectorNGenerator generator = new BitVectorNGenerator(init);
			for (MutableBitVector v : generator) {
				solutions.put(this.getFitness(v), v);
			}

			solutions = solutions.entrySet().stream().limit(2).collect(TreeMap::new,
					(m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

			List<BitVector> bestSolutions = new ArrayList<>(solutions.values());
			init = bestSolutions.get(rand.nextInt(this.numOfBest));
		}

		System.out.println("No solution found (no more iterations).");
		return Optional.empty();
	}

	private double getFitness(BitVector vector) {
		this.stats.setAssignment(vector, false);
		return this.stats.getNumberOfSatisfied() + this.stats.getPercentageBonus();
	}
}
