package org.chaoticbits.gofirst.genetic.dicegenome;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.chaoticbits.gofirst.genetic.DiceGenome;
import org.junit.Test;

public class ToStringTest {

	private Random rand = null;

	@Test
	public void sortedToString() throws Exception {
		// 1 and 2 are flipped, but are in the same die
		DiceGenome genome = new DiceGenome(rand, DiceGenomeTest.TEST3);
		String expectedToString = new DiceGenome(rand, DiceGenomeTest.NORMAL).toString();
		assertEquals(expectedToString, genome.toString());
	}

	@Test
	public void sortedToStringNoChange() throws Exception {
		// 1 and 2 are flipped, but are in the same die
		DiceGenome genome = new DiceGenome(rand, DiceGenomeTest.TEST1);
		String expectedToString = "fit=null;[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, " //
				+ "11, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, " //
				+ "25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, " + //
				"38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48]";
		assertEquals(expectedToString, genome.toString());
	}
}
