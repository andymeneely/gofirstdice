package org.chaoticbits.gofirst.genetic.algorithm;

import java.util.Random;

public class GaussianRNGTransformer {

	private Random rand;

	public GaussianRNGTransformer(Random rand) {
		this.rand = rand;
	}

	public int nextInt(int max) {
		return nextInt(max, -1);
	}

	public int nextInt(int max, int avoid) {
		int next = 0;
		do {
			next = (int) (Math.abs(rand.nextGaussian()) * max);
		} while (next == avoid);
		return next;
	}

}
