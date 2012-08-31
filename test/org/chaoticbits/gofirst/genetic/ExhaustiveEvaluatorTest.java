package org.chaoticbits.gofirst.genetic;

import static org.junit.Assert.assertEquals;

import org.chaoticbits.gofirst.Simulation;
import org.chaoticbits.gofirst.genetic.dicegenome.DiceGenomeTest;
import org.junit.Test;

public class ExhaustiveEvaluatorTest {
	@Test
	public void perfect() throws Exception {
		//from the actual known "go first" dice that we know are perfect
		assertEquals("known solution should be perfect", 1.0, new DiceGenome(new ExhaustiveEvaluator(),
				Simulation.actualGoFirst).getFitness(), 0.000001);
	}

	@Test
	public void normal() throws Exception {
		// normal: die[0] < die[1] < die[2] < die[3] always
		// which means that it's really, really lopsided
		assertEquals("known solution should be perfect", 0.25, new DiceGenome(new ExhaustiveEvaluator(),
				DiceGenomeTest.NORMAL).getFitness(), 0.000001);
	}
}
