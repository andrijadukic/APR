package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.algorithms.util.NthGenerationObserver;
import apr.genetics.algorithms.util.StandardOutputLogger;
import apr.genetics.chromosomes.population.ListBasedPopulation;
import apr.genetics.operators.crossover.floatinpoint.WholeArithmeticCrossover;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;


public class Lab {

    public static void main(String[] args) {
        var ga = new EliminationGeneticAlgorithm(new WholeArithmeticCrossover(), 1., new FloatingPointSimpleMutation(0.02, -50, 150), 1., 3);
        var observer = new NthGenerationObserver(new StandardOutputLogger(), 10000);
        var stoppingCondition = StoppingConditions.precision(0, 1e-4);

        ga.addObserver(observer);

        ga.run(
                new ListBasedPopulation(
                        () -> new MinimizationProblemFloatingPointChromosome(new Interval(-50, 150), 2, FitnessFunctions.f1().negate()),
                        30),
                stoppingCondition);
    }
}
