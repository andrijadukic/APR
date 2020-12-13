package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.operators.crossover.binary.BinarySinglePointCrossover;
import apr.genetics.operators.crossover.floatinpoint.BLXAlphaCrossover;
import apr.genetics.operators.mutation.binary.BinarySimpleMutation;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;


import static apr.genetics.demo.Testing.*;

public class Task2 {

    public static void main(String[] args) {
        var interval = new Interval(-50, 150);
        int popSize = 100;

        var floatingPointGA = new EliminationGeneticAlgorithm(
                new BLXAlphaCrossover(0.5, interval), 1.,
                new FloatingPointSimpleMutation(0.1, interval.start(), interval.end()), 1.,
                3
        );

        var binaryGA = new EliminationGeneticAlgorithm(
                new BinarySinglePointCrossover(), 1.,
                new BinarySimpleMutation(0.05), 1.,
                3
        );

        var stoppingCondition = StoppingConditions.maxIter(100000);

        int runs = 10;
        int[] dimensions = new int[]{1, 3, 6, 10};
        for (int dimension : dimensions) {
            System.out.println();
            System.out.println("Dimension: " + dimension);
            System.out.println();

            System.out.print("Function f6 floating point: ");
            run(floatingPointGA, runs, () -> floatingPointPopulation(popSize, interval, dimension, FitnessFunctions.f6().negate()), stoppingCondition);
            System.out.print("Function f6 binary: ");
            run(binaryGA, runs, () -> binaryPopulation(popSize, interval, 1e-6, dimension, FitnessFunctions.f6().negate()), stoppingCondition);

            System.out.println();

            System.out.print("Function f7 floating point: ");
            run(floatingPointGA, runs, () -> floatingPointPopulation(popSize, interval, dimension, FitnessFunctions.f7().negate()), stoppingCondition);
            System.out.print("Function f7 binary: ");
            run(binaryGA, runs, () -> binaryPopulation(popSize, interval, 1e-6, dimension, FitnessFunctions.f7().negate()), stoppingCondition);
        }
    }
}
