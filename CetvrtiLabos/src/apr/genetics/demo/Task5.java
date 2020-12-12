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

import static apr.genetics.demo.Testing.floatingPointPopulation;

public class Task5 {

    public static void main(String[] args) {
        var interval = new Interval(-50, 150);
        double pm = 0.45;
        int popSize = 80;
        int dimension = 6;
        int runs = 10;

        var function = FitnessFunctions.f6().negate();
        var stoppingCondition = StoppingConditions.maxIter(100000);
        var tournamentSizes = new int[]{3, 5, 10, 20, 30, 50, 80};
        for (var tournamentSize : tournamentSizes) {
            System.out.println("Tournament size: " + tournamentSize);
            List<Chromosome> fittestFloatingPoint = new ArrayList<>(runs);
            for (int i = 0; i < runs; i++) {
                var floatingPointGA = new EliminationGeneticAlgorithm(
                        new SimulatedBinaryCrossover(), 1.,
                        new FloatingPointSimpleMutation(pm, interval.start(), interval.end()), 1.,
                        3
                );

                fittestFloatingPoint.add(floatingPointGA.run(floatingPointPopulation(popSize, interval, dimension, function), stoppingCondition).getFittest());
            }
            System.out.println("Mean: " + fittestFloatingPoint.stream().mapToDouble(Chromosome::getFitness).average().orElseThrow(IllegalStateException::new));
        }
    }
}
