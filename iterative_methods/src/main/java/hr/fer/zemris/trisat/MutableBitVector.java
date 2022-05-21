package hr.fer.zemris.trisat;

public class MutableBitVector extends BitVector {

	public MutableBitVector(boolean... bits) {
		this.bits = bits;
	}

	public MutableBitVector(int n) {
		this.bits = new boolean[n];
	}

	public void set(int index, boolean value) {
		if (index < 0 || index >= this.getSize())
			throw new IndexOutOfBoundsException("" + "Index is out of bounds for length " + this.bits.length);

		this.bits[index] = value;
	}
}
