package apr.optimization.algorithms;

import apr.optimization.function.IFunction;

public abstract class AbstractOptimizationAlgorithm implements IOptimizationAlgorithm {

    protected final IFunction f;
    protected double epsilon = 1e-6;

    protected AbstractOptimizationAlgorithm(IFunction f) {
        this.f = f;
    }

    public AbstractOptimizationAlgorithm(IFunction f, double epsilon) {
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
