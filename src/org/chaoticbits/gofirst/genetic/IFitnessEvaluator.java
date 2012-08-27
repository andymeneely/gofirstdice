package org.chaoticbits.gofirst.genetic;

public interface IFitnessEvaluator<T> {
	public Double fitness(T genome);
}
