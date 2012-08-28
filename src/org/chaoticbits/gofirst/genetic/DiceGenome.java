package org.chaoticbits.gofirst.genetic;

import static org.chaoticbits.gofirst.genetic.algorithm.BirthCertificate.Type.CROSSOVER;
import static org.chaoticbits.gofirst.genetic.algorithm.BirthCertificate.Type.MUTANT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.chaoticbits.gofirst.Die;
import org.chaoticbits.gofirst.genetic.algorithm.BirthCertificate;
import org.chaoticbits.gofirst.genetic.algorithm.IFitnessEvaluator;
import org.chaoticbits.gofirst.genetic.algorithm.Pair;

public class DiceGenome implements Comparable<DiceGenome> {
	public static final Integer NUM_SIDES = 12;
	public static final Integer NUM_DICE = 4;
	public static final Integer SIZE = NUM_DICE * NUM_SIDES;

	private final List<Integer> genome = new ArrayList<Integer>(NUM_SIDES * NUM_DICE);
	private final IFitnessEvaluator<DiceGenome> evaluator;
	private final Random rand;
	private final BirthCertificate<DiceGenome> birthCertificate;
	private Double fitness = null;

	public DiceGenome(Random rand, IFitnessEvaluator<DiceGenome> evaluator,
			BirthCertificate<DiceGenome> birthCertificate) {
		init(rand);
		this.rand = rand;
		this.evaluator = evaluator;
		this.birthCertificate = birthCertificate;
	}

	public DiceGenome(Random rand, IFitnessEvaluator<DiceGenome> evaluator) {
		init(rand);
		this.rand = rand;
		this.evaluator = evaluator;
		this.birthCertificate = new BirthCertificate<DiceGenome>(); // default (INIT)
	}

	public DiceGenome(Random rand, List<Integer> sides) {
		genome.addAll(sides);
		this.rand = rand;
		evaluator = new SimulationEvaluator(rand);
		this.birthCertificate = new BirthCertificate<DiceGenome>(); // default (INIT)
	}

	public DiceGenome(Random rand, BirthCertificate<DiceGenome> birthCertificate, List<Integer> sides) {
		genome.addAll(sides);
		this.rand = rand;
		evaluator = new SimulationEvaluator(rand);
		this.birthCertificate = birthCertificate;
	}

	private void init(Random rand) {
		for (int i = 1; i <= NUM_SIDES * NUM_DICE; i++)
			genome.add(i);
		Collections.shuffle(genome, rand);
	}

	/**
	 * Converts the genome to a list of dice, for easier rolling
	 * 
	 * @return
	 */
	public List<Die> getDice() {
		List<Die> dice = new ArrayList<Die>(NUM_DICE);
		for (int i = 0; i < NUM_DICE; i++) {
			int[] sides = new int[NUM_SIDES];
			for (int j = i * NUM_SIDES; j < (i + 1) * NUM_SIDES; j++) {
				sides[j % NUM_SIDES] = genome.get(j);
			}
			dice.add(new Die(sides));
		}
		return dice;
	}

	/**
	 * Returns the internal genome representation, as a list of integers
	 * 
	 * Violates the immuatability of the object - hence the deprecation
	 * 
	 * @return
	 */
	@Deprecated
	public List<Integer> getGenome() {
		return genome;
	}

	/**
	 * Mutate the genome by swapping at the two given indices.
	 * 
	 * @param firstIndex
	 * @param secondIndex
	 */
	public DiceGenome makeMutant(List<Pair<Integer>> swaps) {
		List<Integer> mutant = new ArrayList<Integer>(genome /* copy! */);
		for (Pair<Integer> pair : swaps) {
			int temp = mutant.get(pair.first);
			mutant.set(pair.first, mutant.get(pair.second));
			mutant.set(pair.second, temp);
		}
		return new DiceGenome(rand, new BirthCertificate<DiceGenome>(this, MUTANT), mutant);
	}

	/**
	 * Create new child that starts with this genome, up to the cut index, then copies everything else from
	 * the other genome. The permutation is still intact - no two numbers are repeated.
	 * @param other
	 * @param cut
	 * @return
	 */
	public DiceGenome crossOver(DiceGenome other, int cut) {
		List<Integer> child = new ArrayList<Integer>();
		for (int i = 0; i < cut; i++) {
			child.add(genome.get(i));
		}
		for (int j = 0; j < other.genome.size(); j++) {
			if (!child.contains(other.genome.get(j)/* mmm, tasty encapsulation violation */))
				child.add(other.genome.get(j));
		}
		return new DiceGenome(rand, new BirthCertificate<DiceGenome>(this, CROSSOVER), child);
	}

	/**
	 * Returns the fitness of the genome, according to the {@link IFitnessEvaluator} given.
	 * 
	 * Since the genome is immutable, this fitness gets computed once and then cached.
	 * 
	 * @return
	 */
	public Double getFitness() {
		if (fitness == null)
			fitness = evaluator.fitness(this);
		return fitness;
	}

	/**
	 * Returns a the birth certificate of this genome. You can traverse the entire history of the genome via
	 * the parent field.
	 * @return
	 */
	public BirthCertificate<DiceGenome> getBirthCertificate() {
		return birthCertificate;
	}

	@Override
	public int compareTo(DiceGenome o) {
		return -1 * getFitness().compareTo(o.getFitness()); // sort in reverse order - highest first
	}

	@Override
	public String toString() {
		return "fit=" + fitness + ";" + genome.toString();
	}

	/**
	 * Returns true if this genome is equivalent to another, ignoring order of integers within a single die
	 * 
	 * Constructs sets of dice and checks if the sets are equal to each other. Probably pretty slow.
	 * 
	 * @param other
	 * @return
	 */
	public boolean equivalent(DiceGenome other) {
		for (int die = 0; die < NUM_DICE; die++) {
			Set<Integer> mine = new HashSet<Integer>(), his = new HashSet<Integer>();
			for (int side = NUM_SIDES * die; side < NUM_SIDES * (die + 1); side++) {
				mine.add(genome.get(side));
				his.add(other.genome.get(side) /* mmm, tasty encapsulation breakage */);
			}
			if (!mine.equals(his))
				return false; // else keep going
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genome == null) ? 0 : genome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiceGenome other = (DiceGenome) obj;
		if (genome == null) {
			if (other.genome != null)
				return false;
		} else if (!equals(genome, other.genome))
			return false;
		return true;
	}

	private boolean equals(List<Integer> a, List<Integer> b) {
		if (a == b)
			return true;
		if (a == null || b == null || a.size() != b.size())
			return false;
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) != b.get(i))
				return false;
		}
		return true;
	}
}
