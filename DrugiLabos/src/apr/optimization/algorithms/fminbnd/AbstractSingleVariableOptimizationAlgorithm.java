package apr.optimization.algorithms.fminbnd;

import apr.optimization.function.ISingleVariableFunction;

/**
 * Abstract helper class which concrete algorithms can extend from
 */
public abstract class AbstractSingleVariableOptimizationAlgorithm implements ISingleVariableOptimizationAlgorithm {

    protected final ISingleVariableFunction f;
    protected double epsilon = 1e-6;

    protected AbstractSingleVariableOptimizationAlgorithm(ISingleVariableFunction f) {
        this.f = f;
    }

    protected AbstractSingleVariableOptimizationAlgorithm(ISingleVariableFunction f, double epsilon) {
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
