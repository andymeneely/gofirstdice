package org.chaoticbits.gofirst;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {

	private static Random rand;

	public static void main(String[] args) {
		rand = new Random();
		Die[] dice = { new Die(new int[] { 1, 8, 11, 14, 17, 24, 27, 30, 33, 40, 41, 47 }), //
				new Die(new int[] { 2, 5, 12, 15, 18, 23, 26, 29, 36, 39, 42, 48 }), //
				new Die(new int[] { 3, 6, 9, 16, 19, 22, 25, 32, 35, 38, 43, 46 }), //
				new Die(new int[] { 4, 7, 10, 13, 20, 21, 28, 31, 34, 37, 44, 45 }), //
		};
		simulate("All four", dice[0], dice[1], dice[2], dice[3]);
		for (int out = 0; out < dice.length; out++) {
			ArrayList<Die> diceSubset = new ArrayList<Die>();
			for (int i = 0; i < dice.length; i++) {
				if (i != out)
					diceSubset.add(dice[i]);
			}
			simulate("Three, without " + out, diceSubset.toArray(new Die[] {}));
		}
		for (int first = 0; first < dice.length; first++) {
			for (int second = first + 1; second < dice.length; second++) {
				if (second != first)
					simulate("Only " + first + ", " + second, dice[first], dice[second]);
			}
		}
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
