package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.operators.crossover.floatinpoint.SimpleArithmeticCrossover;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;

import static apr.genetics.demo.Testing.floatingPointPopulation;
import static apr.genetics.demo.Testing.run;

public class Task5 {

    public static void main(String[] args) {
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
