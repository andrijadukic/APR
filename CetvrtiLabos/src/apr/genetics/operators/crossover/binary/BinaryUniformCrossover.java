package apr.genetics.operators.crossover.binary;

import apr.genetics.chromosomes.binary.BinaryChromosome;
import apr.genetics.chromosomes.util.ChromosomePair;
import apr.util.Pair;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BinaryUniformCrossover extends AbstractBinaryCrossover {

    @Override
    protected Pair<List<BitSet>, List<BitSet>> mate(List<BitSet> first, List<BitSet> second) {
        int length = first.size();
        List<BitSet> firstChild = new ArrayList<>(length);
        List<BitSet> secondChild = new ArrayList<>(length);

        Random random = ThreadLocalRandom.current();
        for (int dimension = 0; dimension < length; dimension++) {
            BitSet p1 = first.get(dimension);
            BitSet p2 = second.get(dimension);

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

            firstChild.add(ch1);
            secondChild.add(ch2);
        }

        return new Pair<>(firstChild, secondChild);
    }
}
