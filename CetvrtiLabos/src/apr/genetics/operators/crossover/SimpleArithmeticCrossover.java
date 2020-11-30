package apr.genetics.operators.crossover;

import apr.genetics.chromosomes.DecimalChromosome;
import apr.genetics.chromosomes.util.ChromosomePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleArithmeticCrossover extends AbstractArithmeticCrossover {

    public SimpleArithmeticCrossover() {
    }

    public SimpleArithmeticCrossover(double alpha) {
        super(alpha);
    }

    @Override
    protected ChromosomePair mate(DecimalChromosome first, DecimalChromosome second) {
        List<Double> firstParentRepresentation = first.getRepresentation();
        List<Double> secondParentRepresentation = second.getRepresentation();

        List<Double> firstChildRepresentation = new ArrayList<>(firstParentRepresentation);
        List<Double> secondChildRepresentation = new ArrayList<>(secondParentRepresentation);

        int length = first.getLength();
        Random random = ThreadLocalRandom.current();
        for (int i = random.nextInt(length); i < length; i++) {
            double val = alpha * (firstParentRepresentation.get(i) + secondParentRepresentation.get(i));
            firstChildRepresentation.set(i, val);
            secondChildRepresentation.set(i, val);
        }

        return new ChromosomePair(first.newInstance(firstChildRepresentation), second.newInstance(secondChildRepresentation));
    }
}

