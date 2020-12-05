package apr.genetics.operators.crossover;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.FieldChromosome;
import apr.genetics.chromosomes.util.ChromosomePair;
import apr.genetics.exceptions.ChromosomeLengthMismatchException;
import apr.util.Pair;

import java.util.List;

public abstract class AbstractFieldChromosomeCrossover<T, C extends FieldChromosome<T>> implements CrossoverOperator {

    @Override
    public ChromosomePair crossover(Chromosome first, Chromosome second) {
        Pair<C, C> parents = typeCheck(first, second);

        C p1 = parents.first();
        C p2 = parents.second();

        validityCheck(p1, p2);

        List<T> rep1 = p1.getRepresentation();
        List<T> rep2 = p2.getRepresentation();

        Pair<List<T>, List<T>> children = mate(rep1, rep2);

        return new ChromosomePair(p1.newInstance(children.first()), p2.newInstance(children.second()));
    }

    protected abstract Pair<C, C> typeCheck(Chromosome first, Chromosome second);

    protected void validityCheck(C p1, C p2) {
        int length1 = p1.getLength();
        int length2 = p2.getLength();

        if (length1 != length2) throw new ChromosomeLengthMismatchException(length2, length1);
    }

    protected abstract Pair<List<T>, List<T>> mate(List<T> first, List<T> second);
}
