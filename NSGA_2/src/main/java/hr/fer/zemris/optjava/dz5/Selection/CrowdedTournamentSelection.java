package hr.fer.zemris.optjava.dz5.Selection;

import java.util.List;
import java.util.Random;
import hr.fer.zemris.optjava.dz5.solution.Solution;

public class CrowdedTournamentSelection implements ISelection {
	private static Random rand = new Random();
	private int tSize;

	public CrowdedTournamentSelection(int tSize) {
		this.tSize = tSize;
	}

	// bira roditelja koristeci grupiranu turnirsku selekciju
	@Override
	public Solution select(List<Solution> population) {
		int num = population.size();
		Solution selected = population.get(rand.nextInt(num));

		for (int i = 0; i < tSize - 1; i++) {
			Solution other = population.get(rand.nextInt(num));
			if (other.isPreferable(selected))
				selected = other;
		}

		return selected;
	}
}
