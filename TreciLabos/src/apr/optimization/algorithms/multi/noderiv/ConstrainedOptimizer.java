package apr.optimization.algorithms.multi.noderiv;

import apr.linear.util.linalg.LinearAlgebra;
import apr.linear.util.linalg.OperationMutability;
import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.ConstrainedMultivariateCostFunction;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.*;

public abstract class ConstrainedOptimizer implements IMultivariateOptimizer {

    protected final ConstrainedMultivariateCostFunction f;
    protected double coefficient;
    protected double epsilon;

    private static final double DEFAULT_EPSILON = 1e-6;
    private static final double DEFAULT_COEFFICIENT = 1.;

    protected ConstrainedOptimizer(ConstrainedMultivariateCostFunction f) {
        this(f, DEFAULT_EPSILON, DEFAULT_COEFFICIENT);
    }

    protected ConstrainedOptimizer(ConstrainedMultivariateCostFunction f, double epsilon, double coefficient) {
        this.f = f;
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
        f.setCoefficient(t);
        while (true) {
            IVector snapshot = x;
            x = argMin(x);

            if (norm(subtract(x, snapshot, IMMUTABLE)) < epsilon) break;

            t *= 10;
        }
        return x;
    }

    protected abstract IVector argMin(IVector x0);
}
