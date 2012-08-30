package org.chaoticbits.gofirst.genetic;

import java.util.List;
import java.util.Random;

import org.chaoticbits.gofirst.Die;
import org.chaoticbits.gofirst.genetic.algorithm.IFitnessEvaluator;

/**
 * Fitness function for DiceGenomes. Based on simulations. See the main fitness function comment for
 * algorithmic details.
 * 
 * @author Andy Meneely
 * 
 */
public class SimulationEvaluator implements IFitnessEvaluator<DiceGenome> {

	public static final long DEFAULT_NUM_TRIALS = 100000;
	private final long numTrials;
	private final Random rand;

	public SimulationEvaluator(Random rand) {
		this.rand = rand;
		this.numTrials = DEFAULT_NUM_TRIALS;
	}

	public SimulationEvaluator(Random rand, int numTrials) {
		this.rand = rand;
		this.numTrials = numTrials;
	}

	/**
	 * Given the genome, compute the fitness by simluating dice rolls. Given numTrials (from the
	 * constructor). Here's the algorithm:
	 * 
	 * <ol>
	 * <li>Roll all of the dice for numTrials times</li>
	 * <li>Count the number of victories for each player</li>
	 * <li>Compute E(Victories), i.e. the expected number of victories for each player (numTrials/numDice)</li>
	 * <li>Compute the 1.0 - |E(Victories) - Victories|/(numTrials*2), or the absolute value of the
	 * difference between expected and actual, times two (since we double-counted), and subtract from the
	 * maximum 1.0 since we want high fitness.
	 * <li>
	 * </ol>
	 * 
	 * There is no need to compute inner combinations of fewer players than dice, since it's as if you did
	 * roll the die, but then ignored it.
	 * 
	 * A poor fitness will be about 94%, and a great fitness is 99.99%
	 * 
	 * @param genome
	 * @return the average percentage over or under the expected number of trials won by each player
	 */
	public Double fitness(DiceGenome genome) {
		List<Die> dice = genome.getDice();
		return compute(fourPlayer(dice, new long[DiceGenome.NUM_DICE]), DiceGenome.NUM_DICE);
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
