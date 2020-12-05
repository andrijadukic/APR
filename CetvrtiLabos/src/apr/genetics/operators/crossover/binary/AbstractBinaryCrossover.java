package apr.genetics.operators.crossover.binary;

import apr.genetics.chromosomes.binary.BinaryChromosome;
import apr.genetics.chromosomes.Chromosome;
import apr.genetics.exceptions.InvalidChromosomeTypeException;
import apr.genetics.operators.crossover.AbstractFieldChromosomeCrossover;
import apr.util.Pair;

import java.util.BitSet;

abstract class AbstractBinaryCrossover extends AbstractFieldChromosomeCrossover<BitSet, BinaryChromosome> {

    @Override
    protected Pair<BinaryChromosome, BinaryChromosome> typeCheck(Chromosome first, Chromosome second) {
        if (!(first instanceof BinaryChromosome firstParent))
            throw new InvalidChromosomeTypeException(BinaryChromosome.class, first.getClass());

        if (!(second instanceof BinaryChromosome secondParent))
            throw new InvalidChromosomeTypeException(BinaryChromosome.class, second.getClass());

        return new Pair<>(firstParent, secondParent);
    }
}
