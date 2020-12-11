package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.operators.crossover.binary.BinarySinglePointCrossover;
import apr.genetics.operators.mutation.binary.BinarySimpleMutation;
import apr.util.Interval;

import static apr.genetics.demo.Testing.*;


public class Lab {

    public static void main(String[] args) {
        zadatak1();
    }

    private static void zadatak1() {
        int lb = -50;
        int ub = 150;
        int dimension = 2;
        var function = FitnessFunctions.f1().negate();

//        var floatingPointGA = new EliminationGeneticAlgorithm(new SimpleArithmeticCrossover(), 0.8,
//                new FloatingPointSimpleMutation(0.02, lb, ub), 1.,
//                3);
//
//        test(floatingPointGA, floatingPointPopulation(80, new Interval(lb, ub), dimension, function));

        var binaryGA = new EliminationGeneticAlgorithm(new BinarySinglePointCrossover(), 0.8,
                new BinarySimpleMutation(0.03), 1.,
                3);

        test(binaryGA, binaryPopulation(80, new Interval(lb, ub), 50, dimension, function));
    }
}
