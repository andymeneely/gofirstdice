package org.chaoticbits.gofirst.genetic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.PropertyConfigurator;
import org.chaoticbits.gofirst.genetic.algorithm.BirthCertificate;
import org.chaoticbits.gofirst.genetic.algorithm.BirthCertificate.Type;
import org.chaoticbits.gofirst.genetic.algorithm.GaussianRNGTransformer;
import org.chaoticbits.gofirst.genetic.algorithm.SwapMutator;
import org.uncommons.maths.random.MersenneTwisterRNG;

public class RunGA {

	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RunGA.class);
	private static final MersenneTwisterRNG rand = new MersenneTwisterRNG();
	private static final int NUM_FITNESS_TRIALS = 1000;
	public static final int POPULATION_SIZE = 1000;
	public static final int NUM_GENERATIONS = 100;
	public static final int MUTATION_SWAPS = 1;
	public static final int NUM_MUTANTS_PER_GEN = 500;
	public static final int NUM_IMMIGRANTS_PER_GEN = 300;
	public static final int NUM_CROSSOVER_PER_GEN = 300;
	private static final SimulationEvaluator evaluator = new SimulationEvaluator(rand, NUM_FITNESS_TRIALS);

	public static void main(String[] args) {
		System.setProperty("current.timestamp", new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.S").format(new Date()));
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
		for (int i = 0; i < NUM_MUTANTS_PER_GEN; i++) { // nobody gets mutated more than once
			population.add(population.get(i).makeMutant(
					mutator.mutate(DiceGenome.SIZE, DiceGenome.NUM_DICE, MUTATION_SWAPS)));
		}
	}

	private static void immigrate(List<DiceGenome> population) {
		log.info("Immigrating...");
		for (int i = 0; i < NUM_IMMIGRANTS_PER_GEN; i++) {
			population.add(new DiceGenome(rand, evaluator, new BirthCertificate<DiceGenome>(Type.IMMIGRANT, (new DiceGenome[]{}))));
		}
	}

	private static void crossover(List<DiceGenome> population) {
		log.info("Sorting...");
		Collections.sort(population); // sort by highest fitness
		log.info("Crossing over...");
		GaussianRNGTransformer rng = new GaussianRNGTransformer(rand);
		for (int i = 0; i < NUM_CROSSOVER_PER_GEN; i++) {
			int cut = rand.nextInt(DiceGenome.SIZE);
			int firstIndex = rng.nextInt(POPULATION_SIZE);
			int secondIndex = rng.nextInt(POPULATION_SIZE, firstIndex /* no self-crossovers */);
			DiceGenome first = population.get(firstIndex);
			DiceGenome second = population.get(secondIndex);
			population.add(first.crossOver(second, cut));
			population.add(second.crossOver(first, cut));
		}
	}

	private static void cull(List<DiceGenome> oldPop) {
		log.info("Sorting...");
		Collections.sort(oldPop); // sort by highest fitness
		log.info("Culling...");
		List<DiceGenome> newPop = new ArrayList<DiceGenome>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			if (i > 0 && !oldPop.get(i).equivalent(oldPop.get(i - 1)))
				newPop.add(oldPop.get(i));
		}
	}

	private static void report(int gen, List<DiceGenome> sortedPop) {
		log.info("Reporting...");
		DiceGenome best = sortedPop.get(0);
		System.out.println("gen=" + gen + ", highest: " + best);
		outputHistory(best);
		System.out.println("gen=" + gen + ", median: " + sortedPop.get(500));
	}

	private static void outputHistory(DiceGenome descendant) {
		System.out.println("History of the highest (BFS-style)...");
		List<DiceGenome> queue = new LinkedList<DiceGenome>();
		queue.add(descendant);
		while (!queue.isEmpty()) {
			DiceGenome current = queue.remove(0);
			System.out.println("\t" + current.getBirthCertificate().getType());
			if (current.getBirthCertificate().getParents() != null) {
				for (DiceGenome parent : current.getBirthCertificate().getParents()) {
					queue.add(parent);
				}
			}
		}
	}

	private static List<DiceGenome> init(Random rand) {
		log.info("Initializing population...");
		List<DiceGenome> population = new ArrayList<DiceGenome>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			population.add(new DiceGenome(rand, evaluator));
		}
		return population;
	}
}
