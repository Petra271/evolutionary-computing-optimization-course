package hr.fer.zemris.optjava.dz2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.functions.TransferFunction;

public class Prijenosna {

	public static void main(String[] args) {
		if (args.length != 2)
			throw new IllegalArgumentException("Expected two arguments: max iterations, path.");

		int iters = Integer.parseInt(args[0]);
		List<String> data = null;
		try {
			data = Files.readAllLines(Paths.get(args[1]));
		} catch (IOException e) {
			System.err.println("An error occurred while reading from file.");
			System.exit(1);
		}

		IFunction fun = new TransferFunction(data);
		Matrix point = Matrix.random(fun.getNumOfVariables(), 1);
		// Matrix pointt = new Matrix(new double[][] { {-0.160, 2.320, -3.500, -2.660,
		// 4.620 }});
		Matrix coeffs = NumOptAlgorithms.gradientDescent(fun, iters, point);
		System.out.println(Arrays.toString(coeffs.getArray()[0]));
		System.out.println(TransferFunction.calculateSingle(coeffs, new double[] { -1.380, -3.780, -1.240, -1.150, 4.190 }));
	}
}
