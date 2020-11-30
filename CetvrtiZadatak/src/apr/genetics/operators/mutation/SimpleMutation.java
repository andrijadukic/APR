package apr.genetics.operators.mutation;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.FieldChromosome;
import apr.genetics.exceptions.InvalidChromosomeTypeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class SimpleMutation<T> implements MutationOperator {

    protected final double pm;

    protected SimpleMutation(double pm) {
        this.pm = pm;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Chromosome mutate(Chromosome chromosome) {
        if (!(chromosome instanceof FieldChromosome<?>))
            throw new InvalidChromosomeTypeException(FieldChromosome.class, chromosome.getClass());

        FieldChromosome<T> fieldChromosome = (FieldChromosome<T>) chromosome;

        List<T> representation = fieldChromosome.getRepresentation();
        List<T> mutatedRepresentation = new ArrayList<>(representation);

        Random random = ThreadLocalRandom.current();
        for (int i = 0, n = mutatedRepresentation.size(); i < n; i++) {
            if (random.nextDouble() < pm) {
                mutatedRepresentation.set(i, mutatedValue(mutatedRepresentation.get(i)));
            }
        }

        return fieldChromosome.newInstance(mutatedRepresentation);
    }

    protected abstract T mutatedValue(T original);
}
