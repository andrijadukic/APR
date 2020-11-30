package apr.optimization.algorithms.uni;

import apr.functions.IUnivariateFunction;

import java.util.Objects;

/**
 * Abstract helper class for storing common properties of {@code ISingleVariableOptimizationAlgorithm} classes
 */
abstract class AbstractUnivariateOptimizer implements IUnivariateOptimizer {

    protected final IUnivariateFunction function;

    protected double epsilon = DEFAULT_EPSILON;

    protected static double DEFAULT_EPSILON = 1e-6;

    protected AbstractUnivariateOptimizer(IUnivariateFunction function) {
        this.function = Objects.requireNonNull(function);
    }

    protected AbstractUnivariateOptimizer(IUnivariateFunction function, double epsilon) {
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
