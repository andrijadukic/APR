package apr.genetics.demo;

import apr.genetics.algorithms.GeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.algorithms.util.NthGenerationObserver;
import apr.genetics.algorithms.util.StandardOutputLogger;
import apr.genetics.chromosomes.binary.Decoders;
import apr.genetics.chromosomes.population.ListBasedPopulation;
import apr.genetics.chromosomes.population.Population;
import apr.util.Interval;

public class Testing {

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

    public static void test(GeneticAlgorithm geneticAlgorithm, Population population) {
        var observer = new NthGenerationObserver(new StandardOutputLogger(), 100000);
        geneticAlgorithm.addObserver(observer);

        var stoppingCondition = StoppingConditions.maxIter(1000000).or(StoppingConditions.precision(0, 1e-5));

        var fittest = geneticAlgorithm.run(population, stoppingCondition).getFittest();

        System.out.println("Best found: " + fittest + " | fitness: " + fittest.getFitness());
    }
}
