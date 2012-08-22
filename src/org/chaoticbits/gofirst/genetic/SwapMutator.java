package org.chaoticbits.gofirst.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SwapMutator {

	private final Random rand;

	public SwapMutator(Random rand) {
		this.rand = rand;
	}

	public List<Integer> mutate(List<Integer> input, int times) {
		List<Integer> mutant = new ArrayList<Integer>();
		mutant.addAll(input);
		for (int i = 0; i < times; i++) {
			int firstIndex = rand.nextInt(mutant.size());
			// swap with another die side so it's not all the same most of the time
			int secondIndex = (firstIndex + (rand.nextInt(DiceGenome.NUM_DICE) + 1) * DiceGenome.NUM_SIDES) % mutant.size();
			int temp = mutant.get(firstIndex);
			mutant.set(firstIndex, mutant.get(secondIndex));
			mutant.set(secondIndex, temp);
		}
		return mutant;
	}

}
