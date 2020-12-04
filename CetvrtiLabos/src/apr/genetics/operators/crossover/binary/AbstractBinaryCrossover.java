package apr.genetics.operators.crossover.binary;

import apr.genetics.chromosomes.binary.BinaryChromosome;
import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.util.ChromosomePair;
import apr.genetics.exceptions.ChromosomeLengthMismatchException;
import apr.genetics.exceptions.InvalidChromosomeTypeException;
import apr.genetics.operators.crossover.CrossoverOperator;

abstract class AbstractBinaryCrossover implements CrossoverOperator {

    @Override
    public ChromosomePair crossover(Chromosome first, Chromosome second) {
        if (!(first instanceof BinaryChromosome firstParent))
            throw new InvalidChromosomeTypeException(BinaryChromosome.class, first.getClass());

        if (!(second instanceof BinaryChromosome secondParent))
            throw new InvalidChromosomeTypeException(BinaryChromosome.class, second.getClass());

        int length = firstParent.getLength();
        int lengthOther = secondParent.getLength();

        if (length != lengthOther) throw new ChromosomeLengthMismatchException(length, lengthOther);

        return mate(firstParent, secondParent);
    }

    protected abstract ChromosomePair mate(BinaryChromosome firstParent, BinaryChromosome secondParent);
}
