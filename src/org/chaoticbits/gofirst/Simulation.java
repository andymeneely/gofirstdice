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

	public static List<Integer> crafted = Arrays.asList(1, 8, 11, 14, 17, 24, 27, 30, 33, 40, 41, 47, //
			2, 5, 12, 15, 18, 23, 26, 29, 36, 39, 42, 48, //
			3, 6, 9, 16, 19, 22, 25, 32, 35, 38, 43, 46, //
			4, 7, 10, 13, 20, 21, 28, 31, 34, 37, 44, 45);

	public static List<Integer> actualGoFirst = Arrays.asList(1, 8, 11, 14, 19, 22, 27, 30, 35, 38, 41, 48, //
			2, 7, 10, 15, 18, 23, 26, 31, 34, 39, 42, 47, //
			3, 6, 12, 13, 17, 24, 25, 32, 36, 37, 43, 46, //
			4, 5, 9, 16, 20, 21, 28, 29, 33, 40, 44, 45);

	public static List<Integer> bestYet = Arrays.asList(5, 13, 15, 19, 21, 23, 25, 32, 35, 38, 40, 47, 3, 7, 8, 11,
			12, 14, 29, 30, 33, 41, 44, 46, 2, 9, 16, 17, 18, 24, 26, 34, 36, 39, 42, 43, 1, 4, 6, 10, 20, 22, 27, 28,
			31, 37, 45, 48);

	public static List<Integer> candidate = Arrays.asList(5, 13, 15, 19, 21, 23, 25, 32, 35, 38, 40, 47, 3, 7, 8, 11,
			12, 14, 29, 30, 33, 41, 44, 46, 2, 9, 16, 17, 18, 24, 26, 34, 36, 39, 42, 43, 1, 4, 6, 10, 20, 22, 27, 28,
			31, 37, 45, 48);

	public static void main(String[] args) {
		rand = new MersenneTwisterRNG();
		DiceGenome genome = new DiceGenome(rand, candidate);
		genome.getFitness(); // hit the cache
		System.out.println("Candidate:\n " + genome);
	}
}
