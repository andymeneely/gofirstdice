package org.chaoticbits.gofirst.genetic;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.chaoticbits.gofirst.genetic.dicegenome.DiceGenomeTest;
import org.junit.Test;

public class SimulationEvaluatorTest {

	@Test
	public void testComputeNormal() throws Exception {
		// normal: die[0] < die[1] < die[2] < die[3] always
		// which means that it's really, really lopsided
		Random rand = new Random(123);
		Double fitness = new SimulationEvaluator(rand).fitness(new DiceGenome(rand, DiceGenomeTest.NORMAL));
		assertEquals("fitness is terrible, only 25% on average", 0.25, fitness, 0.01);
	}
	
	@Test
	public void testComputeReverse() throws Exception {
		// normal: die[0] > die[1] > die[2] > die[3] always
		// which means that it's really, really lopsided
		Random rand = new Random(123);
		Double fitness = new SimulationEvaluator(rand).fitness(new DiceGenome(rand, DiceGenomeTest.REVERSE));
		assertEquals("fitness is terrible, only 25% on average", 0.25, fitness, 0.01);
	}

	@Test
	public void testComputeInterleaved() throws Exception {
		// interleaved is a little better
		Random rand = new Random(123);
		Double fitness = new SimulationEvaluator(rand).fitness(new DiceGenome(rand, DiceGenomeTest.INTERLEAVED));
		assertEquals("fitness is better", 0.901, fitness, 0.001);
	}
}