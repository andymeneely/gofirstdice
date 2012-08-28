package org.chaoticbits.gofirst.genetic.algorithm;

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
	public List<Pair<Integer>> mutate(int size, int numStrata, int times) {
		List<Pair<Integer>> swaps = new ArrayList<Pair<Integer>>(times);
		for (int i = 0; i < times; i++) {
			int firstIndex = rand.nextInt(size);
			// swap with another die side so it's not all the same most of the time
			int otherStratum = rand.nextInt(numStrata - 1) + 1; // relative to this one (1..numStrata-1)
			int secondIndex = (firstIndex + otherStratum * (size / numStrata)) % size;
			swaps.add(new Pair<Integer>(firstIndex, secondIndex));
		}
		return swaps;
	}

}
