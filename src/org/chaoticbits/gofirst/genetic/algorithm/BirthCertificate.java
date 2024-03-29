package org.chaoticbits.gofirst.genetic.algorithm;

/**
 * A record of how this genome was born - the parent and the type.
 * 
 * @author Andy Meneely
 * 
 * @param <T>
 */
public class BirthCertificate<T> {
	public enum Type {
		INIT, MUTANT, CROSSOVER, IMMIGRANT
	}

	private final Type type;
	private final T[] parents;

	/**
	 * General contract is that INIT and IMMIGRANT should have zero parents, MUTANT should have one parent,
	 * and CROSSOVER should have two parents.
	 * 
	 * @param type
	 * @param parents
	 */
	public BirthCertificate(Type type, T... parents) {
		this.parents = parents;
		this.type = type;
		if (parents == null || type == null)
			throw new IllegalArgumentException("Parents and type must be non-null on a birth certificate.");
	}

	/**
	 * Returns a (potentially zero-length) array of parents.
	 * 
	 * @return
	 */
	public T[] getParents() {
		return parents;
	}

	public Type getType() {
		return type;
	}

}
