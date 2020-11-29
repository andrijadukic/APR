package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.util.Pair;
import apr.optimization.exceptions.MaximumIterationCountExceededException;
import apr.optimization.functions.constraints.Constraints;
import apr.optimization.functions.constraints.ExplicitConstraint;
import apr.optimization.functions.constraints.ImplicitConstraint;
import apr.optimization.exceptions.ConstraintsNotSatisfiedException;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.*;

/**
 * Implementation of the box simplex method
 */
public class BoxMethod extends AbstractSimplexMethod {

    private final ExplicitConstraint[] explicitConstraints;
    private final ImplicitConstraint[] implicitConstraints;

    private double alpha = DEFAULT_ALPHA;
    private int maxIter = DEFAULT_MAXIMUM_ITERATION;

    private static final double DEFAULT_ALPHA = 1.3;
    private static final int DEFAULT_MAXIMUM_ITERATION = 100;

    public BoxMethod(IMultivariateCostFunction function, ExplicitConstraint[] explicitConstraints, ImplicitConstraint[] implicitConstraints) {
        super(function);
        this.explicitConstraints = Objects.requireNonNull(explicitConstraints);
        this.implicitConstraints = Objects.requireNonNull(implicitConstraints);
    }

    public BoxMethod(IMultivariateCostFunction function,
                     ExplicitConstraint[] explicitConstraints, ImplicitConstraint[] implicitConstraints,
                     double epsilon, double alpha, int maxIter) {
        super(function, epsilon);
        this.explicitConstraints = Objects.requireNonNull(explicitConstraints);
        this.implicitConstraints = Objects.requireNonNull(implicitConstraints);
        this.alpha = alpha;
        this.maxIter = maxIter;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public int getMaxIter() {
        return maxIter;
    }

    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
    }

    @Override
    protected void validate(IVector x0) {
        if (!Constraints.test(x0, explicitConstraints) || !Constraints.test(x0, implicitConstraints))
            throw new ConstraintsNotSatisfiedException();
    }

    @Override
    protected IVector[] initialSimplex(IVector x0) {
        int n = x0.getDimension();
        int size = n * 2;
        IVector[] simplex = new IVector[size];
        simplex[0] = x0.copy();
        IVector centroid = x0.copy();
        for (int i = 1; i < size; i++) {
            IVector candidate = buildCandidate(x0, explicitConstraints);
            int iter = 0;
            while (!Constraints.test(candidate, implicitConstraints)) {
                if (iter > maxIter) throw new MaximumIterationCountExceededException(maxIter);
                candidate = multiply(add(candidate, centroid, MUTABLE), 0.5, MUTABLE);
                iter++;
            }
            simplex[i] = candidate;
            
            centroid = add(multiply(subtract(candidate, centroid, IMMUTABLE), 1. / (i + 1), MUTABLE), centroid, MUTABLE);
        }

        return simplex;
    }

    protected IVector buildCandidate(IVector x0, ExplicitConstraint[] explicitConstraints) {
        int n = x0.getDimension();
        Random random = ThreadLocalRandom.current();
        IVector candidate = x0.newInstance(n);
        for (int i = 0; i < n; i++) {
            ExplicitConstraint constraint = explicitConstraints[i];
            double lb = constraint.lowerbound();
            double ub = constraint.upperbound();
            candidate.set(i, lb + random.nextDouble() * (ub - lb));
        }
        return candidate;
    }

    @Override
    protected boolean iterate(IVector[] X, double[] fX) {
        Pair worst = worstTwo(fX);
        int h = worst.first();
        int h2 = worst.second();
        IVector xh = X[h];
        IVector xh2 = X[h2];

        IVector xc = centroid(X, h);

        IVector xr = reflection(xc, xh);
        for (int i = 0, n = xr.getDimension(); i < n; i++) {
            ExplicitConstraint constraint = explicitConstraints[i];
            double lb = constraint.lowerbound();
            double ub = constraint.upperbound();
            if (xr.get(i) < lb) {
                xr.set(i, lb);
            } else if (xr.get(i) > ub) {
                xr.set(i, ub);
            }
        }

        int iter = 0;
        while (!Constraints.test(xr, implicitConstraints)) {
            if (iter > maxIter) throw new MaximumIterationCountExceededException(maxIter);
            xr = multiply(add(xr, xc, MUTABLE), 0.5, MUTABLE);
            iter++;
        }

        if (function.valueAt(xr) > function.valueAt(xh2)) {
            xr = multiply(add(xr, xc, MUTABLE), 0.5, MUTABLE);
        }

        X[h] = xr;
        fX[h] = function.valueAt(xr);

        return isStopCriteriaMet(fX, xc);
    }

    private Pair worstTwo(double[] array) {
        int maxIndex = 0;
        int secondMaxIndex = 0;
        double maxValue = array[0];
        double secondMaxValue = array[0];
        for (int i = 1, n = array.length; i < n; i++) {
            double temp = array[i];
            if (temp > maxValue) {
                maxValue = temp;
                maxIndex = i;
            } else if (temp > secondMaxValue) {
                secondMaxValue = temp;
                secondMaxIndex = i;
            }
        }
        return new Pair(maxIndex, secondMaxIndex);
    }

    private IVector reflection(IVector xc, IVector xh) {
        return subtract(
                multiply(xc, 1 + alpha, IMMUTABLE),
                multiply(xh, alpha, IMMUTABLE),
                MUTABLE);
    }

    @Override
    public String getName() {
        return "Box method";
    }
}
