package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public class NumOptAlgorithms {
	private final static double eps = 1e-4;

	public static Matrix gradientDescent(IFunction fun, int maxIters, Matrix point) {
		int count = 0;

		while (count++ < maxIters) {
			Matrix grad = fun.getGradient(point);
			// ako je magnituda dovoljno mala, mozemo stati
			if (grad.normF() < eps) {
				System.out.println("Pogreska: " + grad.normF());
				return point;
			}

			// smjer pretage d je minus gradijent funkcije
			Matrix d = grad.times(-1);

			// korak
			double lambda = bisect(fun, point, d);

			// novo rjesenje
			point = point.plus(d.times(lambda));
			System.out.println("Iteracija " + count + ": " + matrixString(point));
		}
		System.out.println("Potrosene iteracije. Rjesenje: " + matrixString(point));
		return point;
	}

	private static double bisect(IFunction fun, Matrix point, Matrix d) {
		double lamLower = 0;
		double lamUpper = 1;
		double lam;
		double thetaDer;

		// trebac pronaci lamUpper za koju ce f ponovno poceti rasti
		while (thetaDerivation(fun, point, d, lamUpper) < 0) {
			lamUpper *= 2;
		}
			
		lam = (lamLower + lamUpper) / 2;
		while (Math.abs(thetaDer = thetaDerivation(fun, point, d, lam)) > eps) {
			if (thetaDer > 0)
				lamUpper = lam;
			else
				lamLower = lam;
			lam = (lamLower + lamUpper) / 2;
		}
		return lam;
	}

	private static double thetaDerivation(IFunction fun, Matrix point, Matrix d, double lam) {
		Matrix grad = fun.getGradient(point.plus(d.times(lam)));
		return grad.transpose().times(d).get(0, 0); // rezultat je skalar, nalazi se u matrici na poziciji (0,0)
	}

	private static String matrixString(Matrix m) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		double[] row = m.getRowPackedCopy();
		for (double val : row) {
			sb.append(val).append(", ");
		}
		String s  = sb.substring(0, sb.lastIndexOf(","));
		s += "]";
		return s;
	}
}
