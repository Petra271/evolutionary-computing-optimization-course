package hr.fer.zemris.trisat;

public class SATFormula {

	private Clause[] clauses;
	private int numberOfVariables;

	public SATFormula(int numberOfVariables, Clause[] clauses) {
		this.clauses = clauses;
		this.numberOfVariables = numberOfVariables;
	}

	public int getNumberOfVariables() {
		return this.numberOfVariables;
	}

	public int getNumberOfClauses() {
		return this.clauses.length;
	}

	public Clause getClause(int index) {
		if (index < 0 || index >= this.getNumberOfClauses())
			throw new IndexOutOfBoundsException("Index out of bounds.");
		return this.clauses[index];
	}

	public boolean isSatisfied(BitVector assignment) {
		// produkt klauzula je satisfied ako je svaka klauzula satisfied, inace nije
		for (Clause c : this.clauses) {
			if (!c.isSatisfied(assignment))
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (Clause c : this.clauses)
			builder.append("(").append(c).append(")");

		return builder.toString();
	}
}
