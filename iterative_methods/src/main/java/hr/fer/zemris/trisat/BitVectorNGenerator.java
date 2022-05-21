package hr.fer.zemris.trisat;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BitVectorNGenerator implements Iterable<MutableBitVector> {

	private BitVector assignment;

	public BitVectorNGenerator(BitVector assignment) {
		this.assignment = assignment;
	}

	private class MutableBitVectorIterator implements Iterator<MutableBitVector> {
		// broj susjeda je zapravo velicina vektora
		private MutableBitVector currentNeighbor;
		private int neighborIndex = assignment.getSize() - 1;

		private MutableBitVectorIterator() {
		}

		@Override
		public boolean hasNext() {
			return this.neighborIndex >= 0;
		}

		@Override
		public MutableBitVector next() {
			if (!this.hasNext())
				throw new NoSuchElementException("There are no more neighbours left.");

			this.currentNeighbor = assignment.copy();
			boolean currentValueAtIndex = this.currentNeighbor.get(this.neighborIndex);
			this.currentNeighbor.set(this.neighborIndex--, !currentValueAtIndex);
			return this.currentNeighbor;
		}
	}

	// Vraća lijeni iterator koji na svaki next() računa sljedećeg susjeda
	@Override
	public Iterator<MutableBitVector> iterator() {
		return new MutableBitVectorIterator();
	}

	// Vraća kompletno susjedstvo kao jedno polje
	public MutableBitVector[] createNeighborhood() {
		MutableBitVector[] neighborhood = new MutableBitVector[this.assignment.getSize()];

		int index = 0;
		for (MutableBitVector neighbor : this) {
			neighborhood[index++] = neighbor;
		}
		return neighborhood;
	}

}
