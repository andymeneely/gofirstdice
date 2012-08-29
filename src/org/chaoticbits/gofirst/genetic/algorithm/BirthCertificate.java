package org.chaoticbits.gofirst.genetic.algorithm;

/**
 * A record of how this genome was born - the parent and the type.
 * 
 * @author andy
 * 
 * @param <T>
 */
public class BirthCertificate<T> {
	public enum Type {
		INIT, MUTANT, CROSSOVER, IMMIGRANT
	}

	private final Type type;
	private final T[] parents;

	public BirthCertificate() {
		this.parents = null;
		this.type = Type.INIT;
	}

	public BirthCertificate(Type type, T... parents) {
		this.parents = parents;
		this.type = type;
	}

	public T[] getParents() {
		return parents;
	}

	public Type getType() {
		return type;
	}

}
