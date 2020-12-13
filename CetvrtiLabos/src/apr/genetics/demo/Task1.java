package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.operators.crossover.binary.BinarySinglePointCrossover;
import apr.genetics.operators.crossover.binary.BinaryUniformCrossover;
import apr.genetics.operators.crossover.floatinpoint.SimpleArithmeticCrossover;
import apr.genetics.operators.crossover.floatinpoint.SimulatedBinaryCrossover;
import apr.genetics.operators.mutation.binary.BinarySimpleMutation;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;

import static apr.genetics.demo.Testing.*;

public class Task1 {

    public static void main(String[] args) {
        var interval = new Interval(-50, 150);
        int popSize = 80;

        var floatingPointGA = new EliminationGeneticAlgorithm(
                new SimulatedBinaryCrossover(), 1.,
                new FloatingPointSimpleMutation(0.45, interval.start(), interval.end()), 1.,
                3
        );

        var binaryGA = new EliminationGeneticAlgorithm(
                new BinarySinglePointCrossover(), 1.,
                new BinarySimpleMutation(0.2), 1.,
                3
        );

        var stoppingCondition = StoppingConditions.maxIter(100000).or(StoppingConditions.precision(0, 1e-6));

//        System.out.println("Function f1 floating point: ");
//        test(
//                floatingPointGA,
//                floatingPointPopulation(popSize, interval, 2, FitnessFunctions.f1().negate()),
//                stoppingCondition,
//                false
//        );
        System.out.println("Function f1 binary: ");
        test(
                binaryGA,
                binaryPopulation(80, interval, 1e-4, 2, FitnessFunctions.f1().negate()),
                stoppingCondition,
                false
        );
//
//        System.out.println("Function f3 floating point: ");
//        test(
//                floatingPointGA,
//                floatingPointPopulation(popSize, interval, 5, FitnessFunctions.f3().negate()),
//                stoppingCondition,
//                false
//        );
//        System.out.println("Function f3 binary: ");
//        test(binaryGA, binaryPopulation(80, new Interval(lb, ub), 1e-6, 5, FitnessFunctions.f3().negate()));

//        System.out.println("Function f6 floating point: ");
//        test(
//                floatingPointGA,
//                floatingPointPopulation(popSize, interval, 6, FitnessFunctions.f6().negate()),
//                stoppingCondition,
//                false
//        );
//        System.out.println("Function f6 binary: ");
//        test(binaryGA, binaryPopulation(80, new Interval(lb, ub), 1e-6, 2, FitnessFunctions.f6().negate()));
//
//        System.out.println("Function f7 floating point: ");
//        test(
//                floatingPointGA,
//                floatingPointPopulation(popSize, interval, 6, FitnessFunctions.f7().negate()),
//                stoppingCondition,
//                false
//        );
//        System.out.println("Function f7 binary: ");
//        test(binaryGA, binaryPopulation(80, new Interval(lb, ub), 1e-6, 2, FitnessFunctions.f7().negate()));
    }
}
