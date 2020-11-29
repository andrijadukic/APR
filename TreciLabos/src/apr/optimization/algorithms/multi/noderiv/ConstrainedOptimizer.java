package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.ConstrainedMultivariateCostFunction;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.*;

public abstract class ConstrainedOptimizer implements IMultivariateOptimizer {

    private final ConstrainedMultivariateCostFunction function;

    protected double epsilon = DEFAULT_EPSILON;
    private double coefficient = DEFAULT_COEFFICIENT;

    private static final double DEFAULT_EPSILON = 1e-6;
    private static final double DEFAULT_COEFFICIENT = 1.;

    protected ConstrainedOptimizer(ConstrainedMultivariateCostFunction function) {
        this.function = function;
    }

    protected ConstrainedOptimizer(ConstrainedMultivariateCostFunction function, double epsilon, double coefficient) {
        this.function = function;
        this.coefficient = coefficient;
        this.epsilon = epsilon;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public IVector search(IVector x0) {
        IVector x = x0.copy();
        double t = coefficient;
        while (true) {
            IVector snapshot = x.copy();

            function.setCoefficient(t);
            x = argMin(function, x);

            if (norm(subtract(x, snapshot, IMMUTABLE)) < epsilon) break;

            t *= 10;
        }
        return x;
    }

    protected abstract IVector argMin(IMultivariateCostFunction function, IVector x0);
}
