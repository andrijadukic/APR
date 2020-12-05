package apr.genetics.operators.mutation.binary;

import apr.genetics.chromosomes.binary.BinaryChromosome;
import apr.genetics.chromosomes.Chromosome;
import apr.genetics.exceptions.InvalidChromosomeTypeException;
import apr.genetics.operators.mutation.AbstractFieldChromosomeMutation;

import java.util.BitSet;

abstract class AbstractBinaryMutation extends AbstractFieldChromosomeMutation<BinaryChromosome, BitSet> {

    @Override
    protected BinaryChromosome typeCheck(Chromosome chromosome) {
        if (!(chromosome instanceof BinaryChromosome binaryChromosome))
            throw new InvalidChromosomeTypeException(BinaryChromosome.class, chromosome.getClass());

        return binaryChromosome;
    }
}
