package apr.genetics.operators.mutation.binary;

import apr.util.SourceOfRandomness;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class BinarySinglePointMutation extends AbstractBinaryMutation {

    @Override
    protected List<BitSet> mutate(List<BitSet> representation) {
        int length = representation.size();
        List<BitSet> mutatedRepresentation = new ArrayList<>(length);
        Random random = SourceOfRandomness.getSource();
        for (BitSet original : representation) {
            int n = original.size();
            BitSet mutated = new BitSet(n);
            mutated.or(original);
            mutated.flip(random.nextInt(n));
        }
        return mutatedRepresentation;
    }
}
