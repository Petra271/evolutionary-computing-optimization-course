package hr.fer.zemris.optjava.dz2;

import java.util.Arrays;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.functions.Function1;
import hr.fer.zemris.optjava.dz2.functions.Function2;

public class Jednostavno {
	public static void main(String[] args) {
		if (args.length != 2 && args.length != 4)
			throw new IllegalArgumentException("Expected two or four arguments (function, iterations, x1, x2).");

		Matrix point;
		if (args.length == 4)
			point = new Matrix(new double[][] { { Double.parseDouble(args[2]) }, { Double.parseDouble(args[3]) } });
		else {
			point = Matrix.random(2, 1);
			//System.out.println(Arrays.toString(point.getArray()[0]));
			System.out.println(Arrays.toString(point.transpose().getArray()[0]));
		}
		

		int iters = Integer.parseInt(args[1]);
		switch (args[0]) {
		case "1" -> NumOptAlgorithms.gradientDescent(new Function1(), iters, point);
		case "2" -> NumOptAlgorithms.gradientDescent(new Function2(), iters, point);
		}
	}
}
