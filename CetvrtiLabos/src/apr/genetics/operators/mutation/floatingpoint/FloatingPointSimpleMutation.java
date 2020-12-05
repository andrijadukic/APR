package apr.genetics.operators.mutation.floatingpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FloatingPointSimpleMutation extends AbstractFloatingPointMutation {

    private final double pm;
    private final double lb;
    private final double ub;

    public FloatingPointSimpleMutation(double pm, double lb, double ub) {
        this.pm = pm;
        this.lb = lb;
        this.ub = ub;
    }

    @Override
    protected List<Double> mutate(List<Double> representation) {
        List<Double> mutatedRepresentation = new ArrayList<>(representation);
        Random random = ThreadLocalRandom.current();
        for (int i = 0, n = mutatedRepresentation.size(); i < n; i++) {
            if (random.nextDouble() < pm) {
                mutatedRepresentation.set(i, lb + random.nextDouble() * (ub - lb));
            }
        }
        return mutatedRepresentation;
    }
}
