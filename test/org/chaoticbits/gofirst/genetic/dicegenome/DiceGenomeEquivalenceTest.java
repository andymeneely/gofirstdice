package org.chaoticbits.gofirst.genetic.dicegenome;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.chaoticbits.gofirst.genetic.DiceGenome;
import org.chaoticbits.gofirst.genetic.DiceGenomeTest;
import org.junit.Test;

public class DiceGenomeEquivalenceTest {

	private Random rand = null;

	@Test
	public void equivalentSameNumbers() throws Exception {
		DiceGenome g1 = new DiceGenome(rand, DiceGenomeTest.NORMAL);
		DiceGenome g2 = new DiceGenome(rand, DiceGenomeTest.NORMAL);
		assertTrue("equivalent", g1.equivalent(g2));
	}

	@Test
	public void equivalentDifferentNumbers() throws Exception {
		DiceGenome g1 = new DiceGenome(rand, DiceGenomeTest.NORMAL);
		DiceGenome g2 = new DiceGenome(rand, DiceGenomeTest.TEST3 /* 1 & 2 flipped */);
		assertTrue("equivalent", g1.equivalent(g2));
	}

	@Test
	public void notEquivalent() throws Exception {
		DiceGenome g1 = new DiceGenome(rand, DiceGenomeTest.NORMAL);
		DiceGenome g2 = new DiceGenome(rand, DiceGenomeTest.TEST1 /* 11 & 13 flipped */);
		assertFalse("not equivalent", g1.equivalent(g2));
	}

	@Test
	public void setSanityCheck() throws Exception {
		Set<Integer> oneOfThese = new HashSet<Integer>(Arrays.asList(1, 2, 3)), thingsIsNotLike = new HashSet<Integer>(
				Arrays.asList(3, 2, 1)), theOther = new HashSet<Integer>(Arrays.asList(3, 2, 4));
		assertEquals("deep equals?", oneOfThese, thingsIsNotLike);
		assertFalse("deep not equals?", oneOfThese.equals(theOther));
	}
}
