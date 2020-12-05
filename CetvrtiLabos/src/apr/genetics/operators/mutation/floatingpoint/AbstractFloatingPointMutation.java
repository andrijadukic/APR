package apr.genetics.operators.mutation.floatingpoint;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.floatingpoint.FloatingPointChromosome;
import apr.genetics.exceptions.InvalidChromosomeTypeException;
import apr.genetics.operators.mutation.AbstractFieldChromosomeMutation;

abstract class AbstractFloatingPointMutation extends AbstractFieldChromosomeMutation<FloatingPointChromosome, Double> {

    @Override
    protected FloatingPointChromosome typeCheck(Chromosome chromosome) {
        if (!(chromosome instanceof FloatingPointChromosome floatingPointChromosome))
            throw new InvalidChromosomeTypeException(FloatingPointChromosome.class, chromosome.getClass());

        return floatingPointChromosome;
    }
}
