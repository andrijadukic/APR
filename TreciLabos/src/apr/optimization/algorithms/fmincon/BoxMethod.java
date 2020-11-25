package apr.optimization.algorithms.fmincon;

import apr.linear.util.Matrices;
import apr.linear.vector.IVector;
import apr.optimization.algorithms.fmincon.constraints.Constraints;
import apr.optimization.algorithms.fmincon.constraints.ExplicitConstraint;
import apr.optimization.algorithms.fmincon.constraints.ImplicitConstraint;
import apr.optimization.algorithms.fminsearch.SimplexMethod;
import apr.optimization.exceptions.ConstraintsNotSatisfiedException;
import apr.optimization.functions.IMultivariableCostFunction;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.*;

public class BoxMethod extends SimplexMethod {

    private ExplicitConstraint explicitConstraint;
    private ImplicitConstraint[] implicitConstraints;

    private double alpha;

    private static final double DEFAULT_ALPHA = 1.3;

    public BoxMethod(IMultivariableCostFunction f, ExplicitConstraint explicitConstraint, ImplicitConstraint[] implicitConstraints) {
        super(f);
        this.explicitConstraint = explicitConstraint;
        this.implicitConstraints = implicitConstraints;
        alpha = DEFAULT_ALPHA;
    }

    public BoxMethod(IMultivariableCostFunction f, ExplicitConstraint explicitConstraint, ImplicitConstraint[] implicitConstraints, double epsilon, double alpha) {
        super(f, epsilon);
        this.explicitConstraint = explicitConstraint;
        this.implicitConstraints = implicitConstraints;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public ExplicitConstraint getExplicitConstraint() {
        return explicitConstraint;
    }

    public void setExplicitConstraint(ExplicitConstraint explicitConstraint) {
        this.explicitConstraint = explicitConstraint;
    }

    public ImplicitConstraint[] getImplicitConstraints() {
        return implicitConstraints;
    }

    public void setImplicitConstraints(ImplicitConstraint[] implicitConstraints) {
        this.implicitConstraints = implicitConstraints;
    }

    @Override
    public IVector search(IVector x0) {
        if (explicitConstraint.test(x0) || Constraints.test(implicitConstraints, x0))
            throw new ConstraintsNotSatisfiedException();

        IVector[] X = initialSimplex(x0);
        double[] fX = Arrays.stream(X).mapToDouble(f::valueAt).toArray();
        while (true) {
            Pair argMaxMin = argMaxTwo(fX);
            int h = argMaxMin.first();
            int h2 = argMaxMin.second();
            IVector xh = X[h];
            IVector xh2 = X[h2];

            IVector xc = centroid(X, h);
            IVector xr = reflection(xc, xh);

            double lb = explicitConstraint.lowerBound();
            double ub = explicitConstraint.upperBound();
            for (int i = 0, n = xr.getDimension(); i < n; i++) {
                if (xr.get(i) < lb) {
                    xr.set(i, lb);
                } else if (xr.get(i) > ub) {
                    xr.set(i, ub);
                }
            }

            while (!Constraints.test(implicitConstraints, xr)) {
                xr = multiply(add(xr, xc, MUTABLE), 0.5, MUTABLE);
            }

            if (f.valueAt(xr) > f.valueAt(xh2)) {
                xr = multiply(add(xr, xc, MUTABLE), 0.5, MUTABLE);
            }

            X[h] = xr;
            fX[h] = f.valueAt(xr);

            if (isStopCriteriaMet(fX, xc)) break;
        }

        return X[argMin(fX)];
    }

    private IVector[] initialSimplex(IVector x0) {
        int size = x0.getDimension() * 2;
        IVector[] simplex = new IVector[size];
        simplex[0] = x0.copy();

        IVector centroid = x0.copy();
        Random random = ThreadLocalRandom.current();
        double lb = explicitConstraint.lowerBound();
        double ub = explicitConstraint.upperBound();
        double range = ub - lb;
        for (int i = 1; i < size; i++) {
            IVector xi = Matrices.fill(x0.copy(), () -> lb + random.nextDouble() * range);
            while (!Constraints.test(implicitConstraints, xi)) {
                xi = multiply(add(xi, centroid, MUTABLE), 0.5, MUTABLE);
            }
            simplex[i] = xi;
            centroid = add(centroid, multiply(xi, 0.5, MUTABLE), MUTABLE);
        }

        return simplex;
    }

    private IVector reflection(IVector xc, IVector xh) {
        return subtract(
                multiply(xc, 1 + alpha, IMMUTABLE),
                multiply(xh, alpha, IMMUTABLE),
                MUTABLE);
    }

    private Pair argMaxTwo(double[] array) {
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
