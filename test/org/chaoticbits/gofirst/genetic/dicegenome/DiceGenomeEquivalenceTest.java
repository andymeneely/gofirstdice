package org.chaoticbits.gofirst.genetic.dicegenome;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.chaoticbits.gofirst.genetic.DiceGenome;
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
	public void equivalentDifferentOrderOfSets() throws Exception {
		DiceGenome g1 = new DiceGenome(rand, DiceGenomeTest.NORMAL);
		// First two dice are flipped, so they should still be equivalent
		DiceGenome g2 = new DiceGenome(rand, DiceGenomeTest.NORMAL_DIE_ORDER_CHANGED);
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

	@Test
	public void setOfSetsSanityCheck() throws Exception {
		Set<Set<Integer>> oneOfTheseThings = new HashSet<Set<Integer>>();
		oneOfTheseThings.add(new HashSet<Integer>(Arrays.asList(1, 2, 3)));
		oneOfTheseThings.add(new HashSet<Integer>(Arrays.asList(4, 5, 6)));

		Set<Set<Integer>> isNotLike = new HashSet<Set<Integer>>();
		isNotLike.add(new HashSet<Integer>(Arrays.asList(4, 5, 6))); // different order of sets - equivalent
		isNotLike.add(new HashSet<Integer>(Arrays.asList(1, 2, 3)));

		Set<Set<Integer>> theOther = new HashSet<Set<Integer>>();
		theOther.add(new HashSet<Integer>(Arrays.asList(1, 2, 3))); // different second set - not equivalent
		theOther.add(new HashSet<Integer>(Arrays.asList(7, 8, 9)));

		assertEquals("deep equals", oneOfTheseThings, isNotLike);
		assertFalse("deep not equals", oneOfTheseThings.equals(theOther));
		assertFalse("deep not equals", isNotLike.equals(theOther));
	}
}
