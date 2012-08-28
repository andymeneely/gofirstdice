package org.chaoticbits.gofirst.genetic.algorithm;

import java.util.Random;

/**
 * A RNG transformer that takes a normally-distributed number and scales it to a discrete ranking. Mean is
 * centered at rank 0, and the least likely is at max-1
 * @author andy
 * 
 */
public class GaussianRNGTransformer {

	private Random rand;

	public GaussianRNGTransformer(Random rand) {
		this.rand = rand;
	}

	/**
	 * Returns a normally-distributed integer ranging from 0..max-1, with 0 being most likely
	 * @param max
	 * @param avoid
	 * @return
	 */
	public int nextInt(int max) {
		return nextInt(max, -1);
	}

	/**
	 * Returns a normally-distributed integer ranging from 0..max-1. Avoidance of the one number works by
	 * just taking another random number
	 * 
	 * @param max
	 * @param avoid
	 * @return
	 */
	public int nextInt(int max, int avoid) {
		int next = 0;
		do {
			next = (int) (Math.abs(rand.nextGaussian()) * max);
		} while (next == avoid);
		if (next >= max) // off the end of the bell curve
			return max - 1;
		else
			return next;
	}

}
