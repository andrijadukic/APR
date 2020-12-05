package apr.genetics.operators.mutation.binary;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BinarySinglePointMutation extends AbstractBinaryMutation {

    @Override
    protected List<BitSet> mutate(List<BitSet> representation) {
        int length = representation.size();
        List<BitSet> mutatedRepresentation = new ArrayList<>(length);
        Random random = ThreadLocalRandom.current();
        for (BitSet original : representation) {
            int n = original.size();
            BitSet mutated = new BitSet(n);
            mutated.or(original);
            mutated.set(random.nextInt(n), random.nextBoolean());
        }
        return mutatedRepresentation;
    }
}
