package apr.genetics.operators.mutation;

import apr.genetics.chromosomes.Chromosome;

@FunctionalInterface
public interface MutationOperator {

    Chromosome mutate(Chromosome chromosome);

    default MutationOperator then(MutationOperator next) {
        return chromosome -> next.mutate(mutate(chromosome));
    }
}
