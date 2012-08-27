package org.chaoticbits.gofirst.genetic;

import java.util.List;
import java.util.Random;

import org.chaoticbits.gofirst.Die;

/**
 * Fitness function for DiceGenomes. Based on simulations. See the main fitness function comment for
 * algorithmic details.
 * @author andy
 * 
 */
public class SimulationEvaluator implements IFitnessEvaluator<DiceGenome> {

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

	/**
	 * Given the genome, compute the fitness by simluating dice rolls. Given numTrials (from the
	 * constructor), roll the dice for numTrials times, and count the number of victories for each player.
	 * Then divide numTrials by the number of players - that's the expected number of victories for each
	 * player. Take the total number of victories for each player minus the expected number of victories, and
	 * average the percentage correct.
	 * 
	 * Do this for each combination of players, up to DiceGenome.NUM_DICE. For example, all four players.
	 * Then all three combinations of three players. Then all combinations of two players. Do an unweighted
	 * average of each of those and that's your fitness.
	 * 
	 * A poor fitness will be about 94%, and a great fitness is 99.99%
	 * 
	 * @param genome
	 * @return the average percentage over or under the expected number of trials won by each player
	 */
	public Double fitness(DiceGenome genome) {
		List<Die> dice = genome.getDice();
		double fitness = 0;
		fitness += compute(fourPlayer(dice, new long[DiceGenome.NUM_DICE]), DiceGenome.NUM_DICE);
		fitness += compute(threePlayer(dice, new long[DiceGenome.NUM_DICE]), DiceGenome.NUM_DICE);
		fitness += compute(twoPlayer(dice, new long[DiceGenome.NUM_DICE]), DiceGenome.NUM_DICE);
		return fitness / 4.0d; // unweighted average
	}

	private long[] fourPlayer(List<Die> dice, long[] victories) {
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

	private long[] threePlayer(List<Die> dice, long[] victories) {
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
		return victories;
	}

	private long[] twoPlayer(List<Die> dice, long[] victories) {
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
		return victories;
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
