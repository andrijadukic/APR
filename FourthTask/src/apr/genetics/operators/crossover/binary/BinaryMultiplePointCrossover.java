package apr.genetics.operators.crossover.binary;

import apr.util.Pair;
import apr.util.SourceOfRandomness;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinaryMultiplePointCrossover extends AbstractBinaryCrossover {

    private final int crossovers;

    public BinaryMultiplePointCrossover(int crossovers) {
        if (crossovers <= 0) throw new InvalidParameterException();
        this.crossovers = crossovers;
    }

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

            int lastCrossover = 0;
            for (int i = 0; i < crossovers; i++) {
                int crossover = 1 + lastCrossover + random.nextInt(numberOfBits - lastCrossover - (crossovers - i));
                for (int j = lastCrossover; j < crossover; j++) {
                    ch1[i] = x1[i];
                    ch2[i] = x2[i];
                }

                var temp = ch1;
                ch1 = ch2;
                ch2 = temp;

                lastCrossover = crossover;
            }

            for (int i = lastCrossover; i < length; i++) {
                ch1[i] = x1[i];
                ch2[i] = x2[i];
            }

            firstChild.add(ch1);
            secondChild.add(ch2);
        }

        return new Pair<>(firstChild, secondChild);
    }
}
