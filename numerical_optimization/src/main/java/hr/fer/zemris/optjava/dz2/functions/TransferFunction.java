package hr.fer.zemris.optjava.dz2.functions;

import java.util.List;
import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IFunction;
import hr.fer.zemris.optjava.dz2.Utility;

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
			err += Math.pow(this.y.get(ct++, y.getColumnDimension()-1) - exRes, 2);
		}
		return err;
	}

	@Override
	public Matrix getGradient(Matrix point) {
		double[] gradient = new double[this.getNumOfVariables()];
		double[] p = point.getColumnPackedCopy();
		int ct = 0;

		for (double[] ex : this.X.getArrayCopy()) {
			double exRes = calculateSingle(point, ex);
			double diff = exRes - this.y.get(ct++, y.getColumnDimension()-1);

			gradient[0] += diff * ex[0];
			gradient[1] += diff * Math.pow(ex[0], 3) * ex[1];
			gradient[2] += diff * Math.pow(Math.E, p[3] * ex[2]) * (1 + Math.cos(p[4] * ex[3]));
			gradient[3] += diff * p[2] * ex[2] * Math.pow(Math.E, p[3] * ex[2]) * (1 + Math.cos(p[4] * ex[3]));
			gradient[4] += diff * -p[2] * ex[3] * Math.pow(Math.E, p[3] * ex[2]) * Math.sin(p[4] * ex[3]);
			gradient[5] += diff * ex[3] * Math.pow(ex[4], 2);
		}

		Matrix grad = new Matrix(gradient, gradient.length);
		return grad.times(1.0/grad.normF());
	}

	public static double calculateSingle(Matrix point, double[] row) {
		double[] p = point.getColumnPackedCopy();
		double res = 0.0;
		res += p[0] * row[0];
		res += p[1] * Math.pow(row[0], 3) * row[1];
		res += p[2] * Math.pow(Math.E, p[3] * row[2]) * (1 + Math.cos(p[4] * row[3]));
		res += p[5] * row[3] * Math.pow(row[4], 2);
		return res;
	}
}
