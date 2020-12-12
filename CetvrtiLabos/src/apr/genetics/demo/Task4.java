package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.operators.crossover.floatinpoint.SimpleArithmeticCrossover;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;

import static apr.genetics.demo.Testing.floatingPointPopulation;
import static apr.genetics.demo.Testing.run;

public class Task4 {

    public static void main(String[] args) {
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
}
