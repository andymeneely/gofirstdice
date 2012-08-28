package org.chaoticbits.gofirst.genetic.dicegenome;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Random;

import org.chaoticbits.gofirst.genetic.DiceGenome;
import org.chaoticbits.gofirst.genetic.SimulationEvaluator;
import org.chaoticbits.gofirst.genetic.algorithm.IFitnessEvaluator;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class FitnessCached {

	private final IMocksControl ctrl = EasyMock.createControl();
	private final IFitnessEvaluator<DiceGenome> evaluator = ctrl.createMock(SimulationEvaluator.class);
	private final Random rand = new Random(123);

	@Before
	public void init() {
		ctrl.reset();
	}

	@Test
	public void fitnessCalledOnce() throws Exception {
		DiceGenome genome = new DiceGenome(rand, evaluator);
		expect(evaluator.fitness(genome)).andReturn(1.5).once();
		ctrl.replay();
		assertEquals(1.5, genome.getFitness(), 0.0001);
		assertEquals(1.5, genome.getFitness(), 0.0001);
		assertEquals(1.5, genome.getFitness(), 0.0001);
		ctrl.verify();
	}
}
