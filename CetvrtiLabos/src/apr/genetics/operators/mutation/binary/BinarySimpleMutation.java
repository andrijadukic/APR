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
    protected List<BitSet> mutate(List<BitSet> representation) {
        List<BitSet> mutatedRepresentation = new ArrayList<>(representation.size());
        Random random = ThreadLocalRandom.current();
        for (BitSet original : representation) {
            int n = original.size();
            BitSet mutated = new BitSet(n);
            for (int i = 0; i < n; i++) {
                mutated.set(i, (random.nextDouble() < pm) ? random.nextBoolean() : original.get(i));
            }
            mutatedRepresentation.add(mutated);
        }
        return mutatedRepresentation;
    }
}
