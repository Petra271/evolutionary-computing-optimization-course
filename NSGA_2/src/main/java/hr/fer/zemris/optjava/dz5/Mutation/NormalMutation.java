package hr.fer.zemris.optjava.dz5.Mutation;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.solution.Solution;

public class NormalMutation implements IMutation {

	private Random rand = new Random();
	private double sigma;

	public NormalMutation(double sigma) {
		this.sigma = sigma;
	}

	// mutiranje koristi normalnu distribuciju
	// standardna dev kontrolira koliko se mijenja rjesenje
	@Override
	public void mutate(Solution sol) {
		for (int i = 0; i < sol.getNumOfVars(); i++) {
			double val = sol.getValueAtIndex(i);
			val += rand.nextGaussian() * this.sigma;
			sol.setValueAtIndex(i, val);
		}
	}
}
