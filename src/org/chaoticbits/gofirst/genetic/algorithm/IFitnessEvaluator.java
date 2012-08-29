package org.chaoticbits.gofirst.genetic.algorithm;

/**
 * Interface for computing fitness
 * 
 * @author Andy Meneely
 * 
 * @param <T>
 *            Presumably the Genome type
 */
public interface IFitnessEvaluator<T> {
	public Double fitness(T genome);
}
