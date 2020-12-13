package apr.genetics.operators.crossover.binary;

import apr.util.Pair;
import apr.util.SourceOfRandomness;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class BinaryUniformCrossover extends AbstractBinaryCrossover {

    @Override
    protected Pair<List<byte[]>, List<byte[]>> mate(List<byte[]> first, List<byte[]> second) {
        int length = first.size();
        List<byte[]> firstChild = new ArrayList<>(length);
        List<byte[]> secondChild = new ArrayList<>(length);

        Random random = SourceOfRandomness.getSource();
        for (int dimension = 0; dimension < length; dimension++) {
            byte[] p1 = first.get(dimension);
            byte[] p2 = second.get(dimension);

            int numberOfBits = Math.min(p1.length, p2.length);

            byte[] ch1 = new byte[numberOfBits];
            byte[] ch2 = new byte[numberOfBits];
            for (int i = 0; i < numberOfBits; i++) {
                ch1[i] = random.nextDouble() <= 0.5 ? p1[i] : p2[i];
                ch2[i] = random.nextDouble() <= 0.5 ? p1[i] : p2[i];
            }

            firstChild.add(ch1);
            secondChild.add(ch2);
        }

        return new Pair<>(firstChild, secondChild);
    }
}
