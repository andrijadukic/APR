package apr.genetics.operators.crossover;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.util.ChromosomePair;
import apr.genetics.chromosomes.DecimalChromosome;
import apr.genetics.exceptions.ChromosomeLengthMismatchException;
import apr.genetics.exceptions.InvalidChromosomeTypeException;

abstract class AbstractArithmeticCrossover implements CrossoverOperator {

    protected double alpha;

    private static final double DEFAULT_ALPHA = 0.5;

    protected AbstractArithmeticCrossover() {
        this(DEFAULT_ALPHA);
    }

    protected AbstractArithmeticCrossover(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public ChromosomePair crossover(Chromosome first, Chromosome second) {
        if (!(first instanceof DecimalChromosome firstParent))
            throw new InvalidChromosomeTypeException(DecimalChromosome.class, first.getClass());

        if (!(second instanceof DecimalChromosome secondParent))
            throw new InvalidChromosomeTypeException(DecimalChromosome.class, second.getClass());

        int length = firstParent.getLength();
        int lengthOther = secondParent.getLength();

        if (length != lengthOther) throw new ChromosomeLengthMismatchException(length, lengthOther);

        return mate(firstParent, secondParent);
    }

    protected abstract ChromosomePair mate(DecimalChromosome first, DecimalChromosome second);
}
