package org.chaoticbits.gofirst.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.chaoticbits.gofirst.Die;

public class DiceGenome implements Comparable<DiceGenome> {
	public static final Integer NUM_SIDES = 12;
	public static final Integer NUM_DICE = 4;
	public static final Integer SIZE = NUM_DICE * NUM_SIDES;

	private final List<Integer> genome = new ArrayList<Integer>(NUM_SIDES * NUM_DICE);
	private final IFitnessEvaluator evaluator;
	private final Random rand;
	private Double fitness = null;

	public DiceGenome(Random rand, IFitnessEvaluator evaluator) {
		this.rand = rand;
		initDie(rand);
		this.evaluator = evaluator;
	}

	public DiceGenome(List<Integer> sides) {
		genome.addAll(sides);
		rand = new Random();
		evaluator = new SimulationEvaluator(rand);
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
		return evaluator.fitness(this);
	}

	public Double getFitness() {
		if (fitness == null)
			fitness = evaluator.fitness(this);
		return fitness;
	}

	@Override
	public int compareTo(DiceGenome o) {
		return -1 * getFitness().compareTo(o.getFitness()); // sort in reverse order - highest first
	}

	@Override
	public String toString() {
		return "fit=" + getFitness() + ";" + genome.toString();
	}
}
