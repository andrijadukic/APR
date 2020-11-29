package apr.optimization.algorithms.multi.noderiv;

import apr.linear.util.Matrices;
import apr.linear.vector.IVector;
import apr.optimization.exceptions.MaximumIterationCountExceededException;
import apr.optimization.functions.constraints.Constraints;
import apr.optimization.functions.constraints.ExplicitConstraint;
import apr.optimization.functions.constraints.ImplicitConstraint;
import apr.optimization.functions.constraints.InequalityConstraint;
import apr.optimization.exceptions.ConstraintsNotSatisfiedException;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.*;

public class BoxMethod extends AbstractSimplexMethod {

    private ExplicitConstraint[] explicitConstraints;
    private ImplicitConstraint[] implicitConstraints;

    private double alpha = DEFAULT_ALPHA;
    private int maxIter = DEFAULT_MAXIMUM_ITERATION;

    private static final double DEFAULT_ALPHA = 1.3;
    private static final int DEFAULT_MAXIMUM_ITERATION = 100;

    public BoxMethod(IMultivariateCostFunction function, ExplicitConstraint[] explicitConstraints, ImplicitConstraint[] implicitConstraints) {
        super(function);
        this.explicitConstraints = explicitConstraints;
        this.implicitConstraints = implicitConstraints;
    }

    public BoxMethod(IMultivariateCostFunction function,
                     ExplicitConstraint[] explicitConstraints, ImplicitConstraint[] implicitConstraints,
                     double epsilon, double alpha, int maxIter) {
        super(function, epsilon);
        this.explicitConstraints = explicitConstraints;
        this.implicitConstraints = implicitConstraints;
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

    public ExplicitConstraint[] getExplicitConstraints() {
        return explicitConstraints;
    }

    public void setExplicitConstraints(ExplicitConstraint[] explicitConstraints) {
        this.explicitConstraints = explicitConstraints;
    }

    public ImplicitConstraint[] getImplicitConstraints() {
        return implicitConstraints;
    }

    public void setImplicitConstraints(InequalityConstraint[] implicitConstraints) {
        this.implicitConstraints = implicitConstraints;
    }

    @Override
    protected void validate(IVector x0) {
        if (!Constraints.test(x0, explicitConstraints) || !Constraints.test(x0, implicitConstraints))
            throw new ConstraintsNotSatisfiedException();
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

    @Override
    protected IVector[] initialSimplex(IVector x0) {
        int n = x0.getDimension();
        int size = n * 2;
        IVector[] simplex = new IVector[size];
        simplex[0] = x0.copy();

        IVector centroid = x0.copy();
        Random random = ThreadLocalRandom.current();
        for (int i = 1; i < size; i++) {
            IVector xi = x0.newInstance(n);
            for (int j = 0; j < n; j++) {
                ExplicitConstraint constraint = explicitConstraints[j];
                double lb = constraint.lowerbound();
                double ub = constraint.upperbound();
                xi.set(j, lb + random.nextDouble() * (ub - lb));
            }
            int iter = 0;
            while (!Constraints.test(xi, implicitConstraints)) {
                if (iter > maxIter) throw new MaximumIterationCountExceededException(maxIter);
                xi = multiply(add(xi, centroid, MUTABLE), 0.5, MUTABLE);
                iter++;
            }
            simplex[i] = xi;
            centroid = add(multiply(subtract(xi, centroid, IMMUTABLE), 1. / (i + 1), MUTABLE), centroid, MUTABLE);
        }

        return simplex;
    }

    private IVector reflection(IVector xc, IVector xh) {
        return subtract(
                multiply(xc, 1 + alpha, IMMUTABLE),
                multiply(xh, alpha, IMMUTABLE),
                MUTABLE);
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

    @Override
    public String getName() {
        return "Box method";
    }
}
