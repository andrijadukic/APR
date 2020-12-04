package apr.genetics.operators.crossover.floatinpoint;

import apr.genetics.chromosomes.util.ChromosomePair;
import apr.genetics.chromosomes.floatingpoint.FloatingPointChromosome;

import java.util.ArrayList;
import java.util.List;

public class WholeArithmeticCrossover extends AbstractArithmeticCrossover {

    public WholeArithmeticCrossover() {
    }

    public WholeArithmeticCrossover(double alpha) {
        super(alpha);
    }

    @Override
    protected ChromosomePair mate(FloatingPointChromosome firstParent, FloatingPointChromosome secondParent) {
        List<Double> firstParentRepresentation = firstParent.getRepresentation();
        List<Double> secondParentRepresentation = secondParent.getRepresentation();

        List<Double> firstChildRepresentation = new ArrayList<>(firstParentRepresentation);
        List<Double> secondChildRepresentation = new ArrayList<>(secondParentRepresentation);

        for (int i = 0, length = firstParent.getLength(); i < length; i++) {
            double val = alpha * (firstParentRepresentation.get(i) + secondParentRepresentation.get(i));
            firstChildRepresentation.set(i, val);
            secondChildRepresentation.set(i, val);
        }

        return new ChromosomePair(firstParent.newInstance(firstChildRepresentation), secondParent.newInstance(secondChildRepresentation));
    }
}
