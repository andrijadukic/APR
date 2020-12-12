package apr.genetics.operators.mutation.floatingpoint;

import apr.util.SourceOfRandomness;

import java.util.ArrayList;
import java.util.List;

public class FloatingPointSinglePointMutation extends AbstractFloatingPointMutation {

    private final double lb;
    private final double ub;

    public FloatingPointSinglePointMutation(double lb, double ub) {
        this.lb = lb;
        this.ub = ub;
    }

    @Override
    protected List<Double> mutate(List<Double> representation) {
        List<Double> mutatedRepresentation = new ArrayList<>(representation);
        int rind = SourceOfRandomness.getSource().nextInt(mutatedRepresentation.size());
        mutatedRepresentation.set(rind, lb + SourceOfRandomness.getSource().nextDouble() * (ub - lb));
        return mutatedRepresentation;
    }
}
