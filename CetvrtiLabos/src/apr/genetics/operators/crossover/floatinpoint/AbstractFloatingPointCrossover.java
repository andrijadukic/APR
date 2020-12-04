package apr.genetics.operators.crossover.floatinpoint;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.FloatingPointChromosome;
import apr.genetics.chromosomes.util.ChromosomePair;
import apr.genetics.exceptions.ChromosomeLengthMismatchException;
import apr.genetics.exceptions.InvalidChromosomeTypeException;
import apr.genetics.operators.crossover.CrossoverOperator;

abstract class AbstractFloatingPointCrossover implements CrossoverOperator {

    @Override
    public ChromosomePair crossover(Chromosome first, Chromosome second) {
        if (!(first instanceof FloatingPointChromosome firstParent))
            throw new InvalidChromosomeTypeException(FloatingPointChromosome.class, first.getClass());

        if (!(second instanceof FloatingPointChromosome secondParent))
            throw new InvalidChromosomeTypeException(FloatingPointChromosome.class, second.getClass());

        int length = firstParent.getLength();
        int lengthOther = secondParent.getLength();

        if (length != lengthOther) throw new ChromosomeLengthMismatchException(length, lengthOther);

        return mate(firstParent, secondParent);
    }

    protected abstract ChromosomePair mate(FloatingPointChromosome firstParent, FloatingPointChromosome secondParent);
}
