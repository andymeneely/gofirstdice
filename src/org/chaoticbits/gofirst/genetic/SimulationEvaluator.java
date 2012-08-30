package org.chaoticbits.gofirst.genetic;

import static org.chaoticbits.gofirst.genetic.DiceGenome.NUM_DICE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

	public static final long DEFAULT_NUM_TRIALS = NUM_DICE * NUM_DICE * 1000;
	private final long numTrials;
	private final Random rand;

	@SuppressWarnings("unused")
	private SimulationEvaluator(){
		numTrials = DEFAULT_NUM_TRIALS;
		rand = null;
	}
	
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
	 * <li>Determine player order on each roll, tallying up how many times each die was in each place</li>
	 * <li>Compute E(Place), i.e. the expected number of times a player was supposed to be in a given place
	 * (numTrials/numDice)</li>
	 * <li>Compute the 1.0 - |E(Place) - Actual|/(numTrials*2), or the absolute value of the difference
	 * between expected and actual, times two (since we double-counted), and subtract from the maximum 1.0
	 * since we want high fitness.
	 * <li>
	 * </ol>
	 * 
	 * There is no need to compute inner combinations of fewer players than dice, since it's as if you did
	 * roll the die, but then ignored it.
	 * 
	 * Worst fitness is 25% (completely lopsided), poor fitness is about 95%, and a great fitness is 99.99%
	 * 
	 * @param genome
	 * @return the average percentage over or under the expected number of trials won by each player
	 */
	public Double fitness(DiceGenome genome) {
		List<Die> dice = genome.getDice();
		return compute(dice.size(), simulateFullOrder(dice));
	}

	private static class DieSide {
		private Integer die;
		private Integer side;

		public DieSide(int die, int side) {
			this.die = die;
			this.side = side;
		}
	}

	private long[][] simulateFullOrder(List<Die> dice) {
		final int numDice = dice.size();
		long tally[][] = new long[numDice][numDice]; // [player][place]
		List<DieSide> victoryOrder;
		for (long trial = 0; trial < numTrials; trial++) {
			victoryOrder = new ArrayList<SimulationEvaluator.DieSide>(numDice);
			for (int die = 0; die < dice.size(); die++) {
				int roll = dice.get(die).roll(rand);
				victoryOrder.add(new DieSide(die, roll));
			}
			tally(victoryOrder, tally);
		}
		return tally;
	}

	private void tally(List<DieSide> victoryOrder, long[][] tally) {
		Collections.sort(victoryOrder, new Comparator<DieSide>() {
			@Override
			public int compare(DieSide o1, DieSide o2) {
				return -1 * o1.side.compareTo(o2.side); // highest first
			}
		});
		int place = 0;
		for (DieSide dieSide : victoryOrder) {
			tally[dieSide.die][place++]++;
		}
	}

	private double compute(final int numDice, long[][] tally) {
		// |actual-expected|/(2*total), a little fairer if expected evenly divides, hence the default
		double expected = numTrials / numDice; // each roll had numDice additions to the tally
		double totalMisses = 0.0d;
		for (int die = 0; die < numDice; die++) {
			for (int place = 0; place < numDice; place++) {
				totalMisses += Math.abs(tally[die][place] - expected); // |actual-expected|
			}
		}
		// double-counted totalMisses, numDice places per roll
		return 1.0 - totalMisses / (2 * numDice * numTrials);
	}

}
