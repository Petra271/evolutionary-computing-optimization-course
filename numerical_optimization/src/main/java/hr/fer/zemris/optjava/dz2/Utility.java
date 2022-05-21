package hr.fer.zemris.optjava.dz2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Jama.Matrix;

public class Utility {

	public static Matrix[] parseData(List<String> funs) {
		List<double[]> functions = new ArrayList<>();
		List<Double> yValues = new ArrayList<>();

		for (String fun : funs) {
			if (fun.trim().startsWith("#"))
				continue;

			String[] stringValues = fun.substring(1, fun.length() - 1).split(",\\s*");
			double[] values = Arrays.stream(stringValues).mapToDouble(x -> Double.parseDouble(x)).toArray();

			functions.add(Arrays.copyOfRange(values, 0, values.length - 1));
			yValues.add(values[values.length - 1]);
		}

		double[][] functionsArr = new double[functions.size()][functions.get(0).length];
		double[] yArr = new double[functions.size()];

		for (int i = 0; i < functions.size(); i++) {
			functionsArr[i] = functions.get(i);
			yArr[i] = yValues.get(i);
		}

		return new Matrix[] { new Matrix(functionsArr), new Matrix(yArr, yArr.length) };
	}
}
