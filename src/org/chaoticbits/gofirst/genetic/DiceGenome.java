package org.chaoticbits.gofirst.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.chaoticbits.gofirst.Die;

public class DiceGenome implements IFitnessEvaluator {
	public static final Integer NUM_SIDES = 12;
	public static final Integer NUM_DICE = 4;

	private final List<Integer> genome = new ArrayList<Integer>(NUM_SIDES * NUM_DICE);
	private final IFitnessEvaluator evaluator;

	public DiceGenome(Random rand, IFitnessEvaluator evaluator) {
		initDie(rand);
		this.evaluator = evaluator;
	}

	public DiceGenome(List<Integer> sides) {
		genome.addAll(sides);
		evaluator = new SimulationEvaluator();
	}

	private void initDie(Random rand) {
		for (int i = 1; i <= NUM_SIDES * NUM_DICE; i++)
			genome.add(i);
		Collections.shuffle(genome, rand);
	}

	public List<Die> getDice() {
		List<Die> dice = new ArrayList<Die>(NUM_DICE);
		for (int i = 0; i < NUM_DICE; i++) {
			int[] sides = new int[NUM_SIDES];
			for (int j = i * NUM_SIDES; j < (i + 1) * NUM_SIDES; j++) {
				sides[j % NUM_SIDES] = genome.get(j);
			}
			dice.add(new Die(sides));
		}
		return dice;
	}

	public List<Integer> getGenome() {
		return genome;
	}

	public Double fitness() {
		return evaluator.fitness();
	}
}
