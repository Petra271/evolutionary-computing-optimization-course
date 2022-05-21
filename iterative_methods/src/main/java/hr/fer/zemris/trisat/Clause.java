package hr.fer.zemris.trisat;

public class Clause {

	private int[] indexes;

	public Clause(int[] indexes) {
		this.indexes = indexes;
	}

	public int getSize() {
		return this.indexes.length;
	}

	public int getLiteral(int index) {
		if (index < 0 || index > this.getSize())
			throw new IndexOutOfBoundsException("Index out of bounds.");
		return Math.abs(this.indexes[index]) - 1;
	}

	public boolean isSatisfied(BitVector assignment) {
		for (int i = 0; i < this.getSize(); i++) {
			boolean val = assignment.get(this.getLiteral(i));
			// ako je indeks pozitivan i vrijednost 1 ili
			// indeks negativan(komplement) i vrijednost 0,
			// dodjela zadovoljava trenutnu klauzulu
			if (this.indexes[i] > 0 && val || this.indexes[i] < 0 && !val)
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < this.getSize(); i++) {
			int literal = this.indexes[i];

			if (i > 0)
				builder.append(literal > 0 ? "+" : "-");
			builder.append("x").append(Math.abs(literal));
		}
		return builder.toString();
	}
}
