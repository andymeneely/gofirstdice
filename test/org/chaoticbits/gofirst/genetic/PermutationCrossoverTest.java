package org.chaoticbits.gofirst.genetic;

import static org.junit.Assert.assertArrayEquals;

import java.util.List;

import org.chaoticbits.gofirst.genetic.dicegenome.DiceGenomeTest;
import org.junit.Test;

public class PermutationCrossoverTest {
	private PermutationCrossover mutator = new PermutationCrossover();

	@Test
	public void mutateNormal() throws Exception {
		int cut = 14;
		List<Integer> child = mutator.crossOver(DiceGenomeTest.NORMAL, DiceGenomeTest.REVERSE, cut);
		assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 48, 47, 46, 45, 44, 43, 42,
				41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16,
				15 }, child.toArray(new Integer[48]));
	}

	@Test
	public void mutateBoundaries() throws Exception {
		// Cut is at the end
		List<Integer> child = mutator.crossOver(DiceGenomeTest.NORMAL, DiceGenomeTest.REVERSE, 47);
		assertArrayEquals(DiceGenomeTest.NORMAL.toArray(new Integer[48]), child.toArray(new Integer[48]));
		// Cut is at the beginning
		child = mutator.crossOver(DiceGenomeTest.NORMAL, DiceGenomeTest.REVERSE, 0);
		assertArrayEquals(DiceGenomeTest.REVERSE.toArray(new Integer[48]), child.toArray(new Integer[48]));
	}
}
