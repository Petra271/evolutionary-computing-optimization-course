package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Random;

public class BitVector {

	protected boolean[] bits;

	public BitVector(Random rand, int numberOfBits) {
		this.bits = new boolean[numberOfBits];
		for (int i = 0; i < numberOfBits; i++)
			this.bits[i] = rand.nextBoolean();
	}

	public BitVector(boolean... bits) {
		this.bits = bits;
	}

	public BitVector(int n) {
		this.bits = new boolean[n];
	}

	public boolean get(int index) {
		return this.bits[index];
	}

	public int getSize() {
		return this.bits.length;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (boolean bit : this.bits)
			builder.append(bit ? "1" : "0");
		return builder.toString();
	}

	public MutableBitVector copy() {
		return new MutableBitVector(Arrays.copyOf(this.bits, this.getSize()));
	}
}
