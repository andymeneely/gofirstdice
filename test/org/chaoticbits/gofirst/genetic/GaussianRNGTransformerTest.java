package org.chaoticbits.gofirst.genetic;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.chaoticbits.gofirst.genetic.algorithm.GaussianRNGTransformer;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class GaussianRNGTransformerTest {

	private final IMocksControl ctrl = EasyMock.createControl();
	private final Random rand = ctrl.createMock(Random.class);

	@Before
	public void init() {
		ctrl.reset();
	}

	@Test
	public void regularUsage() throws Exception {
		expect(rand.nextGaussian()).andReturn(0.0).once();// mean is most likely
		ctrl.replay();
		GaussianRNGTransformer rng = new GaussianRNGTransformer(rand);
		assertEquals(0, rng.nextInt(100));
		ctrl.verify();
	}

	@Test
	public void roundDown() throws Exception {
		expect(rand.nextGaussian()).andReturn(0.009).once();// mean is most likely
		ctrl.replay();
		GaussianRNGTransformer rng = new GaussianRNGTransformer(rand);
		assertEquals(0, rng.nextInt(100));
		ctrl.verify();
	}

	@Test
	public void fartherDown() throws Exception {
		expect(rand.nextGaussian()).andReturn(0.1).once();// less likely, but still likely
		ctrl.replay();
		GaussianRNGTransformer rng = new GaussianRNGTransformer(rand);
		assertEquals(10, rng.nextInt(100));
		ctrl.verify();
	}

	@Test
	public void negativeIsTheSame() throws Exception {
		expect(rand.nextGaussian()).andReturn(-0.1).once();// less likely, but still likely. as likely as 0.1
		ctrl.replay();
		GaussianRNGTransformer rng = new GaussianRNGTransformer(rand);
		assertEquals(10, rng.nextInt(100));
		ctrl.verify();
	}

	@Test
	public void unlikelyIn1() throws Exception {
		expect(rand.nextGaussian()).andReturn(0.99).once();// very unlikely
		ctrl.replay();
		GaussianRNGTransformer rng = new GaussianRNGTransformer(rand);
		assertEquals(99, rng.nextInt(100));
		ctrl.verify();
	}

	@Test
	public void unlikelyOver1() throws Exception {
		expect(rand.nextGaussian()).andReturn(1.1).once();// very unlikely
		ctrl.replay();
		GaussianRNGTransformer rng = new GaussianRNGTransformer(rand);
		assertEquals(99, rng.nextInt(100));
		ctrl.verify();
	}

	@Test
	public void unlikelyAt1() throws Exception {
		expect(rand.nextGaussian()).andReturn(1.0).once();// very unlikely
		ctrl.replay();
		GaussianRNGTransformer rng = new GaussianRNGTransformer(rand);
		assertEquals(99, rng.nextInt(100));
		ctrl.verify();
	}

	@Test
	public void avoidPrevious() throws Exception {
		expect(rand.nextGaussian()).andReturn(0.99).once();// very unlikely
		expect(rand.nextGaussian()).andReturn(0.1).once();// very unlikely
		ctrl.replay();
		GaussianRNGTransformer rng = new GaussianRNGTransformer(rand);
		assertEquals(10, rng.nextInt(100, 99));
		ctrl.verify();
	}

}
