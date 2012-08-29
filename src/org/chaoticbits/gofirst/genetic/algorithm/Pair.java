package org.chaoticbits.gofirst.genetic.algorithm;

/**
 * A basic Pair class for representing an ordered pair of two things - typically integers.
 * 
 * @author Andy Meneely
 * 
 * @param <T>
 */
public class Pair<T> {
	public T first;
	public T second;

	public Pair(T first, T second) {
		this.first = first;
		this.second = second;
	}
}
