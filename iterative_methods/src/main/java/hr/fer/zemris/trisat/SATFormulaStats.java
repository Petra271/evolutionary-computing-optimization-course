package hr.fer.zemris.trisat;

public class SATFormulaStats {

	private SATFormula formula;
	private BitVector assignment;
	private double[] post;
	private int numOfSatisfied = 0;
	private final double percentageConstantUp = 0.01;
	private final double percentageConstantDown = 0.1;
	private final double percentageUnitAmount = 50;

	public SATFormulaStats(SATFormula formula) {
		this.formula = formula;
		this.post = new double[formula.getNumberOfClauses()];
	}

	// analizira se predano rješenje i pamte svi relevantni pokazatelji
	// primjerice, ažurira elemente polja post[...] ako drugi argument to dozvoli;
	// računa Z; ...
	public void setAssignment(BitVector assignment, boolean updatePercentages) {
		this.assignment = assignment;

		int numOfClauses = formula.getNumberOfClauses();

		this.numOfSatisfied = 0;
		for (int i = 0; i < numOfClauses; i++) {
			if (this.formula.getClause(i).isSatisfied(this.assignment))
				this.numOfSatisfied++;
		}

		if (updatePercentages) {

			for (int i = 0; i < numOfClauses; i++) {
				if (this.formula.getClause(i).isSatisfied(assignment))
					this.post[i] += (1.0 - this.post[i]) * this.percentageConstantUp;
				else
					this.post[i] += (0.0 - this.post[i]) * this.percentageConstantDown;
			}
		}

	}

	// vraća temeljem onoga što je setAssignment zapamtio: broj klauzula koje su
	// zadovoljene
	public int getNumberOfSatisfied() {
		return this.numOfSatisfied;
	}

	// vraća temeljem onoga što je setAssignment zapamtio
	public boolean isSatisfied() {
		return this.formula.isSatisfied(this.assignment);
	}

	// vraća temeljem onoga što je setAssignment zapamtio: suma korekcija klauzula
	// to je korigirani Z iz algoritma 3
	public double getPercentageBonus() {
		double percetangeBonus = 0.0;
		int numOfClauses = this.formula.getNumberOfClauses();

		for (int i = 0; i < numOfClauses; i++) {
			if (formula.getClause(i).isSatisfied(this.assignment))
				percetangeBonus += this.percentageUnitAmount * (1.0 - post[i]);
			else
				percetangeBonus -= this.percentageUnitAmount * (1.0 - post[i]);
		}
		return percetangeBonus;
	}

	// vraća temeljem onoga što je setAssignment zapamtio: procjena postotka za
	// klauzulu
	// to su elementi polja post[...]
	public double getPercentage(int index) {
		if (index < 0 || index >= this.post.length)
			throw new IndexOutOfBoundsException("Index out of bounds.");

		return this.post[index];
	}

	// resetira sve zapamćene vrijednosti na početne (tipa: zapamćene statistike)
	public void reset() {
		this.post = new double[formula.getNumberOfClauses()];
		this.numOfSatisfied = 0;
		this.assignment = null;
	}
}
