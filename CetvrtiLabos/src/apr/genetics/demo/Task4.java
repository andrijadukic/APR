package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.chromosomes.Chromosome;
import apr.genetics.operators.crossover.floatinpoint.SimpleArithmeticCrossover;
import apr.genetics.operators.crossover.floatinpoint.SimulatedBinaryCrossover;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;

import java.util.ArrayList;
import java.util.List;

import static apr.genetics.demo.Testing.*;

public class Task4 {

    public static void main(String[] args) {
        var interval = new Interval(-50, 150);
        double pm = 0.45;
        int dim = 6;
        int popSize = 40;

        var function = FitnessFunctions.f6().negate();
        var stoppingCondition = StoppingConditions.maxIter(100000);
        var runs = 10;

        var populationSizes = new int[]{30, 50, 100, 200};
        for (var populationSize : populationSizes) {
            System.out.println("Population size: " + populationSize);
            List<Chromosome> fittestFloatingPoint = new ArrayList<>(runs);
            for (int i = 0; i < runs; i++) {
                var floatingPointGA = new EliminationGeneticAlgorithm(
                        new SimulatedBinaryCrossover(), 1.,
                        new FloatingPointSimpleMutation(pm, interval.start(), interval.end()), 1.,
                        3
                );

                fittestFloatingPoint.add(floatingPointGA.run(floatingPointPopulation(populationSize, interval, dim, function), stoppingCondition).getFittest());
            }
            System.out.println("Mean: " + fittestFloatingPoint.stream().mapToDouble(Chromosome::getFitness).average().orElseThrow(IllegalStateException::new));
        }

        System.out.println();

        var mutationProbabilities = new double[]{0.1, 0.3, 0.6, 0.9};
        for (var mutationProbability : mutationProbabilities) {
            System.out.println("Mutation probability: " + mutationProbability);
            List<Chromosome> fittestFloatingPoint = new ArrayList<>(runs);
            for (int i = 0; i < runs; i++) {
                var floatingPointGA = new EliminationGeneticAlgorithm(new SimpleArithmeticCrossover(), 1.,
                        new FloatingPointSimpleMutation(pm, interval.start(), interval.end()), 1.,
                        3);

                fittestFloatingPoint.add(floatingPointGA.run(floatingPointPopulation(popSize, interval, dim, function), stoppingCondition).getFittest());
            }
            System.out.println("Mean: " + fittestFloatingPoint.stream().mapToDouble(Chromosome::getFitness).average().orElseThrow(IllegalStateException::new));
        }
    }
}
