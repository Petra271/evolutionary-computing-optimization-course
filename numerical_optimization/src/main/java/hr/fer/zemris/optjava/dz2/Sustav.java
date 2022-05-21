package hr.fer.zemris.optjava.dz2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.functions.LinearSystem;

public class Sustav {

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

		IFunction fun = new LinearSystem(data);
		Matrix point = Matrix.random(fun.getNumOfVariables(), 1);
		Matrix res = NumOptAlgorithms.gradientDescent(fun, iters, point);
		System.out.println(fun.getValue(res));

	}
}
