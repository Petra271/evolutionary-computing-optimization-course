package hr.fer.zemris.optjava.dz5.NSGA2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
import hr.fer.zemris.optjava.dz5.Crossover.ICrossover;
import hr.fer.zemris.optjava.dz5.Mutation.IMutation;
import hr.fer.zemris.optjava.dz5.Selection.ISelection;
import hr.fer.zemris.optjava.dz5.moopProblem.MOOPProblem;
import hr.fer.zemris.optjava.dz5.solution.Solution;
import hr.fer.zemris.optjava.dz5.utils.Util;

public class NSGA2 {

	private static Random rand = new Random();
	private MOOPProblem problem;
	private int popSize;
	private int maxIter;
	private IMutation mutation;
	private ISelection selection;
	private ICrossover crossover;
	private String solFilePath;
	private String objFilePath;

	public NSGA2(MOOPProblem problem, int popSize, int maxIter, ISelection selection, IMutation mutation,
			ICrossover crossover, String solFilePath, String objFilePath) {
		this.problem = problem;
		this.popSize = popSize;
		this.maxIter = maxIter;
		this.selection = selection;
		this.mutation = mutation;
		this.crossover = crossover;
		this.solFilePath = solFilePath;
		this.objFilePath = objFilePath;
	}

	public List<Solution> run() throws IOException {
		List<Solution> population = this.createPopulation();

		for (int i = 0; i < this.maxIter; i++) {
			Map<Integer, List<Solution>> frontsP = this.nondominantSort(population);
			System.out.format("Iteration %2d:", i + 1);
			System.out.format("    N(fronts) = %2d     ->   ", frontsP.size());
			System.out.println(Arrays.toString(frontsP.values().stream().map(x -> x.size()).toArray()));

			List<Solution> RPopulation = this.createQPopulation(population);
			RPopulation.addAll(population);

			Map<Integer, List<Solution>> fronts = this.nondominantSort(RPopulation);
			List<Solution> newPopulation = new ArrayList<>();

			int j = 1;
			while (fronts.containsKey(j) && newPopulation.size() + fronts.get(j).size() < this.popSize) {
				List<Solution> frontJ = fronts.get(j);
				this.calculateDistance(frontJ);
				newPopulation.addAll(frontJ);
				j += 1;
			}

			List<Solution> frontJ = fronts.get(j);
			this.calculateDistance(frontJ);
			frontJ.sort((Solution s1, Solution s2) -> Double.compare(s1.getDistance(), s2.getDistance()));
			Collections.reverse(frontJ);
			newPopulation
					.addAll(frontJ.stream().limit(this.popSize - newPopulation.size()).collect(Collectors.toList()));
			population = newPopulation;
		}

		Map<Integer, List<Solution>> fronts = this.nondominantSort(population);
		System.out.print("    N(fronts) = " + fronts.size() + "   ->   ");
		System.out.println(Arrays.toString(fronts.values().stream().map(x -> x.size()).toArray()));
		Util.writeToFile(population, this.solFilePath, this.objFilePath);
		return fronts.get(1);
	}

	// stvara pocetnu populaciju
	private List<Solution> createPopulation() {
		List<Solution> population = new ArrayList<>();

		for (int i = 0; i < this.popSize; i++) {
			int numOfVars = this.problem.getNumberOfVars();
			double[] values = new double[numOfVars];
			double[][] bounds = this.problem.getBounds();
			for (int j = 0; j < numOfVars; j++) {
				values[j] = bounds[j][0] + rand.nextFloat() * (bounds[j][1] - bounds[j][0]);
			}
			double obj[] = new double[this.problem.getNumberOfObjectives()];
			this.problem.evaluateSolution(values, obj);
			population.add(new Solution(values, obj));
		}
		return population;
	}

	// stvara novu populaciju koristeci operatore
	// selekcije, krizanja i mutacije nad trenutnom
	private List<Solution> createQPopulation(List<Solution> pop) {
		List<Solution> QPop = new ArrayList<>();

		while (QPop.size() < this.popSize) {
			Solution firstParent = this.selection.select(pop);
			Solution secondParent = this.selection.select(pop);
			Solution child = this.crossover.crossover(firstParent, secondParent, this.problem);
			this.mutation.mutate(child);

			double[][] bounds = this.problem.getBounds();
			for (int i = 0; i < child.getNumOfVars(); i++) {
				child.setValueAtIndex(i, Math.max(child.getValueAtIndex(i), bounds[i][0]));
				child.setValueAtIndex(i, Math.min(child.getValueAtIndex(i), bounds[i][1]));
			}

			double obj[] = new double[this.problem.getNumberOfObjectives()];
			this.problem.evaluateSolution(child.getSolution(), obj);
			child.setObjective(obj);
			QPop.add(child);
		}
		return QPop;
	}

	private Map<Integer, List<Solution>> nondominantSort(List<Solution> population) {
		Map<Integer, List<Solution>> fronts = new TreeMap<>();
		List<Solution> copy = new ArrayList<>(population);
		int ct = 1;
		fronts.put(ct, new ArrayList<>());

		for (Solution s1 : copy) {
			for (Solution s2 : copy) {
				if (s1.equals(s2))
					continue;

				if (s1.isDominating(s2)) {
					s1.addDominatedSolution(s2);
					s2.setDominated(s2.getDominatedBy() + 1);
				}
			}
		}

		for (Solution s : copy) {
			if (s.getDominatedBy() == 0) {
				fronts.get(ct).add(s);
				s.setFront(ct);
			}
		}

		List<Solution> curr = fronts.get(ct);
		ct += 1;
		while (curr.size() > 0) {

			for (Solution s : curr) {
				for (Solution d : s.getDominatedSolutions()) {
					int dominatedBy = d.getDominatedBy() - 1;
					d.setDominated(dominatedBy);
					if (dominatedBy == 0) {
						if (!fronts.containsKey(ct))
							fronts.put(ct, new ArrayList<>());
						if (!fronts.get(ct).contains(d)) {
							fronts.get(ct).add(d);
							d.setFront(ct);
						}
					}
				}
			}
			curr = fronts.get(ct);
			if (curr == null)
				break;
			ct += 1;
		}

		return fronts;
	}

	private void calculateDistance(List<Solution> population) {
		for (int i = 0; i < this.problem.getNumberOfObjectives(); i++) {
			final int index = i;

			population.sort(new Comparator<Solution>() {
				@Override
				public int compare(Solution s1, Solution s2) {
					return Double.compare(s1.getObjective()[index], s2.getObjective()[index]);
				}
			});

			population.get(0).setDistance(Double.POSITIVE_INFINITY);
			population.get(population.size() - 1).setDistance(Double.POSITIVE_INFINITY);
			double min = population.get(0).getObjective()[i];
			double max = population.get(population.size() - 1).getObjective()[i];

			for (int j = 1; j < population.size() - 1; j++) {
				double dist = population.get(j).getDistance();
				double prev = population.get(j - 1).getObjective()[i];
				double next = population.get(j + 1).getObjective()[i];
				dist += (next - prev) / (max - min);
				population.get(j).setDistance(dist);
			}
		}
	}
}
