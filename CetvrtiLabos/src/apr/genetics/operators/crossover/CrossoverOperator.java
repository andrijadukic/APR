package apr.genetics.operators.crossover;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.util.ChromosomePair;

@FunctionalInterface
public interface CrossoverOperator {

    ChromosomePair crossover(Chromosome first, Chromosome second);

    default CrossoverOperator andThen(CrossoverOperator next) {
        return (first, second) -> {
            ChromosomePair pair = crossover(first, second);
            return next.crossover(pair.first(), pair.second());
        };
    }
}
