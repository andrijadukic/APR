package apr.genetics.operators.crossover;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.util.ChromosomePair;
import apr.genetics.chromosomes.FieldChromosome;
import apr.genetics.exceptions.ChromosomeLengthMismatchException;
import apr.genetics.exceptions.InsufficientChromosomeLength;
import apr.genetics.exceptions.InvalidChromosomeTypeException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MultiplePointCrossover<T> implements CrossoverOperator {

    private final int crossovers;

    public MultiplePointCrossover(int crossovers) {
        if (crossovers <= 0) throw new InvalidParameterException();
        this.crossovers = crossovers;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ChromosomePair crossover(Chromosome first, Chromosome second) {
        if (!(first instanceof FieldChromosome<?>))
            throw new InvalidChromosomeTypeException(FieldChromosome.class, first.getClass());

        if (!(second instanceof FieldChromosome<?>))
            throw new InvalidChromosomeTypeException(FieldChromosome.class, second.getClass());

        FieldChromosome<T> firstParent = (FieldChromosome<T>) first;
        FieldChromosome<T> secondParent = (FieldChromosome<T>) second;

        int length = firstParent.getLength();
        int lengthOther = secondParent.getLength();

        if (length != lengthOther) throw new ChromosomeLengthMismatchException(length, lengthOther);

        if (length <= crossovers) throw new InsufficientChromosomeLength(length, crossovers + 1);

        List<T> firstParentRepresentation = firstParent.getRepresentation();
        List<T> secondParentRepresentation = secondParent.getRepresentation();

        List<T> firstChildRepresentation = new ArrayList<>(length);
        List<T> secondChildRepresentation = new ArrayList<>(length);

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
