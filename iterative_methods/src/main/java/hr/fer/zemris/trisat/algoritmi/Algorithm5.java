package hr.fer.zemris.trisat.algoritmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.Clause;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

public class Algorithm5 extends Algorithm4 {

	private final int maxIters = 1000;
	private final int maxFlips = 200;
	private Random rand = new Random();
	double percent = 0.3;

	public Algorithm5(SATFormula formula) {
		super(formula);
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

				if (Math.random() <= this.percent) {
					Clause randomUnsatisfiedClause = this.getRandomUnsatisfiedClause(init);
					int index = rand.nextInt(randomUnsatisfiedClause.getSize());
					int literalIndex = Math.abs(randomUnsatisfiedClause.getLiteral(index));
					MutableBitVector mutable = init.copy();
					flip(mutable, literalIndex);
					init = mutable;
				} else {
					init = getBestFlipped(init);
				}
				this.stats.setAssignment(init, false);
			}
		}

		System.out.println("No solution found (no more iterations).");
		return Optional.empty();
	}

	private List<Clause> getUnsatisfiedClauses(BitVector assignment) {
		int numOfClauses = this.formula.getNumberOfClauses();
		List<Clause> clauses = new ArrayList<>();

		for (int i = 0; i < numOfClauses; i++) {
			Clause c = this.formula.getClause(i);
			if (!c.isSatisfied(assignment)) {
				clauses.add(c);
			}
		}
		return clauses;
	}

	private Clause getRandomUnsatisfiedClause(BitVector assignment) {
		List<Clause> unsatisfiedClauses = this.getUnsatisfiedClauses(assignment);
		return unsatisfiedClauses.get(this.rand.nextInt(unsatisfiedClauses.size()));
	}
}
