package org.chaoticbits.gofirst.genetic;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
	public void swapTwice() throws Exception {
		expect(rand.nextInt(7)).andReturn(5).once();
		expect(rand.nextInt(7)).andReturn(6).once();
		expect(rand.nextInt(7)).andReturn(1).once();
		expect(rand.nextInt(7)).andReturn(2).once();
		ctrl.replay();
		List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
		List<Integer> mutant = mutator.mutate(input, 2);
		assertNotSame("no modification here!", mutant, input);
		assertArrayEquals(new Integer[] { 1, 3, 2, 4, 5, 7, 6 }, mutant.toArray(new Integer[7]));

		ctrl.verify();
	}
}
