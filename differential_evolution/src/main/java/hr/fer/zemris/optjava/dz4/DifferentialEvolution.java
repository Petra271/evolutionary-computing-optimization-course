package hr.fer.zemris.optjava.dz4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz4.functions.IFunction;

public class DifferentialEvolution {

	private int popSize;
	private IFunction fun;
	private static Random rand = new Random();
	private List<Double[]> bounds;
	private Matrix bestSolution;
	private int bestIndex;
	private double minError = Double.POSITIVE_INFINITY;
	private int maxIters;
	private double mutationC;
	private double crossoverC;
	private String strategy;

	public DifferentialEvolution(IFunction fun, List<Double[]> bounds, int maxIters, String strategy, int popSize,
			double mutationC, double crossoverC) {
		this.popSize = popSize;
		this.fun = fun;
		this.bounds = bounds;
		this.maxIters = maxIters;
		this.mutationC = mutationC;
		this.crossoverC = crossoverC;
		this.strategy = strategy;
		this.runDifferentialEvolution();
	}

	public Matrix runDifferentialEvolution() {
		List<Matrix> population = this.createPopulation();
		this.evaluatePopulation(population);
		int numOfVars = this.fun.getNumOfVariables();

		for (int i = 0; i < this.maxIters; i++) {
			List<Matrix> newPopulation = new ArrayList<>();

			for (int j = 0; j < this.popSize; j++) {
				Matrix target = population.get(j);

				Integer[] indexArray = this.generateRandomIndexes(j, 2);
				Matrix x1 = population.get(indexArray[0]);
				Matrix x2 = population.get(indexArray[1]);

				double mutationCJ = 0.0;

				Matrix base = null;
				if (this.strategy.equals("DE/best/1/bin")) {
					base = this.bestSolution;
					mutationCJ = mutationC + 0.001 * (rand.nextDouble() - 0.5);
				} else if (this.strategy.equals("DE/target-to-best/1/bin")) {
					mutationCJ = mutationC;
					base = target.plus(this.bestSolution.minus(target).times(mutationCJ));					
				}

				Matrix mutant = base.plus(x1.minus(x2).times(mutationCJ));
				Matrix trial = this.crossover(numOfVars, target, mutant, j);

				double trialErr = this.fun.getValue(trial);
				if (this.fun.getValue(trial) <= this.fun.getValue(target)) {
					newPopulation.add(trial);
					if (trialErr < this.minError) {
						this.minError = trialErr;
						this.bestSolution = trial;
					}
				} else {
					newPopulation.add(target);
				}
			}
			population = newPopulation;
			System.out.println("Iteration " + (i + 1) + ":" + Arrays.toString(this.bestSolution.getArray()[0])
					+ " -> pogreska: " + this.minError);
		}
		return this.bestSolution;
	}

	private List<Matrix> createPopulation() {
		List<Matrix> population = new ArrayList<>();

		for (int i = 0; i < this.popSize; i++) {
			int numOfVars = this.fun.getNumOfVariables();
			double[] values = new double[numOfVars];
			for (int j = 0; j < numOfVars; j++) {
				values[j] = this.bounds.get(j)[0] + rand.nextFloat() * (this.bounds.get(j)[1]-this.bounds.get(j)[0]);
			}
			population.add(new Matrix(values, 1));
		}
		return population;
	}

	private void evaluatePopulation(List<Matrix> population) {
		for (Matrix m : population) {
			double error = this.fun.getValue(m);
			if (error <= this.minError) {
				this.minError = error;
				this.bestSolution = m;
			}
		}
	}

	private Matrix crossover(int numOfVars, Matrix target, Matrix mutant, int j) {
		int jrand = rand.nextInt(numOfVars);
		Matrix trial = target.copy();

		for (int index = 0; index < numOfVars; index++) {
			if (rand.nextDouble() <= this.crossoverC || jrand == j) {
				trial.set(0, index, mutant.get(0, index));
			}
		}
		return trial;
	}

	private Integer[] generateRandomIndexes(int j, int num) {
		Set<Integer> indexes = new HashSet<>();
		while (indexes.size() < num) {
			int index = rand.nextInt(this.popSize);
			if (index != j && index != this.bestIndex)
				indexes.add(index);
		}

		Integer[] indexArray = new Integer[indexes.size()];
		indexes.toArray(indexArray);
		return indexArray;
	}

}
