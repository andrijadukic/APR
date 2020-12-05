package apr.genetics.operators.crossover.floatinpoint;

import apr.genetics.chromosomes.floatingpoint.FloatingPointChromosome;
import apr.genetics.chromosomes.util.ChromosomePair;
import apr.genetics.exceptions.InsufficientChromosomeLength;
import apr.util.Pair;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FloatingPointMultiplePointCrossover extends AbstractFloatingPointCrossover {

    private final int crossovers;

    public FloatingPointMultiplePointCrossover(int crossovers) {
        if (crossovers <= 0) throw new InvalidParameterException();
        this.crossovers = crossovers;
    }

    @Override
    protected Pair<List<Double>, List<Double>> mate(List<Double> first, List<Double> second) {
        int length = first.size();
        List<Double> firstChild = new ArrayList<>(length);
        List<Double> secondChild = new ArrayList<>(length);

        int lastCrossover = 0;
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < crossovers; i++) {
            int crossover = 1 + lastCrossover + random.nextInt(length - lastCrossover - (crossovers - i));
            for (int j = lastCrossover; j < crossover; j++) {
                firstChild.add(first.get(j));
                secondChild.add(second.get(j));
            }

            var temp = firstChild;
            firstChild = secondChild;
            secondChild = temp;

            lastCrossover = crossover;
        }

        for (int i = lastCrossover; i < length; i++) {
            firstChild.add(first.get(i));
            secondChild.add(second.get(i));
        }

        return new Pair<>(firstChild, secondChild);
    }
}
