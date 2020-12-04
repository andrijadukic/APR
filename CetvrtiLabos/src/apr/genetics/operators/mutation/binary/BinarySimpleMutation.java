package apr.genetics.operators.mutation.binary;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BinarySimpleMutation extends AbstractBinaryMutation {

    private final double pm;

    public BinarySimpleMutation(double pm) {
        this.pm = pm;
    }

    @Override
    protected List<BitSet> mutate(List<BitSet> representation, int numberOfBits) {
        int length = representation.size();
        List<BitSet> mutatedRepresentation = new ArrayList<>(length);
        Random random = ThreadLocalRandom.current();
        for (BitSet original : representation) {
            BitSet mutated = new BitSet();
            for (int i = 0; i < numberOfBits; i++) {
                if (random.nextDouble() < pm) {
                    mutated.set(i, random.nextBoolean());
                } else {
                    mutated.set(i, original.get(i));
                }
            }
            mutatedRepresentation.add(mutated);
        }
        return mutatedRepresentation;
    }
}
