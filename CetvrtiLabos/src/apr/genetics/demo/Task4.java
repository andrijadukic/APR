package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.operators.crossover.floatinpoint.BLXAlphaCrossover;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;
import apr.util.Sampling;


import static apr.genetics.demo.Testing.*;

public class Task4 {

    public static void main(String[] args) {
        var interval = new Interval(-50, 150);
        int dimension = 6;

        var function = FitnessFunctions.f6().negate();
        var stoppingCondition = StoppingConditions.maxIter(100000);
        int runs = 10;

        var populationSizes = new int[]{30, 50, 100, 200};
        var populationMedians = new Double[populationSizes.length];
        for (int i = 0, n = populationSizes.length; i < n; i++) {
            int popSize = populationSizes[i];
            System.out.println("Population size: " + populationSizes[i]);
            var floatingPointGA = new EliminationGeneticAlgorithm(
                    new BLXAlphaCrossover(0.5, interval), 1.,
                    new FloatingPointSimpleMutation(0.1, interval.start(), interval.end()), 1.,
                    3
            );

            double median = run(floatingPointGA, runs, () -> floatingPointPopulation(popSize, interval, dimension, function), stoppingCondition);

            System.out.println("Median fitness: " + median);
            System.out.println();
            populationMedians[i] = median;
        }

        System.out.println();
        int optimalPopulation = populationSizes[Sampling.argMin(populationMedians)];
        System.out.println("Optimal population: " + optimalPopulation);

        System.out.println();

        var mutationProbabilities = new double[]{0.1, 0.3, 0.6, 0.9};
        var mutationMedians = new Double[mutationProbabilities.length];
        for (int i = 0, n = mutationProbabilities.length; i < n; i++) {
            System.out.println("Mutation probability: " + mutationProbabilities[i]);
            var floatingPointGA = new EliminationGeneticAlgorithm(
                    new BLXAlphaCrossover(0.5, interval), 1.,
                    new FloatingPointSimpleMutation(mutationProbabilities[i], interval.start(), interval.end()), 1.,
                    3
            );
            double median = run(floatingPointGA, runs, () -> floatingPointPopulation(optimalPopulation, interval, dimension, function), stoppingCondition);

            System.out.println("Median fitness: " + median);
            System.out.println();
            mutationMedians[i] = median;
        }

        System.out.println();
        double optimalMutationProbability = mutationProbabilities[Sampling.argMin(mutationMedians)];
        System.out.println("Optimal mutation probability: " + optimalMutationProbability);
    }
}
