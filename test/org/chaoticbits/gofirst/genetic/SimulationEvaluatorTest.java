package org.chaoticbits.gofirst.genetic;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.chaoticbits.gofirst.Die;
import org.chaoticbits.gofirst.genetic.dicegenome.DiceGenomeTest;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SimulationEvaluator.class)
public class SimulationEvaluatorTest {

	private Random rand = null;

	@Test
	public void computeNormal() throws Exception {
		// normal: die[0] < die[1] < die[2] < die[3] always
		// which means that it's really, really lopsided
		Random rand = new Random(123);
		Double fitness = new SimulationEvaluator(rand, 100).fitness(new DiceGenome(rand, DiceGenomeTest.NORMAL));
		assertEquals("fitness is terrible, only 25% on average", 0.25, fitness, 0.01);
	}

	@Test
	public void computeReverse() throws Exception {
		// normal: die[0] > die[1] > die[2] > die[3] always
		// which means that it's really, really lopsided
		Random rand = new Random(123);
		Double fitness = new SimulationEvaluator(rand, 100).fitness(new DiceGenome(rand, DiceGenomeTest.REVERSE));
		assertEquals("fitness is terrible, only 25% on average", 0.25, fitness, 0.01);
	}

	@Test
	public void computeInterleaved() throws Exception {
		// interleaved is a little better
		Random rand = new Random(123);
		Double fitness = new SimulationEvaluator(rand, 100).fitness(new DiceGenome(rand, DiceGenomeTest.INTERLEAVED));
		assertEquals("fitness is better", 0.9, fitness, 0.001);
	}

	@Test
	public void computePerfect() throws Exception {
		SimulationEvaluator evaluator = PowerMock.createPartialMockAndInvokeDefaultConstructor(
				SimulationEvaluator.class, "simulateFullOrder");
		DiceGenome genome = PowerMock.createMock(DiceGenome.class);
		List<Die> dice = new DiceGenome(rand, DiceGenomeTest.NORMAL).getDice();

		EasyMock.expect(genome.getDice()).andReturn(dice).once();
		PowerMock.expectPrivate(evaluator, "simulateFullOrder", dice).andReturn(
				new long[][] { { 4000, 4000, 4000, 4000 }, { 4000, 4000, 4000, 4000 }, { 4000, 4000, 4000, 4000 },
						{ 4000, 4000, 4000, 4000 } });
		PowerMock.replayAll();

		assertEquals("Perfect fitness", 1.0, evaluator.fitness(genome), 0.000001);

		PowerMock.verifyAll();
	}
}