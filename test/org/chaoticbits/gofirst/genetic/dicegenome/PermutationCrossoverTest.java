package org.chaoticbits.gofirst.genetic.dicegenome;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.chaoticbits.gofirst.genetic.DiceGenome;
import org.junit.Before;
import org.junit.Test;

public class PermutationCrossoverTest {

	private DiceGenome normal;
	private DiceGenome reverse;
	private int cut;

	@Before
	public void init() {
		normal = new DiceGenome(null, DiceGenomeTest.NORMAL);
		reverse = new DiceGenome(null, DiceGenomeTest.REVERSE);
	}

	@Test
	public void crossOverNormal() throws Exception {
		cut = 14;
		DiceGenome child = normal.crossOver(reverse, cut);
		DiceGenome expected = new DiceGenome(null, DiceGenomeTest.NORMAL_CROSS_REVERSE_14);
		assertEquals(expected, child);
		assertNotSame("child is a different object from parents", normal, child);
		assertNotSame("child is a different object from parents", reverse, child);
	}

	@Test
	public void crossOverOpposite() throws Exception {
		cut = 14;
		DiceGenome child = reverse.crossOver(normal, cut);
		DiceGenome expected = new DiceGenome(null, DiceGenomeTest.REVERSE_CROSS_NORMAL_14);
		assertEquals(expected, child);
		assertNotSame("child is a different object from parents", normal, child);
		assertNotSame("child is a different object from parents", reverse, child);
	}

	@Test
	public void cutAtBeginning() throws Exception {
		cut = 0;
		DiceGenome child = normal.crossOver(reverse, cut);
		DiceGenome expected = new DiceGenome(null, DiceGenomeTest.REVERSE);
		assertEquals(expected, child);
	}

	@Test
	public void cutAtEnd() throws Exception {
		cut = 47;
		DiceGenome child = normal.crossOver(reverse, cut);
		DiceGenome expected = new DiceGenome(null, DiceGenomeTest.NORMAL);
		assertEquals(expected, child);
	}
}
