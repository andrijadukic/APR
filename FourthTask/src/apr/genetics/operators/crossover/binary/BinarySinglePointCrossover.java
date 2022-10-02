package apr.genetics.operators.crossover.binary;

import apr.util.Pair;
import apr.util.SourceOfRandomness;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinarySinglePointCrossover extends AbstractBinaryCrossover {

    @Override
    protected Pair<List<byte[]>, List<byte[]>> mate(List<byte[]> first, List<byte[]> second) {
        int length = first.size();
        List<byte[]> firstChild = new ArrayList<>(length);
        List<byte[]> secondChild = new ArrayList<>(length);

        Random random = SourceOfRandomness.getSource();
        for (int dimension = 0; dimension < length; dimension++) {
            byte[] x1 = first.get(dimension);
            byte[] x2 = second.get(dimension);

            int numberOfBits = x1.length;

            byte[] ch1 = new byte[numberOfBits];
            byte[] ch2 = new byte[numberOfBits];
            int rind = random.nextInt(numberOfBits - 1);
            for (int i = 0; i < rind; i++) {
                ch1[i] = x1[i];
                ch2[i] = x2[i];
            }
            for (int i = rind; i < numberOfBits; i++) {
                ch1[i] = x2[i];
                ch2[i] = x1[i];
            }

            firstChild.add(ch1);
            secondChild.add(ch2);
        }

        return new Pair<>(firstChild, secondChild);
    }
}
