package apr.genetics.demo;

import apr.functions.IMultivariateFunction;
import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.algorithms.util.NthGenerationObserver;
import apr.genetics.algorithms.util.StandardOutputLogger;
import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.population.ListBasedPopulation;
import apr.genetics.operators.crossover.floatinpoint.SimpleArithmeticCrossover;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lab {

    public static void main(String[] args) {
        var ga = new EliminationGeneticAlgorithm(new SimpleArithmeticCrossover(), 1., new FloatingPointSimpleMutation(0.02, -50, 150), 1., 3);
        var observer = new NthGenerationObserver(new StandardOutputLogger(), 10000);
        var stoppingCondition = StoppingConditions.maxIter(1000000);

        ga.addObserver(observer);

        ga.run(new ListBasedPopulation(30, () -> floatingPointChromosomeInstance(-50, 150, 2, FitnessFunctions.f1().negate())), stoppingCondition);
    }

    private static Chromosome floatingPointChromosomeInstance(double lb, double ub, int length, IMultivariateFunction fitnessFunction) {
        Random random = ThreadLocalRandom.current();
        return new MinimizationProblemFloatingPointChromosome(
                Stream.generate(() -> lb + random.nextDouble() * (ub - lb))
                        .limit(length)
                        .collect(Collectors.toList()),
                fitnessFunction);
    }
}
