package hr.fer.zemris.optjava.dz4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz4.functions.TransferFunction;

public class Compare {

	public static void main(String[] args) {
		List<String> data = null;
		try {
			data = Files.readAllLines(Paths.get(args[0]));
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

		TransferFunction fun = new TransferFunction(data);

		int n = 10;
		double err1[] = new double[n];
		for (int i = 0; i < n; i++) {
			DifferentialEvolution differentialEvolution1 = new DifferentialEvolution(fun, bounds, 300, "DE/best/1/bin",
					100, 0.5, 0.2);
			Matrix sol1 = differentialEvolution1.runDifferentialEvolution();
			err1[i] = fun.getValue(sol1);
		}

		double err2[] = new double[n];
		for (int i = 0; i < n; i++) {
			DifferentialEvolution differentialEvolution2 = new DifferentialEvolution(fun, bounds, 300,
					"DE/target-to-best/1/bin", 100, 0.5, 0.2);
			Matrix sol2 = differentialEvolution2.runDifferentialEvolution();
			err2[i] = fun.getValue(sol2);
		}

		System.out.println("Prosjecna pogreska za DE/best/1/bin: " + mean(err1));
		System.out.println("Medijan pogresaka za DE/best/1/bin: " + median(err1));
		System.out.println("Prosjecna pogreska za DE/target-to-best/1/bin: " + mean(err2));
		System.out.println("Medijan pogresaka za DE/target-to-best/1/bin: " + median(err2));
	}

	public static double median(double values[]) {
		Arrays.sort(values);
		double median;
		if (values.length % 2 == 0)
			median = ((double) values[values.length / 2] + (double) values[values.length / 2 - 1]) / 2;
		else
			median = (double) values[values.length / 2];
		return median;
	}

	public static double mean(double values[]) {
		double sum = Arrays.stream(values).sum();
		return sum / values.length;
	}

}
