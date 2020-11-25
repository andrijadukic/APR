package apr.optimization.algorithms.fminsearch;

import apr.optimization.functions.IMultivariableCostFunction;

/**
 * Abstract helper class for storing common properties of {@code IMultivariableOptimizationAlgorithm} classes
 */
abstract class AbstractMultivariableOptimizationAlgorithm implements IMultivariableOptimizationAlgorithm {

    protected final IMultivariableCostFunction f;
    protected double epsilon;

    protected static double DEFAULT_EPSILON = 1e-6;

    protected AbstractMultivariableOptimizationAlgorithm(IMultivariableCostFunction f) {
        this(f, DEFAULT_EPSILON);
    }

    protected AbstractMultivariableOptimizationAlgorithm(IMultivariableCostFunction f, double epsilon) {
        this.f = f;
        this.epsilon = epsilon;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }
}
