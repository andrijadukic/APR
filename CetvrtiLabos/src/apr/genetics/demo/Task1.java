package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.operators.crossover.binary.BinarySinglePointCrossover;
import apr.genetics.operators.crossover.floatinpoint.SimpleArithmeticCrossover;
import apr.genetics.operators.mutation.binary.BinarySimpleMutation;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;

import static apr.genetics.demo.Testing.*;

public class Task1 {

    public static void main(String[] args) {
        int lb = -50;
        int ub = 150;

        var floatingPointGA = new EliminationGeneticAlgorithm(new SimpleArithmeticCrossover(), 1.,
                new FloatingPointSimpleMutation(0.2, lb, ub), 1.,
                3);

        var binaryGA = new EliminationGeneticAlgorithm(new BinarySinglePointCrossover(), 1.,
                new BinarySimpleMutation(0.3), 1.,
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
}
