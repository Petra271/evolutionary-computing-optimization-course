package hr.fer.zemris.optjava.dz5.solution;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Solution {

	private double[] solution;
	private double[] objective;
	private int numOfDominatedBySolutions;
	private Set<Solution> dominatesSolutions = new HashSet<>();
	private int front;
	private double distance;

	public Solution(double[] solution, double[] objective) {
		this.solution = solution;
		this.objective = objective;
		this.numOfDominatedBySolutions = 0;
		this.distance = 0;
	}

	public int getNumOfVars() {
		return this.solution.length;
	}

	public double[] getObjective() {
		return this.objective;
	}
	
	public void setObjective(double[] obj) {
		this.objective = obj;
	}

	public double[] getSolution() {
		return this.solution;
	}

	public void setDominated(int dominatedBy) {
		this.numOfDominatedBySolutions = dominatedBy;
	}

	public int getDominatedBy() {
		return this.numOfDominatedBySolutions;
	}

	public void addDominatedSolution(Solution s) {
		this.dominatesSolutions.add(s);
	}

	public Set<Solution> getDominatedSolutions() {
		return this.dominatesSolutions;
	}

	public void setFront(int front) {
		this.front = front;
	}

	public int getFront() {
		return this.front;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDistance() {
		return this.distance;
	}

	public double getValueAtIndex(int index) {
		return this.solution[index];
	}

	public void setValueAtIndex(int index, double val) {
		this.solution[index] = val;
	}

	public boolean isPreferable(Solution s) {
		if (this.front < s.front)
			return true;

		if (this.front == s.front && this.distance > s.distance)
			return true;

		return false;
	}

	public boolean isDominating(Solution s) {
		double[] obj1 = this.objective;
		double[] obj2 = s.getObjective();

		boolean dominating = false;
		for (int i = 0; i < this.getNumOfVars(); i++) {
			if (obj1[i] < obj2[i])
				dominating = true;

			if (obj1[i] > obj2[i]) {
				return false;
			}
		}
		return dominating;
	}

	@Override
	public boolean equals(Object obj) {
		return this.solution.equals(((Solution) obj).solution);
	}

	@Override
	public String toString() {
		return Arrays.toString(this.solution);
	}
}
