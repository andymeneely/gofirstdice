package org.chaoticbits.gofirst.genetic;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.chaoticbits.gofirst.Die;
import org.junit.Test;

public class DiceGenomeTest {

	public static final List<Integer> NORMAL = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
			18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44,
			45, 46, 47, 48);
	public static final List<Integer> INTERLEAVED = Arrays.asList(1, 13, 25, 37, 2, 14, 26, 38, 3, 15, 27, 39, 4, 16,
			28, 40, 5, 17, 29, 41, 6, 18, 30, 42, 7, 19, 31, 43, 8, 20, 32, 44, 9, 21, 33, 45, 10, 22, 34, 46, 11, 23,
			35, 47, 12, 24, 36, 48);
	// 11 and 13 are flipped
	public static final List<Integer> TEST1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 13, 12, 11, 14, 15, 16, 17,
			18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44,
			45, 46, 47, 48);
	public static final List<Integer> REVERSE = Arrays.asList(48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35,
			34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8,
			7, 6, 5, 4, 3, 2, 1);

	@Test
	public void getDiceWorksNormal() throws Exception {
		DiceGenome genome = new DiceGenome(NORMAL);
		List<Die> dice = genome.getDice();
		assertEquals("size", 4, dice.size());
		assertEquals(1, dice.get(0).getSides()[0]);
		assertEquals(48, dice.get(3).getSides()[11]);
		assertEquals(29, dice.get(2).getSides()[4]);
	}

	@Test
	public void getDiceWorksTest1() throws Exception {
		DiceGenome genome = new DiceGenome(TEST1);
		List<Die> dice = genome.getDice();
		assertEquals("size", 4, dice.size());
		assertEquals(13, dice.get(0).getSides()[10]);
		assertEquals(12, dice.get(0).getSides()[11]);
		assertEquals(11, dice.get(1).getSides()[0]);
	}
}
