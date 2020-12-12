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
//        zadatak1();
        zadatak2();
//        zadatak3();
//        zadatak4();
//        zadatak5();
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

    private static void zadatak4() {
        int lb = -50;
        int ub = 150;

        var populationSizes = new int[]{30, 50, 100, 200};
        var mutationProbabilities = new double[]{0.1, 0.3, 0.6, 0.9};
        var function = FitnessFunctions.f6().negate();
        var stoppingCondition = StoppingConditions.maxIter(100000);

        var ga = new EliminationGeneticAlgorithm(new SimpleArithmeticCrossover(), 0.8,
                new FloatingPointSimpleMutation(0.02, lb, ub), 1.,
                3);

        for (var populationSize : populationSizes) {
            System.out.println("Population size: " + populationSize);
            run(ga, 10, floatingPointPopulation(populationSize, new Interval(lb, ub), 6, function), stoppingCondition);
        }

        var optimalPopulation = 80;

        for (var mutationProbability : mutationProbabilities) {
            System.out.println("Mutation probability: " + mutationProbability);
            run(
                    new EliminationGeneticAlgorithm(
                            new SimpleArithmeticCrossover(), 0.8,
                            new FloatingPointSimpleMutation(mutationProbability, lb, ub), 1.,
                            3),
                    10,
                    floatingPointPopulation(optimalPopulation, new Interval(lb, ub), 6, function),
                    stoppingCondition
            );
        }
    }

    private static void zadatak5() {
        int lb = -50;
        int ub = 150;

        var function = FitnessFunctions.f6().negate();
        var stoppingCondition = StoppingConditions.maxIter(1000000);
        var tournamentSizes = new int[]{3, 5, 10, 20, 30, 50, 80};
        for (var tournamentSize : tournamentSizes) {
            System.out.println("Tournament size: " + tournamentSize);
            run(
                    new EliminationGeneticAlgorithm(
                            new SimpleArithmeticCrossover(), 0.8,
                            new FloatingPointSimpleMutation(0.03, lb, ub), 1.,
                            tournamentSize),
                    30,
                    floatingPointPopulation(80, new Interval(lb, ub), 6, function),
                    stoppingCondition
            );
        }
    }
}
