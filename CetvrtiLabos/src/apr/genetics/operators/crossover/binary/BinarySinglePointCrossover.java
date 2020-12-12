package apr.genetics.operators.crossover.binary;

import apr.util.Pair;
import apr.util.SourceOfRandomness;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class BinarySinglePointCrossover extends AbstractBinaryCrossover {

    @Override
    protected Pair<List<BitSet>, List<BitSet>> mate(List<BitSet> first, List<BitSet> second) {
        int length = first.size();
        List<BitSet> firstChild = new ArrayList<>(length);
        List<BitSet> secondChild = new ArrayList<>(length);

        Random random = SourceOfRandomness.getSource();
        for (int dimension = 0; dimension < length; dimension++) {
            BitSet x1 = first.get(dimension);
            BitSet x2 = second.get(dimension);

            int numberOfBits = Math.min(x1.size(), x2.size());

            BitSet ch1 = new BitSet(numberOfBits);
            BitSet ch2 = new BitSet(numberOfBits);
            int point = random.nextInt(numberOfBits - 1);
            for (int i = 0; i < point; i++) {
                ch1.set(i, x1.get(i));
                ch2.set(i, x2.get(i));
            }
            for (int i = point; i < numberOfBits; i++) {
                ch1.set(i, x2.get(i));
                ch2.set(i, x1.get(i));
            }

            firstChild.add(ch1);
            secondChild.add(ch2);
        }

        return new Pair<>(firstChild, secondChild);
    }
}
