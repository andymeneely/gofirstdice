package org.chaoticbits.gofirst.genetic;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class SimulationEvaluatorTest {

	@Test
	public void testComputeNormal() throws Exception {
		// normal means: die[0] < die[1] < die[2] < die[3]
		// which means that it's really, really lopsided
		Random rand = new Random(123);
		Double fitness = new SimulationEvaluator(rand).fitness(new DiceGenome(rand, DiceGenomeTest.NORMAL));
		assertEquals("fitness is terrible, only 35.4% right", 0.354, fitness, 0.01);
	}

	@Test
	public void testComputeInterleaved() throws Exception {
		// interleaved is a little better
		Random rand = new Random(123);
		Double fitness = new SimulationEvaluator(rand).fitness(new DiceGenome(rand, DiceGenomeTest.INTERLEAVED));
		assertEquals("fitness is better, about 89% close", 0.656, fitness, 0.001);
	}
}