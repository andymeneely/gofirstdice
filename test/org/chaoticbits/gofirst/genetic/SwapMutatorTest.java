package org.chaoticbits.gofirst.genetic;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.chaoticbits.gofirst.genetic.algorithm.Pair;
import org.chaoticbits.gofirst.genetic.algorithm.SwapMutator;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class SwapMutatorTest {

	private final IMocksControl ctrl = EasyMock.createControl();
	private final Random rand = ctrl.createMock(Random.class);

	private final SwapMutator mutator = new SwapMutator(rand);

	@Before
	public void init() {
		ctrl.reset();
	}

	@Test
	public void swapOnce() throws Exception {
		expect(rand.nextInt(8)).andReturn(5).once();
		expect(rand.nextInt(3)).andReturn(0).once(); // next stratum over
		ctrl.replay();
		List<Pair<Integer>> swaps = mutator.mutate(8, 4, 1);
		assertEquals("one swap only", 1, swaps.size());
		assertEquals("first index", 5, swaps.get(0).first.intValue());
		assertEquals("second index", 7, swaps.get(0).second.intValue());
		ctrl.verify();
	}

	@Test
	public void swapTwice() throws Exception {
		expect(rand.nextInt(8)).andReturn(5).once();
		expect(rand.nextInt(3)).andReturn(0).once(); // next stratum over
		expect(rand.nextInt(8)).andReturn(4).once();
		expect(rand.nextInt(3)).andReturn(2).once(); // three strata over
		ctrl.replay();
		List<Pair<Integer>> swaps = mutator.mutate(8, 4, 2);
		assertEquals("one swap only", 2, swaps.size());
		assertEquals("first index", 5, swaps.get(0).first.intValue());
		assertEquals("second index", 7, swaps.get(0).second.intValue());
		assertEquals("first index", 4, swaps.get(1).first.intValue());
		assertEquals("second index", 2, swaps.get(1).second.intValue());
		ctrl.verify();
	}
}
