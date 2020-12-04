package apr.genetics.operators.mutation.floatingpoint;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.FloatingPointChromosome;
import apr.genetics.exceptions.InvalidChromosomeTypeException;
import apr.genetics.operators.mutation.MutationOperator;

import java.util.List;

abstract class AbstractFloatingPointMutation implements MutationOperator {

    @Override
    public Chromosome mutate(Chromosome chromosome) {
        if (!(chromosome instanceof FloatingPointChromosome floatingPointChromosome))
            throw new InvalidChromosomeTypeException(FloatingPointChromosome.class, chromosome.getClass());

        List<Double> representation = floatingPointChromosome.getRepresentation();
        List<Double> mutatedRepresentation = mutate(representation);

        return floatingPointChromosome.newInstance(mutatedRepresentation);
    }

    protected abstract List<Double> mutate(List<Double> representation);
}
