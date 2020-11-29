package apr.optimization.algorithms.multi.noderiv;

import apr.optimization.algorithms.multi.IMultivariateCostFunction;

import java.util.Objects;

/**
 * Abstract helper class for storing common properties of {@code IMultivariableOptimizationAlgorithm} classes
 */
abstract class AbstractMultivariateOptimizer implements IMultivariateOptimizer {

    protected final IMultivariateCostFunction function;

    protected double epsilon = DEFAULT_EPSILON;

    protected static double DEFAULT_EPSILON = 1e-6;

    protected AbstractMultivariateOptimizer(IMultivariateCostFunction function) {
        this.function = Objects.requireNonNull(function);
    }

    protected AbstractMultivariateOptimizer(IMultivariateCostFunction function, double epsilon) {
        this(function);
        this.epsilon = epsilon;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }
}
