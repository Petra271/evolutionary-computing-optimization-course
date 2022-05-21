package hr.fer.zemris.trisat.algoritmi;

import java.util.Optional;
import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

public class Algorithm1 implements IOptAlgorithm {

	private SATFormula formula;

	public Algorithm1(SATFormula formula) {
		this.formula = formula;
	}

	@Override
	public Optional<BitVector> solve(Optional<BitVector> initial) {
		int vars = this.formula.getNumberOfVariables();
		int m = (int) Math.pow(2, vars);

		MutableBitVector result = new MutableBitVector(vars);

		for (int i = 0; i < m; i++) {
			if (this.formula.isSatisfied(result))
				System.out.println(result);

			for (int j = vars - 1; j >= 0; j--) {
				if (!result.get(j)) {
					result.set(j, true);
					break;
				}
				result.set(j, false);
			}
		}
		return Optional.of(result);
	}
}
