package apr.genetics.operators.mutation.binary;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BinarySinglePointMutation extends AbstractBinaryMutation {

    @Override
    protected List<BitSet> mutate(List<BitSet> representation, int numberOfBits) {
        int length = representation.size();
        List<BitSet> mutatedRepresentation = new ArrayList<>(length);
        Random random = ThreadLocalRandom.current();
        for (BitSet original : representation) {
            BitSet mutated = new BitSet();
            mutated.or(original);
            mutated.set(random.nextInt(numberOfBits), random.nextBoolean());
        }
        return mutatedRepresentation;
    }
}
