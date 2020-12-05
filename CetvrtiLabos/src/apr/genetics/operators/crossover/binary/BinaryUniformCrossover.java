package apr.genetics.operators.crossover.binary;

import apr.genetics.chromosomes.binary.BinaryChromosome;
import apr.genetics.chromosomes.util.ChromosomePair;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BinaryUniformCrossover extends AbstractBinaryCrossover {

    @Override
    protected ChromosomePair mate(BinaryChromosome firstParent, BinaryChromosome secondParent) {
        List<BitSet> firstParentRepresentation = firstParent.getRepresentation();
        List<BitSet> secondParentRepresentation = secondParent.getRepresentation();

        int length = firstParentRepresentation.size();
        List<BitSet> firstChildRepresentation = new ArrayList<>(length);
        List<BitSet> secondChildRepresentation = new ArrayList<>(length);

        Random random = ThreadLocalRandom.current();
        for (int dimension = 0, n = firstParentRepresentation.size(); dimension < n; dimension++) {
            BitSet p1 = firstParentRepresentation.get(dimension);
            BitSet p2 = secondParentRepresentation.get(dimension);

            int numberOfBits = Math.min(p1.size(), p2.size());

            BitSet ch1 = new BitSet(numberOfBits);
            BitSet ch2 = new BitSet(numberOfBits);
            for (int i = 0; i < numberOfBits; i++) {
                if (p1.get(i) == p2.get(i)) {
                    ch1.set(i, p1.get(i));
                    ch2.set(i, p1.get(i));
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
