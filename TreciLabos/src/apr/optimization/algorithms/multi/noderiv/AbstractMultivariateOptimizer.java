package apr.optimization.algorithms.multi.noderiv;

import apr.optimization.algorithms.multi.IMultivariateCostFunction;

/**
 * Abstract helper class for storing common properties of {@code IMultivariableOptimizationAlgorithm} classes
 */
abstract class AbstractMultivariateOptimizer implements IMultivariateOptimizer {

    protected final IMultivariateCostFunction f;
    protected double epsilon;

    protected static double DEFAULT_EPSILON = 1e-6;

    protected AbstractMultivariateOptimizer(IMultivariateCostFunction f) {
        this(f, DEFAULT_EPSILON);
    }

    protected AbstractMultivariateOptimizer(IMultivariateCostFunction f, double epsilon) {
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
