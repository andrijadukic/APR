package apr.genetics.operators.crossover;

import apr.genetics.chromosomes.util.ChromosomePair;
import apr.genetics.chromosomes.FloatingPointChromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SingleArithmeticCrossover extends AbstractArithmeticCrossover {

    public SingleArithmeticCrossover() {
    }

    public SingleArithmeticCrossover(double alpha) {
        super(alpha);
    }

    @Override
    protected ChromosomePair mate(FloatingPointChromosome first, FloatingPointChromosome second) {
        List<Double> firstParentRepresentation = first.getRepresentation();
        List<Double> secondParentRepresentation = second.getRepresentation();

        List<Double> firstChildRepresentation = new ArrayList<>(firstParentRepresentation);
        List<Double> secondChildRepresentation = new ArrayList<>(secondParentRepresentation);

        Random random = ThreadLocalRandom.current();
        int k = random.nextInt(first.getLength());
        double val = alpha * (firstParentRepresentation.get(k) + secondParentRepresentation.get(k));
        firstChildRepresentation.set(k, val);
        secondChildRepresentation.set(k, val);

        return new ChromosomePair(first.newInstance(firstChildRepresentation), second.newInstance(secondChildRepresentation));
    }
}
