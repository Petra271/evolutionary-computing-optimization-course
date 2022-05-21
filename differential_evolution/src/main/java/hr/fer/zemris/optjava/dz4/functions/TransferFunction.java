package hr.fer.zemris.optjava.dz4.functions;

import java.util.List;
import Jama.Matrix;
import hr.fer.zemris.optjava.dz4.Utility;

public class TransferFunction implements IFunction {
	private Matrix X;
	private Matrix y;

	public TransferFunction(List<String> data) {
		Matrix[] parsed = Utility.parseData(data);
		this.X = parsed[0];
		this.y = parsed[1];
	}

	@Override
	public int getNumOfVariables() {
		return 6;
	}

	@Override
	public double getValue(Matrix point) {
		double err = 0.0;
		int ct = 0;
		for (double[] ex : this.X.getArrayCopy()) {
			double exRes = calculateSingle(point, ex);
			err += Math.pow(this.y.get(ct++, y.getColumnDimension() - 1) - exRes, 2);
		}
		return err;
	}

	public double calculateSingle(Matrix point, double[] row) {
		double[] p = point.getColumnPackedCopy();
		double res = 0.0;
		res += p[0] * row[0];
		res += p[1] * Math.pow(row[0], 3) * row[1];
		res += p[2] * Math.pow(Math.E, p[3] * row[2]) * (1 + Math.cos(p[4] * row[3]));
		res += p[5] * row[3] * Math.pow(row[4], 2);
		return res;
	}
	
	public Matrix getX() {
		return this.X;
	}

	public Matrix getY() {
		return this.y;
	}
}
