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
		fourPlayer(dice, victories);
		threePlayer(dice, victories);
		twoPlayer(dice, victories);
		return victories;
	}

	private void fourPlayer(List<Die> dice, long[] victories) {
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
	}

	private void threePlayer(List<Die> dice, long[] victories) {
		for (long trial = 0; trial < numTrials; trial++) {
			for (int playerOut = 0; playerOut < victories.length; playerOut++) {
				int victor = -1;
				int highest = -1;
				for (int player = 0; player < dice.size(); player++) {
					if (player != playerOut) { // skip over one player
						int roll = dice.get(player).roll(rand);
						if (roll > highest) {
							victor = player;
							highest = roll;
						}
					}
				}
				victories[victor]++;
			}
		}
	}

	private void twoPlayer(List<Die> dice, long[] victories) {
		for (long trial = 0; trial < numTrials; trial++) {
			for (int playerOut1 = 0; playerOut1 < victories.length - 1; playerOut1++) {
				for (int playerOut2 = playerOut1 + 1; playerOut2 < victories.length; playerOut2++) {
					int victor = -1;
					int highest = -1;
					for (int player = 0; player < dice.size(); player++) {
						if (player != playerOut1 && player != playerOut2) { // skip over two players
							int roll = dice.get(player).roll(rand);
							if (roll > highest) {
								victor = player;
								highest = roll;
							}
						}
					}
					victories[victor]++;
				}
			}
		}
	}

	private Double compute(long[] victories, int numDice) {
		double totalMisses = 0;
		long totalVictories = sum(victories);
		for (int victor = 0; victor < numDice; victor++) {
			totalMisses += Math.abs(victories[victor] - totalVictories / numDice); // |actual-expected|
		}
		return 1.0 - totalMisses / (2 * totalVictories); // double-counted the totalMisses, hence the 2
	}

	private long sum(long[] victories) {
		long total = 0;
		for (long victory : victories) {
			total += victory;
		}
		return total;
	}
}
