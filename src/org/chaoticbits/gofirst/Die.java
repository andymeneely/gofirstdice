package org.chaoticbits.gofirst;

import java.util.Random;

public class Die {

	private int[] sides;

	public Die(int[] sides) {
		this.sides = sides;
	}

	public int[] getSides() {
		return sides;
	}
	
	public int roll(Random rand){
		return sides[rand.nextInt(sides.length)];
	}

}
