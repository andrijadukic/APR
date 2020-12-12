package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingCondition;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.operators.crossover.binary.BinarySinglePointCrossover;
import apr.genetics.operators.crossover.floatinpoint.SimpleArithmeticCrossover;
import apr.genetics.operators.mutation.binary.BinarySimpleMutation;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;

import static apr.genetics.demo.Testing.*;


public class Lab {

    public static void main(String[] args) {
        zadatak1();
        zadatak2();
        zadatak3();
    }

    private static void zadatak1() {
        int lb = -50;
        int ub = 150;

        var floatingPointGA = new EliminationGeneticAlgorithm(new SimpleArithmeticCrossover(), 0.8,
                new FloatingPointSimpleMutation(0.02, lb, ub), 1.,
                3);

        var binaryGA = new EliminationGeneticAlgorithm(new BinarySinglePointCrossover(), 1.,
                new BinarySimpleMutation(0.03), 1.,
                3);

        System.out.println("Function f1 floating point: ");
        test(floatingPointGA, floatingPointPopulation(80, new Interval(lb, ub), 2, FitnessFunctions.f1().negate()));
        System.out.println("Function f1 binary: ");
        test(binaryGA, binaryPopulation(80, new Interval(lb, ub), 1e-6, 2, FitnessFunctions.f1().negate()));

        System.out.println("Function f3 floating point: ");
        test(floatingPointGA, floatingPointPopulation(80, new Interval(lb, ub), 5, FitnessFunctions.f3().negate()));
        System.out.println("Function f3 binary: ");
        test(binaryGA, binaryPopulation(80, new Interval(lb, ub), 1e-6, 5, FitnessFunctions.f3().negate()));

        System.out.println("Function f6 floating point: ");
        test(floatingPointGA, floatingPointPopulation(80, new Interval(lb, ub), 2, FitnessFunctions.f6().negate()));
        System.out.println("Function f6 binary: ");
        test(binaryGA, binaryPopulation(80, new Interval(lb, ub), 1e-6, 2, FitnessFunctions.f6().negate()));

        System.out.println("Function f7 floating point: ");
        test(floatingPointGA, floatingPointPopulation(80, new Interval(lb, ub), 2, FitnessFunctions.f7().negate()));
        System.out.println("Function f7 binary: ");
        test(binaryGA, binaryPopulation(80, new Interval(lb, ub), 1e-6, 2, FitnessFunctions.f7().negate()));
    }

    private static void zadatak2() {
        int lb = -50;
        int ub = 150;

        var floatingPointGA = new EliminationGeneticAlgorithm(new SimpleArithmeticCrossover(), 0.8,
                new FloatingPointSimpleMutation(0.02, lb, ub), 1.,
                3);

        var binaryGA = new EliminationGeneticAlgorithm(new BinarySinglePointCrossover(), 1.,
                new BinarySimpleMutation(0.03), 1.,
                3);

        int[] dimensions = new int[]{1, 3, 6, 10};
        for (int dimension : dimensions) {

            System.out.println("Function f6 floating point: ");
            test(floatingPointGA, floatingPointPopulation(80, new Interval(lb, ub), dimension, FitnessFunctions.f6().negate()));
            System.out.println("Function f6 binary: ");
            test(binaryGA, binaryPopulation(80, new Interval(lb, ub), 1e-6, dimension, FitnessFunctions.f6().negate()));

            System.out.println("Function f7 floating point: ");
            test(floatingPointGA, floatingPointPopulation(80, new Interval(lb, ub), dimension, FitnessFunctions.f7().negate()));
            System.out.println("Function f7 binary: ");
            test(binaryGA, binaryPopulation(80, new Interval(lb, ub), 1e-6, dimension, FitnessFunctions.f7().negate()));
        }
    }

    private static void zadatak3() {
        int lb = -50;
        int ub = 150;

        var floatingPointGA = new EliminationGeneticAlgorithm(new SimpleArithmeticCrossover(), 0.8,
                new FloatingPointSimpleMutation(0.02, lb, ub), 1.,
                3);

        var binaryGA = new EliminationGeneticAlgorithm(new BinarySinglePointCrossover(), 1.,
                new BinarySimpleMutation(0.03), 1.,
                3);

        StoppingCondition stoppingCondition = StoppingConditions.maxIter(100000);

        System.out.println("Function f6 floating point 3 dim: ");
        run(floatingPointGA, 10, floatingPointPopulation(80, new Interval(lb, ub), 3, FitnessFunctions.f6().negate()), stoppingCondition);
        System.out.println("Function f6 binary 3 dim: ");
        run(binaryGA, 10, binaryPopulation(80, new Interval(lb, ub), 1e-4, 3, FitnessFunctions.f6().negate()), stoppingCondition);

        System.out.println("Function f6 floating point 6 dim: ");
        run(floatingPointGA, 10, floatingPointPopulation(80, new Interval(lb, ub), 6, FitnessFunctions.f6().negate()), stoppingCondition);
        System.out.println("Function f6 binary 6 dim: ");
        run(binaryGA, 10, binaryPopulation(80, new Interval(lb, ub), 1e-4, 6, FitnessFunctions.f6().negate()), stoppingCondition);

        System.out.println("Function f7 floating point 3 dim: ");
        run(floatingPointGA, 10, floatingPointPopulation(80, new Interval(lb, ub), 3, FitnessFunctions.f7().negate()), stoppingCondition);
        System.out.println("Function f7 binary 3 dim: ");
        run(binaryGA, 10, binaryPopulation(80, new Interval(lb, ub), 1e-4, 3, FitnessFunctions.f7().negate()), stoppingCondition);

        System.out.println("Function f7 floating point 6 dim: ");
        run(floatingPointGA, 10, floatingPointPopulation(80, new Interval(lb, ub), 6, FitnessFunctions.f7().negate()), stoppingCondition);
        System.out.println("Function f7 binary 6 dim: ");
        run(binaryGA, 10, binaryPopulation(80, new Interval(lb, ub), 1e-4, 6, FitnessFunctions.f7().negate()), stoppingCondition);
    }
}
