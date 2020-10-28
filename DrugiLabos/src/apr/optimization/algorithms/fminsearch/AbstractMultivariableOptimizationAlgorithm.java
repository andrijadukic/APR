package apr.optimization.algorithms.fminsearch;

import apr.optimization.function.IMultivariableFunction;

public abstract class AbstractMultivariableOptimizationAlgorithm implements IMultivariableOptimizationAlgorithm {

    protected final IMultivariableFunction f;
    protected double epsilon = 1e-6;

    protected AbstractMultivariableOptimizationAlgorithm(IMultivariableFunction f) {
        this.f = f;
    }

    public AbstractMultivariableOptimizationAlgorithm(IMultivariableFunction f, double epsilon) {
        this(f);
        this.epsilon = epsilon;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }
}
