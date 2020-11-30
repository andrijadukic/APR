package apr.genetics.operators.crossover;

import apr.genetics.chromosomes.util.ChromosomePair;
import apr.genetics.chromosomes.DecimalChromosome;

import java.util.ArrayList;
import java.util.List;

public class WholeArithmeticCrossover extends AbstractArithmeticCrossover {

    public WholeArithmeticCrossover() {
    }

    public WholeArithmeticCrossover(double alpha) {
        super(alpha);
    }

    @Override
    protected ChromosomePair mate(DecimalChromosome first, DecimalChromosome second) {
        List<Double> firstParentRepresentation = first.getRepresentation();
        List<Double> secondParentRepresentation = second.getRepresentation();

        List<Double> firstChildRepresentation = new ArrayList<>(firstParentRepresentation);
        List<Double> secondChildRepresentation = new ArrayList<>(secondParentRepresentation);

        for (int i = 0, length = first.getLength(); i < length; i++) {
            double val = alpha * (firstParentRepresentation.get(i) + secondParentRepresentation.get(i));
            firstChildRepresentation.set(i, val);
            secondChildRepresentation.set(i, val);
        }

        return new ChromosomePair(first.newInstance(firstChildRepresentation), second.newInstance(secondChildRepresentation));
    }
}
