package apr.genetics.operators.mutation.binary;

import apr.util.SourceOfRandomness;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinarySimpleMutation extends AbstractBinaryMutation {

    private final double pm;

    public BinarySimpleMutation(double pm) {
        this.pm = pm;
    }

    @Override
    protected List<byte[]> mutate(List<byte[]> representation) {
        List<byte[]> mutatedRepresentation = new ArrayList<>(representation.size());
        Random random = SourceOfRandomness.getSource();
        for (byte[] original : representation) {
            int n = original.length;
            byte[] mutated = new byte[n];
            System.arraycopy(original, 0, mutated, 0, n);
            for (int i = 0; i < n; i++) {
                if (random.nextDouble() <= pm) {
                    mutated[i] = (byte) (1 - mutated[i]);
                }
            }
            mutatedRepresentation.add(mutated);
        }
        return mutatedRepresentation;
    }
}
