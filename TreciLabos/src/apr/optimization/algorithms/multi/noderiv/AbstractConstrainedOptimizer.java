package apr.optimization.algorithms.multi.noderiv;

import apr.functions.constraints.Constraints;
import apr.functions.constraints.InequalityConstraint;
import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.ConstrainedMultivariateCostFunction;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;
import apr.optimization.exceptions.DivergenceLimitReachedException;

import java.util.Objects;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.*;

/**
 * Abstract implementation of a constrained optimizer
 */
public abstract class AbstractConstrainedOptimizer implements IMultivariateOptimizer {

    private final ConstrainedMultivariateCostFunction function;

    protected double epsilon = DEFAULT_EPSILON;
    private double coefficient = DEFAULT_COEFFICIENT;
    private int divergenceLimit = DEFAULT_DIVERGENCE_LIMIT;

    private static final double DEFAULT_EPSILON = 1e-6;
    private static final double DEFAULT_COEFFICIENT = 1.;
    private static final int DEFAULT_DIVERGENCE_LIMIT = 100;

    protected AbstractConstrainedOptimizer(ConstrainedMultivariateCostFunction function) {
        this.function = Objects.requireNonNull(function);
    }

    protected AbstractConstrainedOptimizer(ConstrainedMultivariateCostFunction function, double epsilon, double coefficient, int divergenceLimit) {
        this(function);
        this.coefficient = coefficient;
        this.epsilon = epsilon;
        this.divergenceLimit = divergenceLimit;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getDivergenceLimit() {
        return divergenceLimit;
    }

    public void setDivergenceLimit(int divergenceLimit) {
        this.divergenceLimit = divergenceLimit;
    }

    @Override
    public IVector search(IVector x0) {
        double initialValue = function.valueAt(x0);

        InequalityConstraint[] inequalityConstraints = function.getInequalityConstraints();
        if (!Constraints.test(x0, inequalityConstraints)) {
            x0 = interiorPoint(x0, inequalityConstraints);
        }

        double t = coefficient;
        IVector x = x0.copy();
        double best = initialValue;
        int count = 0;
        while (true) {
            if (count > divergenceLimit)
                throw new DivergenceLimitReachedException(divergenceLimit, "minimum found [" + x + "]");

            function.setCoefficient(t);
            IVector snapshot = x.copy();
            x = argMin(function, x);

            double value = function.valueAt(x);
            if (value < best) {
                best = value;
                count = 0;
            } else {
                count++;
            }

            if (norm(subtract(snapshot, x, MUTABLE)) < epsilon) break;

            t *= 10;
        }
        return x;
    }

    protected abstract IVector interiorPoint(IVector x0, InequalityConstraint[] inequalityConstraints);

    protected abstract IVector argMin(IMultivariateCostFunction function, IVector x0);
}
