package org.chaoticbits.gofirst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.chaoticbits.gofirst.genetic.DiceGenome;
import org.uncommons.maths.random.MersenneTwisterRNG;

/**
 * A first crack at hand-crafting a GoFirst die, and checking the simulations and fitness
 * 
 * @author Andy Meneely
 * 
 */
public class Simulation {

	private static Random rand;

	private static List<Integer> crafted = Arrays.asList(1, 8, 11, 14, 17, 24, 27, 30, 33, 40, 41, 47, //
			2, 5, 12, 15, 18, 23, 26, 29, 36, 39, 42, 48, //
			3, 6, 9, 16, 19, 22, 25, 32, 35, 38, 43, 46, //
			4, 7, 10, 13, 20, 21, 28, 31, 34, 37, 44, 45);

	private static List<Integer> actualGoFirst = Arrays.asList(1, 8, 11, 14, 19, 22, 27, 30, 35, 38, 41, 48, //
			2, 7, 10, 15, 18, 23, 26, 31, 34, 39, 42, 47, //
			3, 6, 12, 13, 17, 24, 25, 32, 36, 37, 43, 46, //
			4, 5, 9, 16, 20, 21, 28, 29, 33, 40, 44, 45);

	private static List<Integer> anotherTest = Arrays.asList(14, 33, 45, 24, 35, 16, 8, 9, 2, 39, 22, 44, 46, 5, 38,
			40, 12, 18, 34, 37, 17, 3, 25, 20, 21, 1, 31, 27, 19, 13, 43, 47, 15, 42, 7, 28, 4, 26, 11, 32, 29, 41, 10,
			6, 23, 48, 36, 30);

	public static void main(String[] args) {
		rand = new MersenneTwisterRNG();
		DiceGenome genome = new DiceGenome(rand, actualGoFirst);
		List<Die> dice = genome.getDice();
		simulate("All four", dice.get(0), dice.get(1), dice.get(2), dice.get(3));
		for (int out = 0; out < dice.size(); out++) {
			ArrayList<Die> diceSubset = new ArrayList<Die>();
			for (int i = 0; i < dice.size(); i++) {
				if (i != out)
					diceSubset.add(dice.get(i));
			}
			simulate("Three, without " + out, diceSubset.toArray(new Die[] {}));
		}
		for (int first = 0; first < dice.size(); first++) {
			for (int second = first + 1; second < dice.size(); second++) {
				if (second != first)
					simulate("Only " + first + ", " + second, dice.get(first), dice.get(second));
			}
		}
		genome.getFitness();
		System.out.println("Fitness: " + genome);
	}

	private static void simulate(String description, Die... dice) {
		System.out.println("Simulation for " + description);
		int[] victories = new int[] { 0, 0, 0, 0 };
		for (long trial = 0; trial < 10000000L; trial++) {
			int victor = -1;
			int highest = -1;
			for (int i = 0; i < dice.length; i++) {
				int roll = dice[i].roll(rand);
				if (roll > highest) {
					victor = i;
					highest = roll;
				}
			}
			victories[victor]++;
		}
		for (int i = 0; i < victories.length; i++) {
			System.out.println("\t# victories for " + i + " was " + victories[i]);
		}
	}
}
