package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.ConstrainedMultivariateCostFunction;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.*;

/**
 * Abstract implementation of a constrained optimizer
 */
public abstract class AbstractConstrainedOptimizer implements IMultivariateOptimizer {

    private final ConstrainedMultivariateCostFunction function;

    protected double epsilon = DEFAULT_EPSILON;
    private double coefficient = DEFAULT_COEFFICIENT;

    private static final double DEFAULT_EPSILON = 1e-6;
    private static final double DEFAULT_COEFFICIENT = 1.;

    protected AbstractConstrainedOptimizer(ConstrainedMultivariateCostFunction function) {
        this.function = function;
    }

    protected AbstractConstrainedOptimizer(ConstrainedMultivariateCostFunction function, double epsilon, double coefficient) {
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
            function.setCoefficient(t);
            IVector snapshot = x.copy();
            x = argMin(function, x);

            if (norm(subtract(snapshot, x, MUTABLE)) < epsilon) break;

            t *= 10;
        }
        return x;
    }

    protected abstract IVector argMin(IMultivariateCostFunction function, IVector x0);
}
