package apr.genetics.demo;

import apr.genetics.algorithms.EliminationGeneticAlgorithm;
import apr.genetics.algorithms.conditions.StoppingCondition;
import apr.genetics.algorithms.conditions.StoppingConditions;
import apr.genetics.chromosomes.Chromosome;
import apr.genetics.operators.crossover.binary.BinarySinglePointCrossover;
import apr.genetics.operators.crossover.floatinpoint.SimpleArithmeticCrossover;
import apr.genetics.operators.crossover.floatinpoint.SimulatedBinaryCrossover;
import apr.genetics.operators.mutation.binary.BinarySimpleMutation;
import apr.genetics.operators.mutation.floatingpoint.FloatingPointSimpleMutation;
import apr.util.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static apr.genetics.demo.Testing.*;

public class Task3 {

    public static void main(String[] args) {
        var interval = new Interval(-50, 150);
        int popSize = 80;
        double precision = 1e-5;
        double pm = 0.45;

        var dimensions = new int[]{3, 6};
        var functions = new FitnessFunction[]{FitnessFunctions.f6().negate(), FitnessFunctions.f7().negate()};
        var stoppingCondition = StoppingConditions.maxIter(100000);
        int runs = 10;

        for (var dim : dimensions) {
            for (var function : functions) {
                List<Chromosome> fittestFloatingPoint = new ArrayList<>(runs);
                List<Chromosome> fittestBinary = new ArrayList<>(runs);
                for (int i = 0; i < runs; i++) {
                    var floatingPointGA = new EliminationGeneticAlgorithm(
                            new SimulatedBinaryCrossover(), 1.,
                            new FloatingPointSimpleMutation(pm, interval.start(), interval.end()), 1.,
                            3);

                    var binaryGA = new EliminationGeneticAlgorithm(
                            new BinarySinglePointCrossover(), 1.,
                            new BinarySimpleMutation(pm), 1.,
                            3);

                    fittestFloatingPoint.add(floatingPointGA.run(floatingPointPopulation(popSize, interval, dim, function), stoppingCondition).getFittest());
                    fittestBinary.add(binaryGA.run(binaryPopulation(popSize, interval, precision, dim, function), stoppingCondition).getFittest());
                }
                List<Double> fitnessFloatingPoint = fittestFloatingPoint.stream().map(Chromosome::getFitness).collect(Collectors.toList());
                System.out.println(fitnessFloatingPoint.stream().map(String::valueOf).collect(Collectors.joining(", ")));
                List<Double> fitnessBinary = fittestBinary.stream().map(Chromosome::getFitness).collect(Collectors.toList());
                System.out.println(fitnessBinary.stream().map(String::valueOf).collect(Collectors.joining(", ")));

                System.out.println();
            }
        }
    }
}
