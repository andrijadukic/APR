package apr.optimization.algorithms.fminbnd;

import apr.optimization.functions.IUnivariateFunction;

/**
 * Abstract helper class for storing common properties of {@code ISingleVariableOptimizationAlgorithm} classes
 */
abstract class AbstractUnivariateOptimizer implements IUnivariateOptimizer {

    protected final IUnivariateFunction f;
    protected double epsilon;

    protected static double DEFAULT_EPSILON = 1e-6;

    protected AbstractUnivariateOptimizer(IUnivariateFunction f) {
        this(f, DEFAULT_EPSILON);
    }

    protected AbstractUnivariateOptimizer(IUnivariateFunction f, double epsilon) {
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
