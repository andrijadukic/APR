package apr.genetics.demo;

import apr.genetics.algorithms.GeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingCondition;
import apr.genetics.algorithms.util.NthGenerationObserver;
import apr.genetics.algorithms.util.StandardOutputLogger;
import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.binary.Decoders;
import apr.genetics.chromosomes.population.ListBasedPopulation;
import apr.genetics.chromosomes.population.Population;
import apr.util.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Testing {

    public static double run(GeneticAlgorithm geneticAlgorithm, int runs, Supplier<Population> populationSupplier, StoppingCondition stoppingCondition) {
        List<Chromosome> fittest = new ArrayList<>(runs);
        for (int i = 0; i < runs; i++) {
            fittest.add(geneticAlgorithm.run(populationSupplier.get(), stoppingCondition).getFittest());
        }
        System.out.println(fittest.stream().map(c -> String.valueOf(Math.abs(c.getFitness()))).collect(Collectors.joining(", ")));

        var sortedFitness = fittest.stream().mapToDouble(c -> Math.abs(c.getFitness())).sorted().toArray();
        if (runs % 2 == 1) return sortedFitness[runs / 2];
        return (sortedFitness[runs / 2] + sortedFitness[runs / 2 - 1]) / 2;
    }

    public static Population floatingPointPopulation(int populationSize, Interval interval, int dimension, FitnessFunction fitnessFunction) {
        return new ListBasedPopulation(() -> new MinimizationProblemFloatingPointChromosome(interval, dimension, fitnessFunction), populationSize);
    }

    public static Population binaryPopulation(int populationSize, Interval interval, int numberOfBits, int dimension, FitnessFunction fitnessFunction) {
        var decoder = Decoders.binary(interval.start(), interval.end(), numberOfBits);
        return new ListBasedPopulation(() -> new MinimizationProblemBinaryChromosome(decoder, dimension, fitnessFunction), populationSize);
    }

    public static Population binaryPopulation(int populationSize, Interval interval, double precision, int dimension, FitnessFunction fitnessFunction) {
        var decoder = Decoders.binary(interval.start(), interval.end(), precision);
        return new ListBasedPopulation(() -> new MinimizationProblemBinaryChromosome(decoder, dimension, fitnessFunction), populationSize);
    }

    public static void test(GeneticAlgorithm geneticAlgorithm, Population population, StoppingCondition stoppingCondition, boolean verbose) {
        if (verbose) {
            geneticAlgorithm.addObserver(new NthGenerationObserver(new StandardOutputLogger(), 100000));
        }

        var fittest = geneticAlgorithm.run(population, stoppingCondition).getFittest();

        System.out.println("Best found: " + fittest + " | fitness: " + Math.abs(fittest.getFitness()));
    }
}
