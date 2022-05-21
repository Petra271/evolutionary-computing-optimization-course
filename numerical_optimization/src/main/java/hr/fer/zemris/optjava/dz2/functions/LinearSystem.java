package hr.fer.zemris.optjava.dz2.functions;

import java.util.List;
import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IFunction;
import hr.fer.zemris.optjava.dz2.Utility;

public class LinearSystem implements IFunction {
	private Matrix X;
	private Matrix y;

	public LinearSystem(List<String> funs) {
		Matrix[] parsed = Utility.parseData(funs);
		this.X = parsed[0];
		this.y = parsed[1];
	}

	@Override
	public int getNumOfVariables() {
		return this.X.getColumnDimension();
	}

	@Override
	public double getValue(Matrix point) {
		Matrix err = this.X.times(point).minus(this.y);
		return Math.pow(err.normF(), 2);
	}

	@Override
	public Matrix getGradient(Matrix point) {
		Matrix err = this.X.times(point).minus(this.y);
		Matrix grad = this.X.transpose().times(2).times(err);
		return grad;
	}
}
