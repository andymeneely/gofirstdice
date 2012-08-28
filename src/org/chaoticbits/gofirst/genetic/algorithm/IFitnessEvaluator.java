package org.chaoticbits.gofirst.genetic.algorithm;

public interface IFitnessEvaluator<T> {
	public Double fitness(T genome);
}
