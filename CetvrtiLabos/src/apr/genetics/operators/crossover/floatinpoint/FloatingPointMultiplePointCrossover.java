package apr.genetics.operators.crossover.floatinpoint;

import apr.genetics.chromosomes.floatingpoint.FloatingPointChromosome;
import apr.genetics.chromosomes.util.ChromosomePair;
import apr.genetics.exceptions.InsufficientChromosomeLength;

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
    public ChromosomePair mate(FloatingPointChromosome firstParent, FloatingPointChromosome secondParent) {
        int length = firstParent.getLength();

        if (length <= crossovers) throw new InsufficientChromosomeLength(length, crossovers + 1);

        List<Double> firstParentRepresentation = firstParent.getRepresentation();
        List<Double> secondParentRepresentation = secondParent.getRepresentation();

        List<Double> firstChildRepresentation = new ArrayList<>(length);
        List<Double> secondChildRepresentation = new ArrayList<>(length);

        int lastCrossover = 0;
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < crossovers; i++) {
            int crossover = 1 + lastCrossover + random.nextInt(length - lastCrossover - (crossovers - i));
            for (int j = lastCrossover; j < crossover; j++) {
                firstChildRepresentation.add(firstParentRepresentation.get(j));
                secondChildRepresentation.add(secondParentRepresentation.get(j));
            }

            var temp = firstChildRepresentation;
            firstChildRepresentation = secondChildRepresentation;
            secondChildRepresentation = temp;

            lastCrossover = crossover;
        }

        for (int i = lastCrossover; i < length; i++) {
            firstChildRepresentation.add(firstParentRepresentation.get(i));
            secondChildRepresentation.add(secondParentRepresentation.get(i));
        }

        return new ChromosomePair(firstParent.newInstance(firstChildRepresentation), firstParent.newInstance(secondChildRepresentation));
    }
}
