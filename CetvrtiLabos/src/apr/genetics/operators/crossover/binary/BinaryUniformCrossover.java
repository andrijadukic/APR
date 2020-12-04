package apr.genetics.operators.crossover.binary;

import apr.genetics.chromosomes.BinaryChromosome;
import apr.genetics.chromosomes.util.ChromosomePair;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BinaryUniformCrossover extends AbstractBinaryCrossover {

    @Override
    protected ChromosomePair mate(BinaryChromosome firstParent, BinaryChromosome secondParent) {
        int length = firstParent.getLength();

        List<BitSet> firstParentRepresentation = firstParent.getRepresentation();
        List<BitSet> secondParentRepresentation = secondParent.getRepresentation();

        List<BitSet> firstChildRepresentation = new ArrayList<>(length);
        List<BitSet> secondChildRepresentation = new ArrayList<>(length);

        int numberOfBits = firstParent.getNumberOfBits();
        Random random = ThreadLocalRandom.current();
        for (int dimension = 0, n = firstParentRepresentation.size(); dimension < n; dimension++) {
            BitSet x1 = firstParentRepresentation.get(dimension);
            BitSet x2 = secondParentRepresentation.get(dimension);

            BitSet ch1 = new BitSet();
            BitSet ch2 = new BitSet();
            for (int i = 0; i < numberOfBits; i++) {
                if (x1.get(i) == x2.get(i)) {
                    ch1.set(i, x1.get(i));
                    ch2.set(i, x1.get(i));
                } else {
                    ch1.set(i, random.nextBoolean());
                    ch2.set(i, random.nextBoolean());
                }
            }

            firstChildRepresentation.add(ch1);
            secondChildRepresentation.add(ch2);
        }

        return new ChromosomePair(firstParent.newInstance(firstChildRepresentation), firstParent.newInstance(secondChildRepresentation));
    }
}
