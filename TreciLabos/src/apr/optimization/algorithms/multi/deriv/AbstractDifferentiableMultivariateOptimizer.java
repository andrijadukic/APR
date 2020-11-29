package apr.optimization.algorithms.multi.deriv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.uni.GoldenSectionSearch;
import apr.optimization.algorithms.multi.noderiv.IMultivariateOptimizer;
import apr.optimization.exceptions.MaximumIterationCountExceededException;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.IMMUTABLE;
import static apr.linear.util.linalg.OperationMutability.MUTABLE;

abstract class AbstractDifferentiableMultivariateOptimizer implements IMultivariateOptimizer {

    protected final IDifferentiableMultivariateCostFunction function;

    protected double epsilon = DEFAULT_EPSILON;
    protected int maxIter = DEFAULT_MAXIMUM_ITERATION;
    protected boolean computeOptimalStep = DEFAULT_COMPUTE_OPTIMAL_STEP;

    private static final double DEFAULT_EPSILON = 1e-6;
    private static final int DEFAULT_MAXIMUM_ITERATION = 100;
    private static final boolean DEFAULT_COMPUTE_OPTIMAL_STEP = false;

    protected AbstractDifferentiableMultivariateOptimizer(IDifferentiableMultivariateCostFunction function) {
        this.function = function;
    }

    protected AbstractDifferentiableMultivariateOptimizer(IDifferentiableMultivariateCostFunction function, double epsilon, int maxIter, boolean computeOptimalStep) {
        this.function = function;
        this.epsilon = epsilon;
        this.maxIter = maxIter;
        this.computeOptimalStep = computeOptimalStep;
    }

    public double getEpsilon() {
        return DEFAULT_EPSILON;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public int getMaxIter() {
        return maxIter;
    }

    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
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

        int iter = 0;
        double best = function.valueAt(x);
        while (true) {
            if (iter > maxIter)
                throw new MaximumIterationCountExceededException(maxIter, "best value reached is [" + x.toString() + "]");

            IVector direction = computeDirection(x, function.gradient(x));
            double norm = norm(direction);

            if (norm < epsilon) break;

            direction = multiply(direction, 1. / norm, MUTABLE);

            if (computeOptimalStep) {
                final IVector currentX = x.copy();
                final IVector initialDirection = direction.copy();
                double ratio = new GoldenSectionSearch(
                        lambda -> function.valueAt(add(multiply(initialDirection, lambda, IMMUTABLE), currentX, MUTABLE))
                ).search(0);
                direction = multiply(direction, ratio, MUTABLE);
            }

            x = add(x, direction, MUTABLE);

            double value = function.valueAt(x);
            if (value < best) {
                iter = 0;
                best = value;
            } else {
                iter++;
            }
        }

        return x;
    }

    protected abstract IVector computeDirection(IVector x, IVector gradient);
}
