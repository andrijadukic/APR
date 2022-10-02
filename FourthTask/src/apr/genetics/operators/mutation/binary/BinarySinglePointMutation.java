package apr.genetics.operators.mutation.binary;

import apr.util.SourceOfRandomness;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinarySinglePointMutation extends AbstractBinaryMutation {

    @Override
    protected List<byte[]> mutate(List<byte[]> representation) {
        int length = representation.size();
        List<byte[]> mutatedRepresentation = new ArrayList<>(length);
        Random random = SourceOfRandomness.getSource();
        for (byte[] original : representation) {
            int n = original.length;
            byte[] mutated = new byte[n];
            System.arraycopy(original, 0, mutated, 0, n);
            int rind = random.nextInt(n);
            mutated[rind] = (byte) (1 - mutated[rind]);
            mutatedRepresentation.add(mutated);
        }
        return mutatedRepresentation;
    }
}
