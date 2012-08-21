package org.chaoticbits.gofirst.genetic;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class SimulationEvaluatorTest {

	@Test
	public void testComputeNormal() throws Exception {
		// normal means: die[0] < die[1] < die[2] < die[3]
		// which means that it's really, really lopsided - near zero
		Double fitness = new SimulationEvaluator(new Random(123)).fitness(new DiceGenome(DiceGenomeTest.NORMAL));
		assertEquals("fitness is terrible, only 25% right", 0.25, fitness, 0.001);
		for (int i = 1; i <= 12; i++) {
			System.out.print(i + ", " + (i + 12) + ", " + (i + 24) + ", " + (i + 36) + ",");
		}
	}

	@Test
	public void testComputeInterleaved() throws Exception {
		// interleaved is a little better
		Double fitness = new SimulationEvaluator(new Random(123)).fitness(new DiceGenome(DiceGenomeTest.INTERLEAVED));
		assertEquals("fitness is terrible, about 81% close", 0.832, fitness, 0.001);
	}
}