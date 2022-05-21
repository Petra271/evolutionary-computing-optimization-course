package hr.fer.zemris.optjava.dz4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Jama.Matrix;
import hr.fer.zemris.optjava.dz4.functions.IFunction;
import hr.fer.zemris.optjava.dz4.functions.TransferFunction;

public class Prijenosna {

	public static void main(String[] args) {
		if (args.length != 6)
			throw new IllegalArgumentException(
					"Expected arguments: max iterations, path, strategy, population size, crossover const, mutation const");

		int iters = Integer.parseInt(args[0]);
		String strategy = args[2];
		int popSize = Integer.parseInt(args[3]);
		double crossoverC = Double.parseDouble(args[4]);
		double mutationC = Double.parseDouble(args[5]);

		List<String> data = null;
		try {
			data = Files.readAllLines(Paths.get(args[1]));
		} catch (IOException e) {
			System.err.println("An error occurred while reading from file.");
			System.exit(1);
		}

		List<Double[]> bounds = new ArrayList<>();
		bounds.add(new Double[] { -7.0, 7.0 });
		bounds.add(new Double[] { -10.0, 10.0 });
		bounds.add(new Double[] { -5.0, 5.0 });
		bounds.add(new Double[] { -5.0, 5.0 });
		bounds.add(new Double[] { -5.0, 5.0 });
		bounds.add(new Double[] { -10.0, 10.0 });

		IFunction fun = new TransferFunction(data);
		DifferentialEvolution differentialEvolution = new DifferentialEvolution(fun, bounds, iters, strategy, popSize,
				crossoverC, mutationC);
		Matrix sol = differentialEvolution.runDifferentialEvolution();

		System.out.println("Rjesenje:" + Arrays.toString(sol.getArray()[0]));
		TransferFunction transfer = (TransferFunction) fun;
		int ct = 0;
		Matrix yVal = transfer.getY();
		for (double[] ex : transfer.getX().getArrayCopy()) {
			double y1 = yVal.get(ct++, yVal.getColumnDimension() - 1);
			double y2 = transfer.calculateSingle(sol, ex);
			System.out.format("tocno rješenje: %-15f |  dobiveno rjesenje: %-15f |  razlika: %-15f\n", y1, y2,
					Math.abs(y1 - y2));
		}
	}
}
