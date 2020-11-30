package apr.genetics.operators.mutation;

import java.util.concurrent.ThreadLocalRandom;

public class DecimalSinglePointMutation extends SinglePointMutation<Double> {

    private final double lb;
    private final double ub;

    public DecimalSinglePointMutation(double lb, double ub) {
        this.lb = lb;
        this.ub = ub;
    }

    @Override
    protected Double mutatedValue(Double original) {
        return lb + ThreadLocalRandom.current().nextDouble() * (ub - lb);
    }
}
