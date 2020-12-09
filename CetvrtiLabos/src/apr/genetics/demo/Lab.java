package apr.genetics.demo;

import apr.functions.MultivariateFunction;
import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.GeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.algorithms.util.NthGenerationObserver;
import apr.genetics.algorithms.util.StandardOutputLogger;
import apr.genetics.chromosomes.binary.BinaryChromosome;
import apr.genetics.chromosomes.binary.Decoders;
import apr.genetics.chromosomes.population.ListBasedPopulation;
import apr.genetics.chromosomes.population.Population;
import apr.genetics.operators.crossover.binary.BinarySinglePointCrossover;
import apr.genetics.operators.crossover.binary.BinaryUniformCrossover;
import apr.genetics.operators.crossover.floatinpoint.SimpleArithmeticCrossover;
import apr.genetics.operators.crossover.floatinpoint.WholeArithmeticCrossover;
import apr.genetics.operators.mutation.binary.BinarySimpleMutation;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;


public class Lab {

    public static void main(String[] args) {
        zadatak1();
    }

    private static void zadatak1() {
        int lb = -50;
        int ub = 150;
        int dimension = 2;
        var function = FitnessFunctions.f1().negate();

//        var floatingPointGA = new EliminationGeneticAlgorithm(new SimpleArithmeticCrossover(), 1.,
//                new FloatingPointSimpleMutation(0.02, lb, ub), 1.,
//                3);
//
//        test(floatingPointGA, floatingPointPopulation(100, new Interval(lb, ub), dimension, function));

        var binaryGA = new EliminationGeneticAlgorithm(new BinaryUniformCrossover(), 1.,
                new BinarySimpleMutation(0.02), 1.,
                3);

        test(binaryGA, binaryPopulation(100, new Interval(lb, ub), 8, dimension, function));
    }

    public static Population floatingPointPopulation(int populationSize, Interval interval, int dimension, MultivariateFunction fitnessFunction) {
        return new ListBasedPopulation(() -> new MinimizationProblemFloatingPointChromosome(interval, dimension, fitnessFunction), populationSize);
    }

    public static Population binaryPopulation(int populationSize, Interval interval, int numberOfBits, int dimension, MultivariateFunction fitnessFunction) {
        var decoder = Decoders.binary(interval.start(), interval.end(), numberOfBits);
        return new ListBasedPopulation(() -> new MinimizationProblemBinaryChromosome(decoder, dimension, fitnessFunction), populationSize);
    }

    public static Population binaryPopulation(int populationSize, Interval interval, double precision, int dimension, MultivariateFunction fitnessFunction) {
        var decoder = Decoders.binary(interval.start(), interval.end(), precision);
        return new ListBasedPopulation(() -> new MinimizationProblemBinaryChromosome(decoder, dimension, fitnessFunction), populationSize);
    }

    public static void test(GeneticAlgorithm geneticAlgorithm, Population population) {
        var observer = new NthGenerationObserver(new StandardOutputLogger(), 10000);
        geneticAlgorithm.addObserver(observer);

        var stoppingCondition = StoppingConditions.precision(0, 1e-4);

        geneticAlgorithm.run(population, stoppingCondition);
    }
}
