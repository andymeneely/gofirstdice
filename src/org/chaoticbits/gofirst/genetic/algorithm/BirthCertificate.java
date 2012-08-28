package org.chaoticbits.gofirst.genetic.algorithm;

public class BirthCertificate<T> {
	public enum Type {
		INIT, MUTANT, CROSSOVER, IMMIGRANT
	}

	private final Type type;
	private final T parent;

	public BirthCertificate() {
		this.parent = null;
		this.type = Type.INIT;
	}

	public BirthCertificate(T parent, Type type) {
		this.parent = parent;
		this.type = type;
	}

	public T getParent() {
		return parent;
	}

	public Type getType() {
		return type;
	}

}
