package org.chaoticbits.gofirst.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.chaoticbits.gofirst.Die;
import org.chaoticbits.gofirst.genetic.algorithm.IFitnessEvaluator;

/**
 * Fitness function for DiceGenomes. Based on exhaustive analysis. See the main fitness function comment for
 * algorithmic details.
 * 
 * @author Andy Meneely
 * 
 */
public class ExhaustiveEvaluator implements IFitnessEvaluator<DiceGenome> {

	/**
	 * Given the genome, compute the fitness by exhaustively attempting every possible roll. Assumin. Given
	 * numTrials (from the constructor). Here's the algorithm:
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
		if (dice.size() != 4)
			throw new IllegalArgumentException("Only currently have support for 4 dice");
		for (int die0Side = 0; die0Side < DiceGenome.NUM_SIDES; die0Side++) {
			for (int die1Side = 0; die1Side < DiceGenome.NUM_SIDES; die1Side++) {
				for (int die2Side = 0; die2Side < DiceGenome.NUM_SIDES; die2Side++) {
					for (int die3Side = 0; die3Side < DiceGenome.NUM_SIDES; die3Side++) {
						victoryOrder = new ArrayList<DieSide>(numDice);
						victoryOrder.add(new DieSide(0, dice.get(0).getSides()[die0Side]));
						victoryOrder.add(new DieSide(1, dice.get(1).getSides()[die1Side]));
						victoryOrder.add(new DieSide(2, dice.get(2).getSides()[die2Side]));
						victoryOrder.add(new DieSide(3, dice.get(3).getSides()[die3Side]));
						tally(victoryOrder, tally);
					}
				}
			}
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
		double expected = 5184; // hardcoded for four players
		double totalMisses = 0.0d;
		for (int die = 0; die < numDice; die++) {
			for (int place = 0; place < numDice; place++) {
				totalMisses += Math.abs(tally[die][place] - expected); // |actual-expected|
			}
		}
		// double-counted totalMisses, numDice places per roll
		return 1.0 - totalMisses / (2 * 4 * 20736.0); // hardcoded for four players
	}

}
