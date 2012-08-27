package org.chaoticbits.gofirst.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SwapMutator {

	private final Random rand;

	public SwapMutator(Random rand) {
		this.rand = rand;
	}

	/**
	 * Mutate a list of integers by swapping
	 * 
	 * Assume the list of integers is stratified into "numStrata" portions. Pick a random first integer, then
	 * a random, different stratum, and swap with the equivalent in that stratum. Do all this "times" times.
	 * 
	 * @param input
	 * @param numStrata
	 * @param times
	 * @return
	 */
	public List<Integer> mutate(List<Integer> input, int numStrata, int times) {
		List<Integer> mutant = new ArrayList<Integer>();
		mutant.addAll(input); // copy!
		for (int i = 0; i < times; i++) {
			int firstIndex = rand.nextInt(mutant.size());
			// swap with another die side so it's not all the same most of the time
			int otherStratum = rand.nextInt(numStrata - 1) + 1; // relative to this one (1..numStrata-1)
			int secondIndex = (firstIndex + otherStratum * (mutant.size() / numStrata)) % mutant.size();
			swap(mutant, firstIndex, secondIndex);
		}
		return mutant;
	}

	/*
	 * A basic in-place swap
	 */
	private void swap(List<Integer> mutant, int firstIndex, int secondIndex) {
		int temp = mutant.get(firstIndex);
		mutant.set(firstIndex, mutant.get(secondIndex));
		mutant.set(secondIndex, temp);
	}

}
