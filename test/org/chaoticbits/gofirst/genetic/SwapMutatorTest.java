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
	public void swapOnce() throws Exception {
		expect(rand.nextInt(8)).andReturn(5).once();
		expect(rand.nextInt(3)).andReturn(0).once(); // next stratum over
		ctrl.replay();
		List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8); // two per stratum
		List<Integer> mutant = mutator.mutate(input, 4, 1);
		assertNotSame("no modification here!", mutant, input);
		assertArrayEquals("swap is stratified", new Integer[] { 1, 2, 3, 4, 5, 8, 7, 6 },
				mutant.toArray(new Integer[7]));
		ctrl.verify();
	}

	@Test
	public void swapTwice() throws Exception {
		expect(rand.nextInt(8)).andReturn(5).once();
		expect(rand.nextInt(3)).andReturn(0).once(); // next stratum over
		expect(rand.nextInt(8)).andReturn(4).once();
		expect(rand.nextInt(3)).andReturn(2).once(); // three strata over
		ctrl.replay();
		List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8); // two per stratum
		List<Integer> mutant = mutator.mutate(input, 4, 2);
		assertNotSame("no modification here!", mutant, input);
		assertArrayEquals("swap is stratified", new Integer[] { 1, 2, 5, 4, 3, 8, 7, 6 },
				mutant.toArray(new Integer[7]));
		ctrl.verify();
	}
}
