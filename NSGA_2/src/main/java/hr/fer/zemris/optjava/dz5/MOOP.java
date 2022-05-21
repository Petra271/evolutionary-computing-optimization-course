package hr.fer.zemris.optjava.dz5;

import java.io.IOException;

import hr.fer.zemris.optjava.dz5.Crossover.ArithmeticCrossover;
import hr.fer.zemris.optjava.dz5.Crossover.ICrossover;
import hr.fer.zemris.optjava.dz5.Mutation.IMutation;
import hr.fer.zemris.optjava.dz5.Mutation.NormalMutation;
import hr.fer.zemris.optjava.dz5.NSGA2.NSGA2;
import hr.fer.zemris.optjava.dz5.Selection.CrowdedTournamentSelection;
import hr.fer.zemris.optjava.dz5.Selection.ISelection;
import hr.fer.zemris.optjava.dz5.moopProblem.MOOPProblem;
import hr.fer.zemris.optjava.dz5.moopProblem.Problem1;
import hr.fer.zemris.optjava.dz5.moopProblem.Problem2;

public class MOOP {

	public static void main(final String[] args) throws IOException {

		double sigma = 0;
		int tournSize = 0;
		double lam = 0;
		int problemId = 0;
		int popSize = 0;
		int maxIter = 0;

		if (args.length != 6) {
			System.err.println(
					"Expected 6 arguments: problem id (1 or 2), population size, number of iterations, sigma, lambda, tournament size.");
			System.exit(1);
		} else {
			try {
				problemId = Integer.parseInt(args[0]);
				popSize = Integer.parseInt(args[1]);
				maxIter = Integer.parseInt(args[2]);
				sigma = Double.parseDouble(args[3]);
				lam = Double.parseDouble(args[4]);
				tournSize = Integer.parseInt(args[5]);
			} catch (NumberFormatException e) {
				System.err.println("Cannot parse input.");
				System.exit(1);
			}
		}

		MOOPProblem problem;
		problem = problemId == 1 ? new Problem1() : new Problem2();
		ISelection selection = new CrowdedTournamentSelection(tournSize);
		ICrossover crossover = new ArithmeticCrossover(lam);
		IMutation mutation = new NormalMutation(sigma);

		String solFile = "izlaz-dec" + problemId;
		String objFile = "izlaz-obj" + problemId;

		NSGA2 nsga = new NSGA2(problem, popSize, maxIter, selection, mutation, crossover, solFile, objFile);
		nsga.run();
	}
}
