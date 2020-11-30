package apr.genetics.operators.mutation;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.FieldChromosome;
import apr.genetics.exceptions.InvalidChromosomeTypeException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class SinglePointMutation<T> implements MutationOperator {

    @Override
    @SuppressWarnings("unchecked")
    public Chromosome mutate(Chromosome chromosome) {
        if (!(chromosome instanceof FieldChromosome<?>))
            throw new InvalidChromosomeTypeException(FieldChromosome.class, chromosome.getClass());

        FieldChromosome<T> fieldChromosome = (FieldChromosome<T>) chromosome;

        List<T> representation = fieldChromosome.getRepresentation();
        List<T> mutatedRepresentation = new ArrayList<>(representation);

        int rind = ThreadLocalRandom.current().nextInt(mutatedRepresentation.size());
        mutatedRepresentation.set(rind, mutatedValue(mutatedRepresentation.get(rind)));

        return fieldChromosome.newInstance(mutatedRepresentation);
    }

    protected abstract T mutatedValue(T original);
}
