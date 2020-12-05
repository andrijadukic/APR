package apr.genetics.operators.mutation;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.FieldChromosome;

import java.util.List;

public abstract class AbstractFieldChromosomeMutation<T, C extends FieldChromosome<T>> implements MutationOperator {

    @Override
    public Chromosome mutate(Chromosome chromosome) {
        C original = typeCheck(chromosome);

        List<T> originalRepresentation = original.getRepresentation();
        List<T> mutatedRepresentation = mutate(originalRepresentation);

        return original.newInstance(mutatedRepresentation);
    }

    protected abstract C typeCheck(Chromosome chromosome);

    protected abstract List<T> mutate(List<T> representation);
}
