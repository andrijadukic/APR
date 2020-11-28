package apr.optimization.algorithms.multi.noderiv;

import apr.linear.util.Matrices;
import apr.linear.vector.IVector;
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
    private InequalityConstraint[] implicitConstraints;

    private double alpha;

    private static final double DEFAULT_ALPHA = 1.3;

    public BoxMethod(IMultivariateCostFunction function, ExplicitConstraint[] explicitConstraints, InequalityConstraint[] implicitConstraints) {
        this(function, explicitConstraints, implicitConstraints, DEFAULT_EPSILON, DEFAULT_ALPHA);
    }

    public BoxMethod(IMultivariateCostFunction function,
                     ExplicitConstraint[] explicitConstraints, InequalityConstraint[] implicitConstraints,
                     double epsilon, double alpha) {
        super(function, epsilon);
        this.explicitConstraints = explicitConstraints;
        this.implicitConstraints = implicitConstraints;
        this.alpha = alpha;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
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
        if (Constraints.test(x0, explicitConstraints) || Constraints.test(x0, implicitConstraints))
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

        while (!Constraints.test(xr, implicitConstraints)) {
            xr = multiply(add(xr, xc, MUTABLE), 0.5, MUTABLE);
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
        int size = x0.getDimension() * 2;
        IVector[] simplex = new IVector[size];
        simplex[0] = x0.copy();

        IVector centroid = x0.copy();
        Random random = ThreadLocalRandom.current();
        for (int i = 1; i < size; i++) {
            ExplicitConstraint constraint = explicitConstraints[i - 1];
            double lb = constraint.lowerbound();
            double ub = constraint.upperbound();
            IVector xi = Matrices.fill(x0.copy(), () -> lb + random.nextDouble() * (ub - lb));
            while (!Constraints.test(xi, implicitConstraints)) {
                xi = multiply(
                        add(xi, centroid, MUTABLE),
                        0.5,
                        MUTABLE);
            }
            simplex[i] = xi;
            centroid = add(centroid, multiply(subtract(centroid, xi, IMMUTABLE), 1. / (i + 1), MUTABLE), MUTABLE);
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
