package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.operators.crossover.binary.BinarySinglePointCrossover;
import apr.genetics.operators.crossover.floatinpoint.BLXAlphaCrossover;
import apr.genetics.operators.mutation.binary.BinarySimpleMutation;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;

import static apr.genetics.demo.Testing.*;

public class Task3 {

    public static void main(String[] args) {
        var interval = new Interval(-50, 150);

        var dimensions = new int[]{3, 6};
        var functions = new FitnessFunction[]{FitnessFunctions.f6().negate(), FitnessFunctions.f7().negate()};
        var stoppingCondition = StoppingConditions.maxIter(1000000);
        int runs = 10;

        for (var dimension : dimensions) {
            for (var function : functions) {
                var floatingPointGA = new EliminationGeneticAlgorithm(
                        new BLXAlphaCrossover(0.5, interval), 1.,
                        new FloatingPointSimpleMutation(0.1, interval.start(), interval.end()), 1.,
                        3
                );

                var binaryGA = new EliminationGeneticAlgorithm(
                        new BinarySinglePointCrossover(), 1.,
                        new BinarySimpleMutation(0.1), 1.,
                        3
                );

                run(floatingPointGA, runs, () -> floatingPointPopulation(90, interval, dimension, function), stoppingCondition);
//                run(binaryGA, runs, () -> binaryPopulation(90, interval, 1e-4, dimension, function), stoppingCondition);

                System.out.println();
            }
        }
    }
}
