package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.operators.crossover.floatinpoint.BLXAlphaCrossover;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;

import static apr.genetics.demo.Testing.floatingPointPopulation;
import static apr.genetics.demo.Testing.run;

public class Task5 {

    public static void main(String[] args) {
        var interval = new Interval(-50, 150);
        double pm = 0.1;
        int popSize = 80;
        int dimension = 6;
        int runs = 10;

        var function = FitnessFunctions.f6().negate();
        var stoppingCondition = StoppingConditions.maxIter(100000);
        var tournamentSizes = new int[]{3, 5, 10, 30, 50, 80};
        for (var tournamentSize : tournamentSizes) {
            System.out.println("Tournament size: " + tournamentSize);
            var floatingPointGA = new EliminationGeneticAlgorithm(
                    new BLXAlphaCrossover(0.5, interval), 1.,
                    new FloatingPointSimpleMutation(pm, interval.start(), interval.end()), 1.,
                    tournamentSize
            );
            double median = run(floatingPointGA, runs, () -> floatingPointPopulation(popSize, interval, dimension, function), stoppingCondition);
            System.out.println("Median fitness: " + median);
            System.out.println();
        }
    }
}
