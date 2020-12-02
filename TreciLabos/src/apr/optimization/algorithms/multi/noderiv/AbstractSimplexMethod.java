package apr.optimization.algorithms.multi.noderiv;

import apr.linear.util.Vectors;
import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;

import java.util.Arrays;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.IMMUTABLE;
import static apr.linear.util.linalg.OperationMutability.MUTABLE;

/**
 * Abstract implementation of a simplex optimization method
 */
public abstract class AbstractSimplexMethod extends AbstractMultivariateOptimizer {

    protected AbstractSimplexMethod(IMultivariateCostFunction function) {
        super(function);
    }

    protected AbstractSimplexMethod(IMultivariateCostFunction function, double epsilon) {
        super(function, epsilon);
    }

    @Override
    public IVector search(IVector x0) {
        validate(x0);

        final IVector[] X = initialSimplex(x0);
        final double[] fX = Arrays.stream(X).mapToDouble(function::valueAt).toArray();

        return optimize(X, fX);
    }

    protected abstract void validate(IVector x0);

    protected abstract IVector[] initialSimplex(IVector x0);

    protected abstract IVector optimize(IVector[] X, double[] fX);

    protected IVector centroid(IVector[] simplex, int h) {
        int n = simplex.length;
        IVector centroid = Vectors.zeroes(simplex[0].getDimension());
        for (int i = 0; i < n; i++) {
            if (i == h) continue;
            add(centroid, simplex[i], MUTABLE);
        }
        return multiply(centroid, 1. / (n - 1), MUTABLE);
    }

    protected IVector reflection(IVector xc, IVector xh, double alpha) {
        return subtract(
                multiply(xc, 1 + alpha, IMMUTABLE),
                multiply(xh, alpha, IMMUTABLE),
                MUTABLE);
    }

    protected IVector expansion(IVector xc, IVector xr, double gamma) {
        return add(
                multiply(xc, 1 - gamma, IMMUTABLE),
                multiply(xr, gamma, IMMUTABLE),
                MUTABLE);
    }

    protected IVector contraction(IVector xc, IVector xh, double beta) {
        return add(
                multiply(xc, 1 - beta, IMMUTABLE),
                multiply(xh, beta, IMMUTABLE),
                MUTABLE);
    }

    protected IVector shrink(IVector xi, IVector xl, double sigma) {
        return multiply(
                add(xi, xl, MUTABLE),
                sigma,
                MUTABLE);
    }

    protected boolean testConvergence(double[] fX, double fxc) {
        double val = 0.;
        for (double fx : fX) {
            val += Math.pow(fx - fxc, 2);
        }
        return Math.sqrt(val / (fX.length - 1)) <= epsilon;
    }

    protected int argMin(double[] array) {
        int index = 0;
        double min = array[0];
        for (int i = 1, n = array.length; i < n; i++) {
            double temp = array[i];
            if (temp < min) {
                min = temp;
                index = i;
            }
        }
        return index;
    }
}
