package org.chaoticbits.gofirst.genetic;

import java.util.List;
import java.util.Random;

import org.chaoticbits.gofirst.Die;

public class SimulationEvaluator implements IFitnessEvaluator {

	public static final long DEFAULT_NUM_TRIALS = 10000;
	private final long numTrials;
	private final Random rand;

	public SimulationEvaluator(Random rand) {
		this.rand = rand;
		this.numTrials = DEFAULT_NUM_TRIALS;
	}

	public SimulationEvaluator(Random rand, int numTrials) {
		this.rand = rand;
		this.numTrials = DEFAULT_NUM_TRIALS;
	}

	@Override
	public Double fitness(DiceGenome genome) {
		List<Die> dice = genome.getDice();
		int numDice = dice.size();
		long[] victories = simulate(dice);
		return compute(victories, numDice);
	}

	private long[] simulate(List<Die> dice) {
		long[] victories = new long[] { 0, 0, 0, 0 };
		for (long trial = 0; trial < numTrials; trial++) {
			int victor = -1;
			int highest = -1;
			for (int i = 0; i < dice.size(); i++) {
				int roll = dice.get(i).roll(rand);
				if (roll > highest) {
					victor = i;
					highest = roll;
				}
			}
			victories[victor]++;
		}
		return victories;
	}

	private Double compute(long[] victories, int numDice) {
		double totalMisses = 0;
		for (int victor = 0; victor < numDice; victor++) {
			totalMisses += Math.abs(victories[victor] - numTrials / numDice); // |actual-expected|
		}
		return 1.0 - totalMisses / (2 * numTrials); // double-counted the totalMisses, hence the 2
	}
}
