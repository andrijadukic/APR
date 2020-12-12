package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.operators.crossover.binary.BinarySinglePointCrossover;
import apr.genetics.operators.crossover.floatinpoint.SimpleArithmeticCrossover;
import apr.genetics.operators.mutation.binary.BinarySimpleMutation;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;

import static apr.genetics.demo.Testing.*;

public class Task2 {

    public static void main(String[] args) {
        int lb = -50;
        int ub = 150;

        var floatingPointGA = new EliminationGeneticAlgorithm(new SimpleArithmeticCrossover(), 0.8,
                new FloatingPointSimpleMutation(0.03, lb, ub), 1.,
                3);

        var binaryGA = new EliminationGeneticAlgorithm(new BinarySinglePointCrossover(), 1.,
                new BinarySimpleMutation(0.03), 1.,
                3);

        var stoppingCondition = StoppingConditions.maxIter(1000000);

        int[] dimensions = new int[]{1, 3, 6, 10};
        for (int dimension : dimensions) {
            System.out.println("Dimension: " + dimension);

            System.out.println("Function f6 floating point: ");
            run(floatingPointGA, 10, floatingPointPopulation(80, new Interval(lb, ub), dimension, FitnessFunctions.f6().negate()), stoppingCondition);
            System.out.println("Function f6 binary: ");
            run(binaryGA, 10, binaryPopulation(80, new Interval(lb, ub), 1e-6, dimension, FitnessFunctions.f6().negate()), stoppingCondition);

            System.out.println("Function f7 floating point: ");
            run(floatingPointGA, 10, floatingPointPopulation(80, new Interval(lb, ub), dimension, FitnessFunctions.f7().negate()), stoppingCondition);
            System.out.println("Function f7 binary: ");
            run(binaryGA, 10, binaryPopulation(80, new Interval(lb, ub), 1e-6, dimension, FitnessFunctions.f7().negate()), stoppingCondition);
        }
    }
}
