package apr.genetics.operators.mutation.binary;

import apr.genetics.chromosomes.binary.BinaryChromosome;
import apr.genetics.chromosomes.Chromosome;
import apr.genetics.exceptions.InvalidChromosomeTypeException;
import apr.genetics.operators.mutation.MutationOperator;

import java.util.BitSet;
import java.util.List;

abstract class AbstractBinaryMutation implements MutationOperator {

    @Override
    public Chromosome mutate(Chromosome chromosome) {
        if (!(chromosome instanceof BinaryChromosome binaryChromosome))
            throw new InvalidChromosomeTypeException(BinaryChromosome.class, chromosome.getClass());

        List<BitSet> representation = binaryChromosome.getRepresentation();
        List<BitSet> mutatedRepresentation = mutate(representation, binaryChromosome.getNumberOfBits());

        return binaryChromosome.newInstance(mutatedRepresentation);
    }

    protected abstract List<BitSet> mutate(List<BitSet> representation, int numberOfBits);
}
