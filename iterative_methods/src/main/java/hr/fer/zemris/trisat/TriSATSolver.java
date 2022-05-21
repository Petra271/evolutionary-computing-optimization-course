package hr.fer.zemris.trisat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import hr.fer.zemris.trisat.algoritmi.Algorithm1;
import hr.fer.zemris.trisat.algoritmi.Algorithm2;
import hr.fer.zemris.trisat.algoritmi.Algorithm3;
import hr.fer.zemris.trisat.algoritmi.Algorithm4;
import hr.fer.zemris.trisat.algoritmi.Algorithm5;
import hr.fer.zemris.trisat.algoritmi.Algorithm6;
import hr.fer.zemris.trisat.algoritmi.IOptAlgorithm;

public class TriSATSolver {

	public static void main(String[] args) {
		if (args.length != 2)
			throw new IllegalArgumentException("Expected arguments: algorithm index, file path.");

		SATFormula formula = parseFile(args[1]);

		IOptAlgorithm algorithm = null;
		switch (args[0]) {
		case "1" -> algorithm = new Algorithm1(formula);
		case "2" -> algorithm = new Algorithm2(formula);
		case "3" -> algorithm = new Algorithm3(formula);
		case "4" -> algorithm = new Algorithm4(formula);
		case "5" -> algorithm = new Algorithm5(formula);
		case "6" -> algorithm = new Algorithm6(formula);
		default -> throw new IllegalArgumentException("Unexpected value: " + args[0]);
		}
		
		System.out.println("Formula:\n" + formula);
		System.out.println("\nSolution:");
		algorithm.solve(Optional.empty());
	}

	public static SATFormula parseFile(String path) {
		Clause[] clauses = null;
		int numOfVars = 0;
		int numOfClauses = 0;
		int index = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
			String line;
			while (!(line = br.readLine()).startsWith("%")) {
				if (line.startsWith("c"))
					continue;

				String[] values = line.trim().replaceAll(" +", " ").split(" ");

				if (values[0].equals("p")) {
					numOfVars = Integer.parseInt(values[2]);
					numOfClauses = Integer.parseInt(values[3]);
					clauses = new Clause[numOfClauses];
				} else {
					int[] result = Arrays.stream(values).mapToInt(Integer::parseInt).toArray();
					result = Arrays.copyOfRange(result, 0, result.length - 1);
					clauses[index++] = new Clause(result);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot open file.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("An error occurred while reading from file.");
			System.exit(1);
		}

		SATFormula formula = new SATFormula(numOfVars, clauses);
		return formula;
	}
}
