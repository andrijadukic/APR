package apr.genetics.demo;

import apr.genetics.chromosomes.floatingpoint.FloatingPointChromosome;
import apr.genetics.chromosomes.FieldChromosome;
import apr.util.Interval;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinimizationProblemFloatingPointChromosome extends FloatingPointChromosome {

    private final FitnessFunction function;

    public MinimizationProblemFloatingPointChromosome(List<Double> representation, FitnessFunction function) {
        super(representation);
        this.function = function;
    }

    public MinimizationProblemFloatingPointChromosome(Interval interval, int length, FitnessFunction function) {
        this(buildRepresentation(interval, length), function);
    }

    @Override
    protected double evaluate() {
        return function.valueAt(representation);
    }

    @Override
    public FieldChromosome<Double> newInstance(List<Double> representation) {
        return new MinimizationProblemFloatingPointChromosome(representation, function);
    }

    private static List<Double> buildRepresentation(Interval interval, int length) {
        double lb = interval.start();
        double ub = interval.end();
        Random random = ThreadLocalRandom.current();
        return Stream.generate(() -> lb + random.nextDouble() * (ub - lb))
                .limit(length)
                .collect(Collectors.toList());
    }
}
