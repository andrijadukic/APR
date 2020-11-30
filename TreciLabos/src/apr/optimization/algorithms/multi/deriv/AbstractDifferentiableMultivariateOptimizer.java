package apr.optimization.algorithms.multi.deriv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.LineSearch;
import apr.optimization.algorithms.multi.noderiv.IMultivariateOptimizer;
import apr.optimization.exceptions.DivergenceLimitReachedException;

import java.util.Objects;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.MUTABLE;

/**
 * Abstract implementation of an optimizer using derivation methods
 */
abstract class AbstractDifferentiableMultivariateOptimizer implements IMultivariateOptimizer {

    protected final IDifferentiableMultivariateCostFunction function;

    protected double epsilon = DEFAULT_EPSILON;
    protected int divergenceLimit = DEFAULT_MAXIMUM_ITERATION;
    protected boolean computeOptimalStep = DEFAULT_COMPUTE_OPTIMAL_STEP;

    private static final double DEFAULT_EPSILON = 1e-6;
    private static final int DEFAULT_MAXIMUM_ITERATION = 100;
    private static final boolean DEFAULT_COMPUTE_OPTIMAL_STEP = true;

    protected AbstractDifferentiableMultivariateOptimizer(IDifferentiableMultivariateCostFunction function) {
        this.function = Objects.requireNonNull(function);
    }

    protected AbstractDifferentiableMultivariateOptimizer(IDifferentiableMultivariateCostFunction function, double epsilon, int divergenceLimit, boolean computeOptimalStep) {
        this(function);
        this.epsilon = epsilon;
        this.divergenceLimit = divergenceLimit;
        this.computeOptimalStep = computeOptimalStep;
    }

    public double getEpsilon() {
        return DEFAULT_EPSILON;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public int getDivergenceLimit() {
        return divergenceLimit;
    }

    public void setDivergenceLimit(int divergenceLimit) {
        this.divergenceLimit = divergenceLimit;
    }

    public boolean isComputeOptimalStep() {
        return computeOptimalStep;
    }

    public void setComputeOptimalStep(boolean computeOptimalStep) {
        this.computeOptimalStep = computeOptimalStep;
    }

    @Override
    public IVector search(IVector x0) {
        IVector x = x0.copy();

        int count = 0;
        double best = function.valueAt(x);
        while (true) {
            if (count > divergenceLimit)
                throw new DivergenceLimitReachedException(divergenceLimit, "best value reached is [" + x + "]");

            IVector direction = computeDirection(x, function.gradient(x));
            double norm = norm(direction);

            if (norm < epsilon) break;

            direction = multiply(direction, 1. / norm, MUTABLE);
            if (computeOptimalStep) {
                double coefficient = new LineSearch(function, x, direction).search(0);
                direction = multiply(direction, coefficient, MUTABLE);
            }

            x = add(x, direction, MUTABLE);

            double value = function.valueAt(x);
            if (value < best) {
                best = value;
                count = 0;
            } else {
                count++;
            }
        }

        return x;
    }

    protected abstract IVector computeDirection(IVector x, IVector gradient);
}
