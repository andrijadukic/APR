package apr.optimization.algorithms.multi.gradient;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.uni.GoldenSectionSearch;
import apr.optimization.algorithms.multi.noderiv.IMultivariateOptimizer;
import apr.optimization.exceptions.MaximumIterationCountExceededException;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.IMMUTABLE;
import static apr.linear.util.linalg.OperationMutability.MUTABLE;

abstract class AbstractDifferentiableMultivariateOptimizer implements IMultivariateOptimizer {

    protected final IDifferentiableMultivariateCostFunction f;

    protected double epsilon;
    protected int maxIter;
    protected boolean computeOptimalStep;

    private static final double DEFAULT_EPSILON = 1e-6;
    private static final int DEFAULT_MAXIMUM_ITERATION = 100;
    private static final boolean DEFAULT_COMPUTE_OPTIMAL_STEP = false;

    protected AbstractDifferentiableMultivariateOptimizer(IDifferentiableMultivariateCostFunction f) {
        this(f, DEFAULT_EPSILON, DEFAULT_MAXIMUM_ITERATION, DEFAULT_COMPUTE_OPTIMAL_STEP);
    }

    protected AbstractDifferentiableMultivariateOptimizer(IDifferentiableMultivariateCostFunction f, double epsilon, int maxIter, boolean computeOptimalStep) {
        this.f = f;
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

        double previousValue = f.valueAt(x);
        double currentValue = previousValue;

        int iter = 0;
        while (true) {
            if (iter > maxIter) throw new MaximumIterationCountExceededException();

            IVector direction = computeDirection(x, f.gradient(x));
            double norm = norm(direction);

            if (norm < epsilon) break;

            direction = multiply(direction, 1. / norm, MUTABLE);

            if (computeOptimalStep) {
                final IVector currentX = x.copy();
                final IVector initialDirection = direction.copy();
                double ratio = new GoldenSectionSearch(
                        lambda -> f.valueAt(add(multiply(initialDirection, lambda, IMMUTABLE), currentX, MUTABLE))
                ).search(0);
                direction = multiply(direction, ratio, MUTABLE);
            }

            x = add(x, direction, MUTABLE);

            previousValue = currentValue;
            currentValue = f.valueAt(x);

            if (previousValue > currentValue) {
                iter = 0;
            } else {
                iter++;
            }
        }

        return x;
    }

    protected abstract IVector computeDirection(IVector x, IVector gradient);
}
