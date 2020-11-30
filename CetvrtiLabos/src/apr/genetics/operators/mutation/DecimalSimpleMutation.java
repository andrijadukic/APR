package apr.genetics.operators.mutation;

import java.util.concurrent.ThreadLocalRandom;

public class DecimalSimpleMutation extends SimpleMutation<Double> {

    private final double lb;
    private final double ub;

    public DecimalSimpleMutation(double pm, double lb, double ub) {
        super(pm);
        this.lb = lb;
        this.ub = ub;
    }

    protected Double mutatedValue(Double original) {
        return lb + ThreadLocalRandom.current().nextDouble() * (ub - lb);
    }
}
