package apr.optimization.algorithms.fminunc;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.fminbnd.GoldenSectionSearch;
import apr.optimization.algorithms.fminsearch.IMultivariableOptimizationAlgorithm;
import apr.optimization.exceptions.MaximumIterationCountExceededException;
import apr.optimization.functions.IDifferentiableMultivariableCostFunction;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.LinearAlgebra.multiply;
import static apr.linear.util.linalg.OperationMutability.IMMUTABLE;
import static apr.linear.util.linalg.OperationMutability.MUTABLE;

abstract class AbstractDifferentiableMultivariableOptimizationAlgorithm implements IMultivariableOptimizationAlgorithm {

    protected final IDifferentiableMultivariableCostFunction f;

    protected double epsilon;
    protected int maxIter;
    protected boolean computeOptimalStep;

    private static final double DEFAULT_EPSILON = 1e-6;
    private static final int DEFAULT_MAXIMUM_ITERATION = 1000;
    private static final boolean DEFAULT_COMPUTE_OPTIMAL_STEP = false;

    protected AbstractDifferentiableMultivariableOptimizationAlgorithm(IDifferentiableMultivariableCostFunction f) {
        this(f, DEFAULT_EPSILON, DEFAULT_MAXIMUM_ITERATION, DEFAULT_COMPUTE_OPTIMAL_STEP);
    }

    protected AbstractDifferentiableMultivariableOptimizationAlgorithm(IDifferentiableMultivariableCostFunction f, double epsilon, int maxIter, boolean computeOptimalStep) {
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

            IVector gradient = f.gradient(x);
            double norm = norm(gradient);

            if (norm < epsilon) break;

            IVector direction = computeDirection(x, gradient);

            if (computeOptimalStep) {
                double ratio = 1. / norm;
                direction = multiply(direction, ratio, MUTABLE);
                final IVector argX = x.copy();
                final IVector argV = direction.copy();
                ratio *= new GoldenSectionSearch(lambda -> f.valueAt(add(multiply(argV, lambda, IMMUTABLE), argX, MUTABLE))).search(0);
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
