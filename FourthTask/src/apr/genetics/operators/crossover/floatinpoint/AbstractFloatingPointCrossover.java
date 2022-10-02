package apr.genetics.operators.crossover.floatinpoint;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.floatingpoint.FloatingPointChromosome;
import apr.genetics.exceptions.InvalidChromosomeTypeException;
import apr.genetics.operators.crossover.AbstractFieldChromosomeCrossover;
import apr.util.Pair;

abstract class AbstractFloatingPointCrossover extends AbstractFieldChromosomeCrossover<Double, FloatingPointChromosome> {

    @Override
    protected Pair<FloatingPointChromosome, FloatingPointChromosome> typeCheck(Chromosome first, Chromosome second) {
        if (!(first instanceof FloatingPointChromosome firstParent))
            throw new InvalidChromosomeTypeException(FloatingPointChromosome.class, first.getClass());

        if (!(second instanceof FloatingPointChromosome secondParent))
            throw new InvalidChromosomeTypeException(FloatingPointChromosome.class, second.getClass());

        return new Pair<>(firstParent, secondParent);
    }
}
