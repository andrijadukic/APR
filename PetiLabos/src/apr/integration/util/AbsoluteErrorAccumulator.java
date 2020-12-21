package apr.integration.util;

import apr.functions.UnivariateVectorFunction;
import apr.linear.vector.Vector;

public class AbsoluteErrorAccumulator implements LinearSystemIntegrationObserver {

    private final UnivariateVectorFunction function;

    private double accumulatedError;

    public AbsoluteErrorAccumulator(UnivariateVectorFunction function) {
        this.function = function;
    }

    @Override
    public void update(StateStatistics statistics) {
        Vector approximation = statistics.x();
        Vector actual = function.valueAt(statistics.step());

        for (int i = 0, n = approximation.getDimension(); i < n; i++) {
            accumulatedError += Math.abs(approximation.get(i) - actual.get(i));
        }
    }

    public void clear() {
        accumulatedError = 0.;
    }

    public double getAccumulatedError() {
        return accumulatedError;
    }
}
