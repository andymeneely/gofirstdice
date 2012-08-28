package org.chaoticbits.gofirst.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.log4j.PropertyConfigurator;
import org.uncommons.maths.random.MersenneTwisterRNG;

public class RunGA {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RunGA.class);
	private static final MersenneTwisterRNG rand = new MersenneTwisterRNG();
	private static final int NUM_FITNESS_TRIALS = 1000;
	public static final int POPULATION_SIZE = 1000;
	public static final int NUM_GENERATIONS = 10;
	public static final int MUTATION_SWAPS = 1;
	public static final int NUM_MUTATIONS_PER_GEN = 500;
	public static final int NUM_IMMIGRANTS_PER_GEN = 200;
	public static final int NUM_CROSSOVER_PER_GEN = 200;
	private static final SimulationEvaluator evaluator = new SimulationEvaluator(rand, NUM_FITNESS_TRIALS);

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		log.info("===First population===");
		List<DiceGenome> population = runAlg(init(rand));
		log.info("===Second population===");
		population.addAll(runAlg(init(rand)));
		log.info("===Third population===");
		population.addAll(runAlg(init(rand)));
		log.info("===Evolving Main Population===");
		runAlg(population);
		log.info("Done.");
	}

	private static List<DiceGenome> runAlg(List<DiceGenome> population) {
		for (int gen = 0; gen < NUM_GENERATIONS; gen++) {
			mutate(population);
			immigrate(population);
			crossover(population);
			cull(population);
			report(gen, population);
		}
		return population;
	}

	private static void mutate(List<DiceGenome> population) {
		log.info("Mutating...");
		SwapMutator mutator = new SwapMutator(rand);
		Collections.shuffle(population, rand); // mutations are completely random - no fitness bias
		for (int i = 0; i < NUM_MUTATIONS_PER_GEN; i++) { // nobody gets mutated more than once
			population.add(population.get(i).makeMutant(
					mutator.mutate(DiceGenome.SIZE, DiceGenome.NUM_DICE, MUTATION_SWAPS)));
		}
		Collections.sort(population); // sort by highest fitness
	}

	private static void immigrate(List<DiceGenome> population) {
		log.info("Immigrating...");
		for (int i = 0; i < NUM_IMMIGRANTS_PER_GEN; i++) {
			population.add(new DiceGenome(rand, evaluator));
		}
		Collections.sort(population); // sort by highest fitness
	}

	private static void crossover(List<DiceGenome> population) {
		log.info("Crossing over...");
		for (int i = 0; i < NUM_CROSSOVER_PER_GEN; i++) {
			int cut = rand.nextInt(DiceGenome.SIZE);
			DiceGenome first = population.get(rand.nextInt(NUM_CROSSOVER_PER_GEN));
			DiceGenome second = population.get(rand.nextInt(NUM_CROSSOVER_PER_GEN));
			population.add(first.crossOver(second, cut));
			population.add(second.crossOver(first, cut));
		}
		Collections.sort(population); // sort by highest fitness
	}

	private static void cull(List<DiceGenome> oldPop) {
		log.info("Culling...");
		Collections.sort(oldPop); // sort by highest fitness
		List<DiceGenome> newPop = new ArrayList<DiceGenome>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			if (i > 0 && !oldPop.get(i).equivalent(oldPop.get(i - 1)))
				newPop.add(oldPop.get(i));
		}
	}

	private static void report(int gen, List<DiceGenome> sortedPop) {
		log.info("Reporting...");
		System.out.println("gen=" + gen + ", highest: " + sortedPop.get(0));
		System.out.println("gen=" + gen + ", median: " + sortedPop.get(500));
	}

	private static List<DiceGenome> init(Random rand) {
		log.info("Initializing population...");
		List<DiceGenome> population = new ArrayList<DiceGenome>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			population.add(new DiceGenome(rand, evaluator));
		}
		Collections.sort(population); // sort by highest fitness
		return population;
	}
}
