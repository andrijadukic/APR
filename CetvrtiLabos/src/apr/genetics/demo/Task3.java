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

public class Task3 {

    public static void main(String[] args) {
        var interval = new Interval(-50, 150);
        int popSize = 40;
        double precision = 1e-5;

        var floatingPointGA = new EliminationGeneticAlgorithm(new SimpleArithmeticCrossover(), 1.,
                new FloatingPointSimpleMutation(0.1, interval.start(), interval.end()), 1.,
                3);

        var binaryGA = new EliminationGeneticAlgorithm(new BinarySinglePointCrossover(), 1.,
                new BinarySimpleMutation(0.1), 1.,
                3);

        StoppingCondition stoppingCondition = StoppingConditions.maxIter(10000000);

        System.out.print("Function f6 floating point 3 dim: ");
        run(floatingPointGA, 10, floatingPointPopulation(popSize, interval, 3, FitnessFunctions.f6().negate()), stoppingCondition);
        System.out.print("Function f6 binary 3 dim:         ");
        run(binaryGA, 10, binaryPopulation(popSize, interval, precision, 3, FitnessFunctions.f6().negate()), stoppingCondition);

        System.out.println();

        System.out.print("Function f6 floating point 6 dim: ");
        run(floatingPointGA, 10, floatingPointPopulation(popSize, interval, 6, FitnessFunctions.f6().negate()), stoppingCondition);
        System.out.print("Function f6 binary 6 dim:         ");
        run(binaryGA, 10, binaryPopulation(popSize, interval, precision, 6, FitnessFunctions.f6().negate()), stoppingCondition);

        System.out.println();

        System.out.print("Function f7 floating point 3 dim: ");
        run(floatingPointGA, 10, floatingPointPopulation(80, interval, 3, FitnessFunctions.f7().negate()), stoppingCondition);
        System.out.print("Function f7 binary 3 dim:         ");
        run(binaryGA, 10, binaryPopulation(popSize, interval, precision, 3, FitnessFunctions.f7().negate()), stoppingCondition);

        System.out.println();

        System.out.print("Function f7 floating point 6 dim: ");
        run(floatingPointGA, 10, floatingPointPopulation(popSize, interval, 6, FitnessFunctions.f7().negate()), stoppingCondition);
        System.out.print("Function f7 binary 6 dim:         ");
        run(binaryGA, 10, binaryPopulation(popSize, interval, precision, 6, FitnessFunctions.f7().negate()), stoppingCondition);
    }
}
